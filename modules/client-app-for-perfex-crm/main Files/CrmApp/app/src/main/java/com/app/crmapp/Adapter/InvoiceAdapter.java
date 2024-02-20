package com.app.crmapp.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.crmapp.Models.InvoiceDetail;
import com.app.crmapp.R;
import com.app.crmapp.Utils.mDateFormat;

import java.util.List;

/**
 * Created by LBMSOLUTIONS on 29-10-2019.
 */

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.MyHolder> {
    private Activity activity;
    private List<InvoiceDetail.InvoiceDetailBean> data;

    /****** initialize values ******/
    public InvoiceAdapter(FragmentActivity activity, List<InvoiceDetail.InvoiceDetailBean> data) {
        this.activity= activity;
        this.data=data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_invoice,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder viewHolder, int i) {
        /******* display data *******/
        viewHolder.startDate.setText("Start Date : "+new mDateFormat().mFormat(data.get(i).getDate()));
        viewHolder.dueDate.setText("Due Date  : "+new mDateFormat().mFormat(data.get(i).getDuedate()));
        viewHolder.amount.setText(" \u20B9 "+data.get(i).getTotal());
        if(data.get(i).getStatus().equals("1")){
            viewHolder.status.setBackgroundResource(R.drawable.in_progress);
            viewHolder.status.setText("Un Paid");
        }else if(data.get(i).getStatus().equals("2")){
            viewHolder.status.setBackgroundResource(R.drawable.in_progress);
            viewHolder.status.setText("Paid");
        }else if(data.get(i).getStatus().equals("3")){
            viewHolder.status.setBackgroundResource(R.drawable.in_progress);
            viewHolder.status.setText("Partially Paid");
        }else if(data.get(i).getStatus().equals("4")){
            viewHolder.status.setBackgroundResource(R.drawable.in_progress);
            viewHolder.status.setText("OverDue");
        }else if(data.get(i).getStatus().equals("5")){
            viewHolder.status.setBackgroundResource(R.drawable.in_progress);
            viewHolder.status.setText("Cancelled");
        }else if(data.get(i).getStatus().equals("6")){
            viewHolder.status.setBackgroundResource(R.drawable.in_progress);
            viewHolder.status.setText("In Progress");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /******* viewholder for recyclerview *******/
    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView name,startDate,dueDate,amount,status;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            /******* fetch ids from xml *******/
            name= itemView.findViewById(R.id.text_name);
            startDate= itemView.findViewById(R.id.start_date);
            dueDate= itemView.findViewById(R.id.due_date);
            status= itemView.findViewById(R.id.status);
            amount= itemView.findViewById(R.id.amount);
        }
    }
}
