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
import android.widget.TextView;

import com.app.crmapp.Adapter.ProposalAdapter;
import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.ProposalDetail;
import com.app.crmapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LBMSOLUTIONS on 22-10-2019.
 */

public class ProposalListFragment extends Fragment {
    private RecyclerView supportList;
    private ProposalAdapter adapter;
    private LinearLayoutManager linearManager;
    private List<ProposalDetail.DataBean> data;
    private ProgressDialog dialog;
    private TextView total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proposal_list, container, false);

        /***** Initialize components from xml ******/
        initView(view);

        /****** get userId from sharedpreference ******/
        String userId= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserId();

        /********* Check for Internet Connection
         * if available than make api call getProposalData
         * otherwise toast with error message **********/
        if(ConnectionDetection.isInternetAvailable(getActivity())){
            getProposalData(userId);
        }else{
            ToastCustom.toastInfo(getActivity(),getString(R.string.network_error));
        }
        return view;
    }

    private void initView(View view) {
        /****** Initailize ProgressDialog
         *  set Message on it ******/
        dialog= new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.wait_msg));

        /****** initialize Arraylist ******/
        data = new ArrayList<>();
        total=view.findViewById(R.id.total);
        supportList = view.findViewById(R.id.proposal_list);

        /*******Initialize linearlayoutManager
         * set on supportList recylerView ********/
        linearManager = new LinearLayoutManager(getActivity());
        supportList.setLayoutManager(linearManager);
    }

    private void getProposalData(String userId) {
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<ProposalDetail> call= service.getProposalList(userId, Urls.API_KEY);
        call.enqueue(new Callback<ProposalDetail>(){
            @Override
            public void onResponse(Call<ProposalDetail> call, Response<ProposalDetail> response) {
                dialog.dismiss();
                /******** if response status is 1 than setData() is called
                 * otherwise toast with server error message *********/
                if(response.body().getStatus()==1){
                    setData(response.body());
                }else {
                    ToastCustom.toastError(getActivity(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProposalDetail> call, Throwable t) {
                dialog.dismiss();
                /********If api call fails this toast will be displayed *******/
                ToastCustom.toastError(getActivity(),call.toString());
            }
        });
    }

    /******* initialize data arraylist and if size is greator than 0
     * than adapter set on the list
     * otherwise Toast will be displayed*********/
    private void setData(ProposalDetail body) {
        total.setText(body.getValue().getTotal());
        data= body.getData();
        if(data.size()>0) {
            adapter = new ProposalAdapter(getActivity(), data);
            supportList.setAdapter(adapter);
        }else{
            ToastCustom.toastInfo(getActivity(),"Proposal Data is Empty");
        }
    }
}