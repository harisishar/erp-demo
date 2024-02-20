package com.app.crmapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.CompanyData;
import com.app.crmapp.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LBMSOLUTIONS on 29-10-2019.
 */

public class SplashActivity extends AppCompatActivity{
    private static final int SPLASH_TIME_OUT = 3000;
    private static final String TAG = "Splash";
    private TextView textview;
    private String isLogin=null;
    private ImageView mLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        /******Get ISLOGIN string value from sharedpreference ******/
        isLogin= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getIsLogin();
        textview=findViewById(R.id.title);
        mLogo=findViewById(R.id.imageView);

        /****** spannable class is used to change color of the string
         * from specific position to mentioned position at runtime
         * *******/
        Spannable word = new SpannableString("PERFEX CRM APP");
        word.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText(word);

        /*******Method to get Company logo*******/
        getCompanyLogo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    /*********If IsLogin value is 1 than user redirect to main activity
                     * otherwise LoginActivity screen is opened***********/
                    if (isLogin.equals("1")) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                    finish();
                } catch (NullPointerException ex) {
                    /******* when user first time open app after splash activity redirect to Login Activity*****/
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /******** apicall to get Company logo *******/
    private void getCompanyLogo() {
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<CompanyData> call= service.getCompanyData("16", Urls.API_KEY);
        call.enqueue(new Callback<CompanyData>() {
            @Override
            public void onResponse(Call<CompanyData> call, Response<CompanyData> response) {
                Log.e(TAG,"Response"+ response.body());
                /**********if response status is 1 than setLogo  method is called
                 *  otherwise error message is displayed***********/
                if(response.body().getStatus()==1){
                    setLogo(response.body());
                }else{
                    ToastCustom.toastError(SplashActivity.this,response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CompanyData> call, Throwable t) {
                Log.e("Failure","failure");
                /********If api call fails this toast will be displayed *******/
                ToastCustom.toastError(SplashActivity.this,t.toString());
            }
        });
    }

    private void setLogo(CompanyData body) {
        /*******Display logo on the screen********/
        Picasso.with(this).load(ApiInterface.LOGO_BASE+body.getData().get(0).getValue()).into(mLogo);
    }
}

