package com.app.crmapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.crmapp.Models.ProjectData;
import com.app.crmapp.R;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private FragmentActivity activity;
    private List<ProjectData.DataBean> data;

    public ProjectListAdapter(FragmentActivity activity, List<ProjectData.DataBean> listData) {
        this.activity = activity;
        this.data=listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_item_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.projectName.setText(data.get(i).getName());
        viewHolder.department.setText(data.get(i).getDescription());
        viewHolder.date.setText(data.get(i).getStart_date());

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView projectName,domainName,department,status,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName= itemView.findViewById(R.id.project_name);
            domainName=itemView.findViewById(R.id.domain_name);
            department= itemView.findViewById(R.id.department);
            status=itemView.findViewById(R.id.status);
            date= itemView.findViewById(R.id.date);
        }
    }
}
