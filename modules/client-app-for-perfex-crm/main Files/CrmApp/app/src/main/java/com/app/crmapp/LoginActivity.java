package com.app.crmapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.CompanyData;
import com.app.crmapp.Models.Login;
import com.app.crmapp.R;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEmail, mPassword;
    private Button login;
    private ImageView mLogo,imageEye;
    private ProgressDialog dialog;
    private  final String TAG="LoginActivity";
    private Boolean isClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*******Method to fetch id's of the layout components from xml*******/
        initView();

        /*****Check for internet connection, if available than make call to the server otherwise show error message ******/
        if(ConnectionDetection.isInternetAvailable(this)) {
            /*****Method to get company logo from server*****/
            getCompanyLogo();
        }
        else{
            ToastCustom.toastError(this,getString(R.string.network_error));
        }
    }

    private void getCompanyLogo() {
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<CompanyData> call= service.getCompanyData("16",Urls.API_KEY);
        call.enqueue(new Callback<CompanyData>() {
            @Override
            public void onResponse(Call<CompanyData> call, Response<CompanyData> response) {
                Log.e(TAG,"Response"+ response.body());
                /*****if response status is 1 than setLogo method is called otherwise show error message*****/
                if(response.body().getStatus()==1){
                    setLogo(response.body());
                }
                else{
                    ToastCustom.toastError(LoginActivity.this,response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CompanyData> call, Throwable t) {
                Log.e("failure","Failure"+" "+t.getMessage());
                ToastCustom.toastError(LoginActivity.this,t.toString());
            }
        });
    }

    /******Method to display logo on the screen******/
    private void setLogo(CompanyData body) {
        Picasso.with(this).load(ApiInterface.LOGO_BASE+body.getData().get(0).getValue()).into(mLogo);
    }

    private void initView() {
        mEmail= findViewById(R.id.txtEmail);
        mPassword=findViewById(R.id.txtPassword);
        login=findViewById(R.id.loginBtn);
        mLogo= findViewById(R.id.logo);
        imageEye= findViewById(R.id.iv_eye);
        dialog= new ProgressDialog(this);
        dialog.setMessage(getString(R.string.wait_msg));

        /********OnClick Listener*********/
        isClicked=false;

        /***** set icon on imageEye view*****/
        imageEye.setBackgroundResource(R.drawable.ic_eye_grey);
        login.setOnClickListener(this);
        imageEye.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_eye:
                /****** icon replaced with new icon
                 * when user show and hide password *****/
                if (isClicked) {
                    imageEye.setBackgroundResource(R.drawable.ic_eye_grey);
                    mPassword.setTransformationMethod(new PasswordTransformationMethod());
                    isClicked=false;
                }
                else {
                    imageEye.setBackgroundResource(R.drawable.ic_eye_green);
                    mPassword.setTransformationMethod(null);
                    isClicked=true;
                }
                break;
            case R.id.loginBtn:
                /*******When User click on the login button
                 * after data validation else part is executed********/
                String email= mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    ToastCustom.toastInfo(this, getString(R.string.empty_field_check));
                }
                else if(TextUtils.isEmpty(email)){
                    ToastCustom.toastInfo(this, getString(R.string.email_check));
                }
                else if(TextUtils.isEmpty(password)){
                    ToastCustom.toastInfo(this, getString(R.string.password_check));
                }
                else if(!isValidEmail(email)){
                    ToastCustom.toastInfo(this, getString(R.string.email_validation));
                }
                else if(!ConnectionDetection.isInternetAvailable(this)){
                    ToastCustom.toastError(this,getString(R.string.network_error));
                }
                else{
                    /****Hide keyboard***/
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    /****loginUser() called****/
                    loginUser(email,password);
                }
                break;
        }
    }
    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /******** Login api using Retrofit**********/
    private void loginUser(String email, String password) {
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<Login> call= service.loginUser(Urls.API_KEY,email,password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.e(TAG,"Response"+response.body());
                dialog.dismiss();
                /*****If response success status is 200 than setData method will be called
                 * otherwise error message is displayed on the screen*****/
                if(response.body().getSuccess()==200){
                    setData(response.body());
                }
                else{
                    ToastCustom.toastError(LoginActivity.this,response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e(TAG,"OnFailure"+ t.getMessage());
                dialog.dismiss();

                /********If api call fails this toast will be displayed *******/
                ToastCustom.toastError(LoginActivity.this,call.toString());
            }
        });
    }


    /******method to save data into the shared preference,
     * than after navigate User to the Main Activity ******/
    private void setData(Login body) {
        saveLoginData(body);
        Intent intent= new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    /*****This method saved the data into the sharedpreference
     * and display toast with login successfull message *****/
    private void saveLoginData(Login body) {
        SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).saveData("1",body.getUser_detail().getUserid());
        ToastCustom.toastSuccess(this,body.getMessage().toString());
    }
}
