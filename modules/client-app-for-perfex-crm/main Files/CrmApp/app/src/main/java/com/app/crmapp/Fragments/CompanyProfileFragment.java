package com.app.crmapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.CompanyProfile;
import com.app.crmapp.Models.UpdateCompanyProfileModel;
import com.app.crmapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyProfileFragment extends Fragment implements View.OnClickListener {
    private TextView mCompanyName,mGstinNumber,mPhone,mWebsite,mCountry,mCity,mZipCode,mState;
    private EditText mEditCompanyName,mEditGstinNumber,mEditphone,mEditWebsite,mEditCountry,mEditCity,mEditZipcode,mEditState;
    private Button mBtnUpdate;
    private boolean isEdited;
    private String userId;
    private static String TAG="CompanyProfile";
    private ProgressDialog dialog;
    private View view;
    private boolean dialogStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update_profile,container,false);

        /*****Get UserId from SharedPreference******/
        userId= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserId();

        /****** Initialize Xml Components******/
        initId(view);

        /********** Check Internet Connection if true than make call
         * to server to get company profile
         * otherwise Internet Connectionerror displayed *********/
        if(ConnectionDetection.isInternetAvailable(getActivity())){
            dialogStatus=true;
            getCompanyProfile(userId,dialogStatus);
        }else {
            ToastCustom.toastError(getActivity(),getString(R.string.network_error));
        }
        return view;
    }

    private void initId(View v){
        isEdited=false;
        mCompanyName = v.findViewById(R.id.tv_company);
        mGstinNumber = v.findViewById(R.id.tv_gstin_number);
        mPhone = v.findViewById(R.id.tv_phone);
        mWebsite = v.findViewById(R.id.tv_website);
        mCountry = v.findViewById(R.id.tv_country);
        mCity = v.findViewById(R.id.tv_city);
        mZipCode = v.findViewById(R.id.tv_zip_code);
        mState = v.findViewById(R.id.tv_state);
        mEditCompanyName = v.findViewById(R.id.ed_company);
        mEditGstinNumber = v.findViewById(R.id.ed_gstin_number);
        mEditphone = v.findViewById(R.id.ed_phone);
        mEditWebsite = v.findViewById(R.id.ed_website);
        mEditCountry = v.findViewById(R.id.ed_country);
        mEditCity = v.findViewById(R.id.ed_city);
        mEditZipcode = v.findViewById(R.id.ed_zip_code);
        mEditState = v.findViewById(R.id.ed_state);
        mBtnUpdate=v.findViewById(R.id.btn_update_profile);

        /*****Set Edit text on the fields accordingly
         * to Edit profile user click on these textview
         * edit fields accordingly ******/
        mCompanyName.setText(getString(R.string.edit));
        mGstinNumber.setText(getString(R.string.edit));
        mPhone.setText(getString(R.string.edit));
        mWebsite.setText(getString(R.string.edit));
        mCountry.setText(getString(R.string.edit));
        mCity.setText(getString(R.string.edit));
        mZipCode.setText(getString(R.string.edit));
        mState.setText(getString(R.string.edit));


        /***********OnClick Listener************/
        mCompanyName.setOnClickListener(this);
        mGstinNumber.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        mWebsite.setOnClickListener(this);
        mCountry.setOnClickListener(this);
        mCity.setOnClickListener(this);
        mZipCode.setOnClickListener(this);
        mState.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);

        /******* Initialize Progress Dialog
         * set message on it ********/
        dialog= new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.wait_msg));
    }

    private void getCompanyProfile(String id, boolean dialogStatus) {
        if(dialogStatus) {
            dialog.show();
        }
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<CompanyProfile> call= service.getProfile(id,Urls.API_KEY);
        call.enqueue(new Callback<CompanyProfile>() {
            @Override
            public void onResponse(Call<CompanyProfile> call, Response<CompanyProfile> response) {
                dialog.dismiss();
                /******** If Response status is 1
                 * than setData() called
                 * otherwise toast with server error message displayed *********/
                if(response.body().getStatus()==1) {
                    setData(response.body());

                }else{
                    ToastCustom.toastError(getActivity(),response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<CompanyProfile> call, Throwable t) {
                Log.e(TAG,"Failure"+ t.toString());
                dialog.dismiss();
                ToastCustom.toastError(getActivity(),t.getMessage());
            }
        });
    }

    private void setData(CompanyProfile body) {
        /**** set company data on the views ****/
        mEditCompanyName.setText(body.getData().get(0).getCompany());
        mEditGstinNumber.setText(body.getData().get(0).getGstin_number());
        mEditphone.setText(body.getData().get(0).getPhonenumber());
        mEditWebsite.setText(body.getData().get(0).getWebsite());
        mEditCountry.setText(body.getData().get(0).getCountry());
        mEditCity.setText(body.getData().get(0).getCity());
        mEditZipcode.setText(body.getData().get(0).getZip());
        mEditState.setText(body.getData().get(0).getState());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_company:
                mCompanyName.setText(getString(R.string.edit));
                mCompanyName.setTextColor(getResources().getColor(R.color.text_color));
                mEditCompanyName.setEnabled(true);
                mEditCompanyName.requestFocus();
                isEdited = true;
                break;

            case R.id.tv_gstin_number:
                mGstinNumber.setText(getString(R.string.edit));
                mGstinNumber.setTextColor(getResources().getColor(R.color.text_color));
                mEditGstinNumber.setEnabled(true);
                mEditGstinNumber.requestFocus();
                isEdited = true;
                break;

            case R.id.tv_phone:
                mPhone.setText(getString(R.string.edit));
                mPhone.setTextColor(getResources().getColor(R.color.text_color));
                mEditphone.setEnabled(true);
                mEditphone.requestFocus();
                isEdited = true;
                break;

            case R.id.tv_website:
                mWebsite.setText(getString(R.string.edit));
                mWebsite.setTextColor(getResources().getColor(R.color.text_color));
                mEditWebsite.setEnabled(true);
                mEditWebsite.requestFocus();
                isEdited = true;
                break;

            case R.id.tv_country:
                mCountry.setText(getString(R.string.edit));
                mCountry.setTextColor(getResources().getColor(R.color.text_color));
                mEditCountry.setEnabled(true);
                mEditCountry.requestFocus();
                isEdited = true;
                break;

            case R.id.tv_city:
                mCity.setText(getString(R.string.edit));
                mCity.setTextColor(getResources().getColor(R.color.text_color));
                mEditCity.setEnabled(true);
                mEditCity.requestFocus();
                isEdited = true;
                break;

            case R.id.tv_zip_code:
                mZipCode.setText(getString(R.string.edit));
                mZipCode.setTextColor(getResources().getColor(R.color.text_color));
                mEditZipcode.setEnabled(true);
                mEditZipcode.requestFocus();
                isEdited = true;
                break;

            case R.id.tv_state:
                mState.setText(getString(R.string.edit));
                mState.setTextColor(getResources().getColor(R.color.text_color));
                mEditState.setEnabled(true);
                mEditState.requestFocus();
                isEdited = true;
                break;

            case R.id.btn_update_profile:
                /******* If IsEdited value is true
                 * than Internet connection checked if its available
                 * than updateCompanyProfile() executed
                 * otherwise error message displayed *******/
                if (isEdited) {
                    if (ConnectionDetection.isInternetAvailable(getActivity())) {
                        updateCompanyProfile(mEditCompanyName.getText().toString(), mEditGstinNumber.getText().toString(),
                                mEditphone.getText().toString(), mEditWebsite.getText().toString(),
                                mEditCountry.getText().toString(), mEditCity.getText().toString(),
                                mEditZipcode.getText().toString(),
                                mEditState.getText().toString());
                    }else {
                        ToastCustom.toastError(getActivity(), getString(R.string.network_error));
                    }
                }else {
                    ToastCustom.toastInfo(getActivity(), getString(R.string.empty_update_error));
                }
                break;
        }
    }

    private void updateCompanyProfile(String company_proflie_name,String company_gstin,String company_phone_number,
                                       String company_website,String company_country,String company_city,
                                       String company_zip_code,String company_state){
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<UpdateCompanyProfileModel> call=service.updateCompanyProfile(userId,Urls.API_KEY,company_proflie_name,company_gstin,
                                                                            company_phone_number,company_website,company_country,company_city,company_zip_code,
                                                                            company_state);
        call.enqueue(new Callback<UpdateCompanyProfileModel>(){
            @Override
            public void onResponse(Call<UpdateCompanyProfileModel> call, Response<UpdateCompanyProfileModel> response) {
                dialog.dismiss();
                try
                {
                    /******If response success code is 200
                     * successfully updated profile
                     * and getCompanyProfile() is called to reflect changes
                     * otherwise toase with error message is displayed *******/
                   if (response.body().getSuccess()==200) {
                       dialogStatus=false;
                       getCompanyProfile(userId, dialogStatus);
                       ToastCustom.toastSuccess(getActivity(), response.body().getMessage());
                   }
                }
                catch (Exception e){
                   e.getStackTrace();
               }
            }

            @Override
            public void onFailure(Call<UpdateCompanyProfileModel> call, Throwable t) {
                Log.e("Failure","Failure"+t.toString());
                dialog.dismiss();
                ToastCustom.toastError(getActivity(),t.getMessage());
            }
        });
    }
}
