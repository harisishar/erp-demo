package com.app.crmapp.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.crmapp.Models.ContractDetail;
import com.app.crmapp.R;
import com.app.crmapp.Utils.mDateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by LBMSOLUTIONS on 29-10-2019.
 */

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.MyHolder> {
    private Activity activity;
    private List<ContractDetail.DataBean> data;

    /****** constructor to initialize values ******/
    public ContractAdapter(FragmentActivity activity, List<ContractDetail.DataBean> data) {
        this.data= data;
        this.activity= activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_invoice,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder viewHolder, int i) {
        /********* set data on views *********/
        viewHolder.startDate.setText("Start Date : "+ new mDateFormat().mFormat(data.get(i).getDatestart()));
        viewHolder.dueDate.setText("End Date : "+new mDateFormat().mFormat(data.get(i).getDateend()));
        viewHolder.amount.setText("\u20B9 "+data.get(i).getContract_value());
        viewHolder.name.setText(data.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView name,startDate,dueDate,amount,status;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            /***** initialize xml values *******/
            name = itemView.findViewById(R.id.text_name);
            startDate = itemView.findViewById(R.id.start_date);
            dueDate = itemView.findViewById(R.id.due_date);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.status);
        }
    }
}
