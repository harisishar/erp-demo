package com.app.crmapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.crmapp.Models.DepartmentModel;
import com.app.crmapp.Models.SupportTicket;
import com.app.crmapp.Models.TicketStatus;
import com.app.crmapp.R;
import com.app.crmapp.UserReplyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LBMSOLUTIONS on 22-10-2019.
 */

public class SupportListAdapter extends RecyclerView.Adapter<SupportListAdapter.MyHolder> {
    private Activity activity;
    private List<SupportTicket.DataBean> data;

    public SupportListAdapter(FragmentActivity activity, List<SupportTicket.DataBean> data) {
        this.activity=activity;
        this.data= data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.support_list_items,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder viewHolder, final int i) {
        final String ticketId = data.get(i).getTicketid();
        final String username = data.get(i).getName();
        viewHolder.subject.setText(data.get(i).getSubject());
        viewHolder.description.setText(data.get(i).getMessage());
        viewHolder.date.setText(data.get(i).getDate());
        viewHolder.department.setText(data.get(i).getDepartment());
        viewHolder.status.setText(data.get(i).getStatus());
        viewHolder.status.setBackgroundResource(R.drawable.in_progress);
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UserReplyActivity.class);
                intent.putExtra("ticket_id", ticketId);
                intent.putExtra("username", username);
                intent.putExtra("created_date",viewHolder.date.getText().toString());
                intent.putExtra("subject",viewHolder.subject.getText().toString());
                intent.putExtra("department",viewHolder.department.getText());
                intent.putExtra("status",viewHolder.status.getText().toString());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView subject,description,department,date,status;
        private RelativeLayout parentLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            subject= itemView.findViewById(R.id.ticket_subject);
            description=itemView.findViewById(R.id.ticket_description);
            department=itemView.findViewById(R.id.department);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.ticket_status);
            parentLayout=itemView.findViewById(R.id.parent_layout);
        }
    }
}
