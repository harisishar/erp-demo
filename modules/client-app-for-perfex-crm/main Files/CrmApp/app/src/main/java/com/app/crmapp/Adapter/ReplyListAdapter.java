package com.app.crmapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.Models.ReplyDetailModel;
import com.app.crmapp.R;
import com.app.crmapp.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.ViewHolder> {
    private Activity activity;
    private List<ReplyDetailModel.DataBean> data;
    private String name;
    String[] separated=null;

    public ReplyListAdapter(FragmentActivity activity, List<ReplyDetailModel.DataBean> listData, String user_name) {
        this.activity=activity;
        this.data=listData;
        this.name = user_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_reply_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.mStaffName.setText(name);
        viewHolder.mContent.setText(data.get(i).getMessage());
        if(data.get(i).getAdmin()!=null){
            viewHolder.mStaffName.setText("Admin");
            viewHolder.mStaffName.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        }else {
            viewHolder.mStaffName.setText(name);
            viewHolder.mStaffName.setTextColor(activity.getResources().getColor(R.color.text_color));
        }
        if(data.get(i).getFile_name()!=null){
            String currentString = data.get(i).getFile_name();
            separated = currentString.split("\\.");
            separated[1] = separated[1].trim();
            if (separated[1].equals("pdf")) {
                viewHolder.pdf_image.setVisibility(View.VISIBLE);
                viewHolder.mAttach_image.setVisibility(View.GONE);
            }
            else if(separated[1].equals("jpg")) {
                viewHolder.mAttach_image.setVisibility(View.VISIBLE);
                viewHolder.pdf_image.setVisibility(View.GONE);
                Picasso.with(activity).load(ApiInterface.TICKETPROFILE+data.get(i).getTicketid()+"/"+data.get(i).getFile_name()).placeholder(R.drawable.dummy_profile_img).into(viewHolder.mAttach_image);
            }
        }else{
            viewHolder.mAttach_image.setVisibility(View.GONE);
            viewHolder.pdf_image.setVisibility(View.GONE);
        }

        viewHolder.pdf_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /****** pdf file will be uploaded on webview
                 * when user click on pdf image*******/
                Intent intent = new Intent(activity, WebViewActivity.class);
                intent.putExtra("ticket_id", data.get(i).getTicketid());
                intent.putExtra("file_name", data.get(i).getFile_name());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mStaffName,mStaff,mContent;
        private ImageView mAttach_image, pdf_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mStaffName = itemView.findViewById(R.id.tv_staff_name);
            // mStaff = itemView.findViewById(R.id.tv_staff);
            mContent = itemView.findViewById(R.id.tv_content);
            mAttach_image = itemView.findViewById(R.id.iv_attach_image);
            pdf_image=itemView.findViewById(R.id.pdf_image);
        }
    }
}
