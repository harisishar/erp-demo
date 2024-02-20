package com.app.crmapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.crmapp.Adapter.SupportListAdapter;
import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.DepartmentModel;
import com.app.crmapp.Models.SupportTicket;
import com.app.crmapp.Models.TicketStatus;
import com.app.crmapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LBMSOLUTIONS on 21-10-2019.
 */

public class SupportScreenFragment extends Fragment implements View.OnClickListener{
    private RecyclerView supportList;
    private SupportListAdapter adapter;
    private LinearLayoutManager linearManager;
    private List<SupportTicket.DataBean> data;
    private FloatingActionButton add_ticket;
    private String TAG="SupportTicket";
    private ProgressDialog dialog;
    private String userId;
    private ArrayList<DepartmentModel.DataBean> dept_list;
    private ArrayList<TicketStatus.DataBean> ticket_status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support,container,false);

        /***** Initialize components from xml ******/
        initView(view);

        /****** get userId from sharedpreference ******/
        userId= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserId();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        /********* Check for Internet Connection
         * if available than make api call getTicketData()
         * otherwise toast with error message displayed **********/
        if(ConnectionDetection.isInternetAvailable(getActivity())){
            getTicketsData(userId);
        }else {
            ToastCustom.toastError(getActivity(),getString(R.string.network_error));
        }
    }

    private void initView(View view) {
        supportList= view.findViewById(R.id.support_list);
        add_ticket= view.findViewById(R.id.add_ticket);

        /****** Initailize ProgressDialog
         *  set Message on it ******/
        dialog= new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.wait_msg));

        /****** initialize Arraylist ******/
        data= new ArrayList<>();
        dept_list= new ArrayList<>();
        ticket_status=new ArrayList<>();

        /*******Initialize linearlayoutManager
         * set on supportList recylerView ********/
        linearManager= new LinearLayoutManager(getActivity());
        supportList.setLayoutManager(linearManager);

        /*******OnClick Listener**********/
        add_ticket.setOnClickListener(this);
    }

    private void getTicketsData(final String userId) {
        dialog.show();
        ApiInterface service = RetrofitClient.getClient().create(ApiInterface.class);
        Call<SupportTicket> call = service.getSupportTicket(userId, Urls.API_KEY);
        call.enqueue(new Callback<SupportTicket>() {
            @Override
            public void onResponse(Call<SupportTicket> call, Response<SupportTicket> response) {
                dialog.dismiss();

                /******** if response status is 1 than setData() is called
                 * otherwise toast with server error message *********/
                if (response.body().getStatus() == 1) {
                    setData(response.body());
                } else {
                    ToastCustom.toastError(getActivity(),response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<SupportTicket> call, Throwable t) {
                Log.e(TAG, "Failure" + t.toString());
                dialog.dismiss();

                /********If api call fails this toast will be displayed *******/
                ToastCustom.toastError(getActivity(),call.toString());
            }
        });
    }

    private void setData(SupportTicket body) {
        /******* If response data is not null
         * than setSupportTicketAdapter() is called
         * otherwise toast with empty arraylist message is displayed********/
        if(body.getData()!=null){
            setSupportTicketAdapter(body.getData());
        }else{
            ToastCustom.toastInfo(getActivity(),"No ticket is available in the list");
        }
    }

    /******** Method to initialize and set adapter on supportticket list********/
    private void setSupportTicketAdapter(List<SupportTicket.DataBean> obj) {
        data.clear();
        data.addAll(obj);
        adapter = new SupportListAdapter(getActivity(), data);
        supportList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_ticket:
                /******** click on add ticket Button
                 * navigate user to AddTicketScreen to add new ticket *********/
                Intent intent = new Intent(getActivity(), AddTicketScreen.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }
}
