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
import com.app.crmapp.Adapter.ProjectCountAdapter;
import com.app.crmapp.Adapter.ProjectListAdapter;
import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.ProjectData;
import com.app.crmapp.R;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectFragment extends Fragment {
    private RecyclerView projectCount;
    private LinearLayoutManager linearLayoutManager;
    private ProjectCountAdapter adpter;
    private RecyclerView projectList;
    private ProjectListAdapter projectAdapter;
    private String userId;
    private ProgressDialog dialog;
    private static String TAG="ProjectFragment";
    private List<ProjectData.DataBean> listData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project,container,false);

        /****** Initialize xml components******/
        initId(view);

        /****** get userId from sharedPreference *******/
        userId= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserId();

        /******Check internet connection if available
         * than getProjectDetail() is called
         * otherwise Toast with internet error message is displayed *******/
        if(ConnectionDetection.isInternetAvailable(getActivity())){
            getProjectDetail(userId);
        }else {
            ToastCustom.toastError(getActivity(),getString(R.string.network_error));
        }
        return view;
    }

    private void initId(View v) {
        /******* Initialize ProgressDialog
         * set message on progress dialog *******/
        dialog= new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.wait_msg));

        projectCount = v.findViewById(R.id.project_list);
        projectList = v.findViewById(R.id.list);

        /******** initialize and set layout manager
         * and set adapter on project count list ********/
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);
        projectCount.setLayoutManager(linearLayoutManager);
        adpter = new ProjectCountAdapter(getActivity());
        projectCount.setAdapter(adpter);

        /***** set linear layoutmanager on project list ******/
        linearLayoutManager = new LinearLayoutManager(getContext());
        projectList.setLayoutManager(linearLayoutManager);
    }

    private void getProjectDetail(String  id ){
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<ProjectData> call= service.getProjectDetail(id,Urls.API_KEY);
        call.enqueue(new Callback<ProjectData>() {
            @Override
            public void onResponse(Call<ProjectData>call, Response<ProjectData> response) {
                dialog.dismiss();
                /******* If response status is 1
                 * than setData() is called
                 * otherwise Toast with server error message is displayed *******/
                try{
                    if(response.body().getStatus()==1) {
                        setData(response.body());
                    }else{
                        ToastCustom.toastError(getActivity(),response.body().getMessage());
                    }

                }catch (Exception e){
                   e.getStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ProjectData> call, Throwable t) {
                Log.e(TAG,"Failure"+ t.toString());
                dialog.dismiss();
                ToastCustom.toastError(getActivity(),t.getMessage());
            }
        });
    }

    private  void setData(ProjectData body){
        /******* Add data in listData arraylist ******/
        List<ProjectData.DataBean> temp = body.getData();
        listData.clear();
        listData.addAll(temp);

        /****** If listData size > 0
         * than set adapter on projectlist
         * otherwise toast message with Project Data is Empty *******/
        if(listData.size()>0) {
            projectAdapter = new ProjectListAdapter(getActivity(), listData);
            projectList.setAdapter(projectAdapter);
        }
        else {
            ToastCustom.toastInfo(getActivity(),"Project Data is Empty");
        }
    }
}
