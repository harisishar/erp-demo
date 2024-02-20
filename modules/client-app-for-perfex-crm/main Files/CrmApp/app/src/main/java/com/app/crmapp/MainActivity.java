package com.app.crmapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Fragments.CompanyProfileFragment;
import com.app.crmapp.Fragments.ContractFragment;
import com.app.crmapp.Fragments.InvoiceFragment;
import com.app.crmapp.Fragments.ProjectFragment;
import com.app.crmapp.Fragments.ProposalListFragment;
import com.app.crmapp.Fragments.SupportScreenFragment;
import com.app.crmapp.Models.CompanyData;
import com.app.crmapp.Models.UserInfo;
import com.app.crmapp.R;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FragmentManager fragmentManager;
    private ImageView backbtn,logout;
    private CircleImageView user_image;
    private TextView companyName,userName,userCompanyName;
    private ProgressDialog dialog;
    private LinearLayout layoutContainer,linearLayoutName;
    private CardView support_layout,
            projects_layout,
            invoice_layout,
            company_profile_layout,
            proposal_layout,
            contracts_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /******Get UserId from the sharedPreference*********/
        String userId= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserId();
        fragmentManager = getSupportFragmentManager();

        /*****Initialize xml components*****/
        initView();

        /****Check for internet connection if available than make call to server
         * to fetch company name and user information otherwise
         * Internet connection error will be displayed on the screen ****/
        if(ConnectionDetection.isInternetAvailable(getApplicationContext())){
            getCompanyName();
            getUserInfo(userId);
        }else{
            ToastCustom.toastError(this,getString(R.string.network_error));
        }
    }

    private void initView() {
        /******Initailize ProgressDialog and set message on it*******/
        dialog= new ProgressDialog(this);
        dialog.setMessage(getString(R.string.wait_msg));

        backbtn=findViewById(R.id.back_btn);
        support_layout= findViewById(R.id.support_layout);
        projects_layout= findViewById(R.id.projects_layout);
        invoice_layout=findViewById(R.id.invoice_layout);
        company_profile_layout=findViewById(R.id.company_profile_layout);
        proposal_layout=findViewById(R.id.proposal_layout);
        contracts_layout=findViewById(R.id.contracts_layout);
        layoutContainer = findViewById(R.id.linearlayout);
        linearLayoutName = findViewById(R.id.linear_layout_name);
        linearLayoutName.setVisibility(View.VISIBLE);
        layoutContainer.setVisibility(View.VISIBLE);

        /*****ActionBar Components********/
        companyName=findViewById(R.id.app_name);
        user_image=findViewById(R.id.user_image);
        userName=findViewById(R.id.user_name);
        userCompanyName=findViewById(R.id.company_name);
        logout= findViewById(R.id.logout);


        /********OnClick Listener********/
        support_layout.setOnClickListener(this);
        proposal_layout.setOnClickListener(this);
        company_profile_layout.setOnClickListener(this);
        projects_layout.setOnClickListener(this);
        invoice_layout.setOnClickListener(this);
        contracts_layout.setOnClickListener(this);
        backbtn.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    /********* api call to get company name*********/
    public void getCompanyName() {
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<CompanyData> call= service.getCompanyData("2",Urls.API_KEY);
        call.enqueue(new Callback<CompanyData>() {
            @Override
            public void onResponse(Call<CompanyData> call, retrofit2.Response<CompanyData> response) {
                dialog.dismiss();
                /******If response Status is 1 than set company name on textview
                 *otherwise display error message ********/
                if(response.body().getStatus()==1){
                    companyName.setText(response.body().getData().get(0).getValue());
                }else{
                    ToastCustom.toastError(MainActivity.this,response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CompanyData> call, Throwable t) {
                dialog.dismiss();
                /********If api call fails this toast will be displayed *******/
                ToastCustom.toastError(MainActivity.this,t.toString());
            }
        });
    }

    /******* Apicall to get logged in user information ********/
    public void getUserInfo(String userId) {
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<UserInfo> call= service.getUserInfo(userId,Urls.API_KEY);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                /********** If Response status is 1 than setData method will be called
                 *  otherwise error message is shown*********/
                if(response.body().getStatus()==1){
                    setData(response.body());
                }else{
                    ToastCustom.toastError(MainActivity.this,response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("Failure","Failure");
                /********If api call fails this toast will be displayed *******/
                ToastCustom.toastError(MainActivity.this,t.toString());
            }
        });
    }

    /*********In this method username, usercompany_name, userimage and companyname
     * saved into sharedpreference and also display data on the screen**********/
    private void setData(UserInfo body) {
        String name= body.getDetail().getFirstname()+body.getDetail().getLastname();
        String image= body.getDetail().getProfile_image();
        String user_company_name= body.getCompany_name().get(0).getCompany();
        SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).saveUserData(name,user_company_name,image,companyName.getText().toString());
        userName.setText(name);
        userCompanyName.setText(user_company_name);

        /**********Picasso library is used to load image on ImageView*********/
        Picasso.with(this).load(ApiInterface.PROFILE_IMAGE+image).placeholder(R.drawable.dummy_profile_img).into(user_image);
    }

    /*******Check Internet connection if available than initialize fragment manager
     * and replace current fragment with called fragment
     * othewise message will be displayed in logcat**********/
    public void setFragment(Fragment fragment) {
        checkConnection();
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null).commit();
        }
        else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.support_layout:
                linearLayoutName.setVisibility(View.VISIBLE);

                /**** setFragment() called ******/
                setFragment(new SupportScreenFragment());
                break;
            case R.id.projects_layout:
                /**** setFragment() called ******/
                setFragment(new ProjectFragment());
                break;
            case R.id.invoice_layout:
                layoutContainer.setVisibility(View.GONE);

                /**** setFragment() called ******/
                setFragment(new InvoiceFragment());
                break;
            case R.id.company_profile_layout:
                /**** setFragment() called ******/
                setFragment(new CompanyProfileFragment());
                break;
            case R.id.proposal_layout:
                layoutContainer.setVisibility(View.GONE);

                /**** setFragment() called ******/
                setFragment(new ProposalListFragment());
                break;
            case R.id.contracts_layout:
                layoutContainer.setVisibility(View.GONE);

                /**** setFragment() called ******/
                setFragment(new ContractFragment());
                break;
            case R.id.back_btn:
                linearLayoutName.setVisibility(View.VISIBLE);
                layoutContainer.setVisibility(View.VISIBLE);
                super.onBackPressed();
                break;
            case R.id.logout:
                /*******Remove Userdata from the
                 * sharedpreference on logout button click*********/
                SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).logoutUser();
                finish();
                break;
                default:
                    break;
        }
    }


    /**********Check for Internet connection if available than open open new fragment
     * otherwise show alter dialog on the screen**************/
    private void checkConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        alertMassege(activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void alertMassege(boolean isConnected) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog;
        if (!isConnected) {
            alertDialogBuilder.setMessage("No Internet Connection Available." +
                    "Do you want to try again?");
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.recreate();
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                }
            });
            alertDialogBuilder.setCancelable(false);
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        linearLayoutName.setVisibility(View.VISIBLE);
        layoutContainer.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }
}
