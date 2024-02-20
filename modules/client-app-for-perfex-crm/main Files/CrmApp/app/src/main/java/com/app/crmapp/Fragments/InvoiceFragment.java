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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Adapter.InvoiceAdapter;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.InvoiceDetail;
import com.app.crmapp.R;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LBMSOLUTIONS on 29-10-2019.
 */

public class InvoiceFragment extends Fragment {
    private RecyclerView supportList;
    private InvoiceAdapter adapter;
    private LinearLayoutManager linearManager;
    private List<InvoiceDetail.InvoiceDetailBean> data;
    private TextView total,pending_text;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);

        /******** Initialize xml components ********/
        initView(view);

        /******* get userId from sharedpreference *******/
        String userId= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserId();

        /*******Check Internet connection if connection is available
         * than make call to server to getInvoice data
         * otherwise Toast with message
         * Internet connection error is displayed ********/
        if(ConnectionDetection.isInternetAvailable(getActivity())) {
            getInvoiceData(userId);
        }else{
            ToastCustom.toastInfo(getActivity(),getString(R.string.network_error));
        }
        return view;
    }

    private void initView(View view) {
        /********* Initialize ProgressDialog
         * setmessage on it *********/
        dialog= new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.wait_msg));

        total=view.findViewById(R.id.total);
        pending_text=view.findViewById(R.id.pending);
        supportList = view.findViewById(R.id.proposal_list);

        /****** set LayoutManager on recyclerview *******/
        linearManager = new LinearLayoutManager(getActivity());
        supportList.setLayoutManager(linearManager);
    }

    private void getInvoiceData(String userId) {
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<InvoiceDetail> call= service.getInvoiceDetail(userId, Urls.API_KEY);
        call.enqueue(new Callback<InvoiceDetail>(){
            @Override
            public void onResponse(Call<InvoiceDetail> call, Response<InvoiceDetail> response) {
                dialog.dismiss();
                /******** If response status is 200 than setData() called
                 * otherwise toast with server message displayed*********/
                if(response.body().getStatus()==200){
                    setData(response.body());
                }else {
                    ToastCustom.toastError(getActivity(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<InvoiceDetail> call, Throwable t) {
                Log.e("Failure","failure");
                dialog.dismiss();
                ToastCustom.toastError(getActivity(),call.toString());
            }
        });
    }

    private void setData(InvoiceDetail body) {
        total.setText(body.getTotal());
        pending_text.setText(String.valueOf(body.getPending()));

        /*******Initialise data arraylist with getInvoiceDetail data*******/
        data= body.getInvoiceDetail();

        /******* If arraylist size > 0
         * than adapter initialize and pass arraylist
         * set adapter on it
         * otherwise Toast with message Invoice data is Empty is displayed ********/
        if(data.size()>0) {
            adapter = new InvoiceAdapter(getActivity(), data);
            supportList.setAdapter(adapter);
        }else{
            ToastCustom.toastInfo(getActivity(),"Invoice Data is Empty");
        }
    }
}
