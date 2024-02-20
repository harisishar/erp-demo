package com.app.crmapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.crmapp.Adapter.ContractAdapter;
import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.ContractDetail;
import com.app.crmapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LBMSOLUTIONS on 29-10-2019.
 */

public class ContractFragment extends Fragment {
    private RecyclerView contractList;
    private LinearLayoutManager linearManager;
    private List<ContractDetail.DataBean> contractData;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_contract,container,false);

        /*****Initialize views from xml******/
        initView(view);

        /******* Get UserId from SharedPreference *******/
        String userId= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserId();

        /*******Check Internet Connection error if available
         * than getContractDetal() is called
         * otherwise toast with error message displayed ********/
        if(ConnectionDetection.isInternetAvailable(getActivity())){
            getContractDetail(userId);
        }else{
            ToastCustom.toastError(getActivity(),getString(R.string.network_error));
        }
        return view;
    }

    private void initView(View view) {
        /******Initialize ProgressDialog
         * setMessage on it *******/
        dialog= new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.wait_msg));

        contractList=view.findViewById(R.id.contract_list);

        /********* set layout manager on recyclerview *********/
        linearManager= new LinearLayoutManager(getActivity());
        contractList.setLayoutManager(linearManager);
    }

    private void getContractDetail(String userId) {
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<ContractDetail> call= service.getContractDetail(userId, Urls.API_KEY);
        call.enqueue(new Callback<ContractDetail>() {
            @Override
            public void onResponse(Call<ContractDetail> call, Response<ContractDetail> response) {
                dialog.dismiss();
                /********** If response status is 1 than setData() called
                 *  otherwise toast with error message
                 *  displayed on it **********/
                if(response.body().getStatus()==1){
                    setData(response.body().getData());
                }else{
                    ToastCustom.toastError(getActivity(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ContractDetail> call, Throwable t) {
                Log.e("Failure","Failure"+t.toString());
                dialog.dismiss();
                ToastCustom.toastError(getActivity(),t.toString());
            }
        });
    }

    private void setData(List<ContractDetail.DataBean> data) {
        /**** Initialize contractData arraylist *****/
        contractData= new ArrayList<>();
        contractData.addAll(data);

        /******* data is not null or size is greator than zero
         * Adapter set on contractList Recyclerview
         * otherwise Toast with message that contract List is Empty displayed ********/
        if(contractData.size()>0) {
            ContractAdapter adapter = new ContractAdapter(getActivity(), data);
            contractList.setAdapter(adapter);
        }else{
            ToastCustom.toastInfo(getActivity(),"Contract List is Empty");
        }
    }
}
