package com.app.crmapp;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.app.crmapp.Adapter.ReplyListAdapter;
import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.FilePath;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.AddTicketModel;
import com.app.crmapp.Models.CompanyData;
import com.app.crmapp.Models.ReplyDetailModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserReplyActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog dialog;
    private static String TAG="UserReplyActivity";
    private TextView tv_AttachmentName;
    private EditText mEditReply;
    private Button mBtnAddReply;
    private String user_id=" ",formattedDate = "";
    private List<ReplyDetailModel.DataBean> listData = new ArrayList<>();
    private static final int PICKFILE_RESULT_CODE = 1;
    private static  final int Pdf_RESULT_CODE = 222;
    private String ticket_id,username,Department,mDate,mSubject,mStatus;
    private RecyclerView recyclerView;
    private ReplyListAdapter adapter;
    private LinearLayoutManager linearManager;
    private AlertDialog mdialog;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private File photoFile ;
    private String imagePath = "";
    private String imagePathPdf = "";
    boolean boolean_permission;
    private ImageView back_btn;
    private TextView mCompanyname,mTicketSubject,mUsername,mDepartment,mstatus,mTicketDate;
    private static final int STORAGE_PERMISSION_CODE = 123;
    Uri selectedImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reply);

        /******* request Read and write external storage permissions *******/
        requestPermission();
        requestStoragePermission();

        /**********Get Data From Previous(Support screen fragment) screen*********/
        ticket_id = getIntent().getStringExtra("ticket_id");
        username = getIntent().getStringExtra("username");
        Department = getIntent().getStringExtra("department");
        mSubject = getIntent().getStringExtra("subject");
        mDate = getIntent().getStringExtra("created_date");
        mStatus = getIntent().getStringExtra("status");

        /*******Get UserId From Sharedpreference*******/
        user_id= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserId();
        Log.e(TAG,"TicketId and UserId"+ticket_id+" "+user_id);

        /*****Get Layout components id's from xml*****/
        initId();

        /*****Check for Internet Connection if available
         * than make call to server otherwise displayed
         * Internet connection error on screen*****/
        if(ConnectionDetection.isInternetAvailable(this)){
            /*****Methods to get company name and get Users Reply list*****/
            getCompanyName();
            getReplyDetail(ticket_id);
        }else {
            ToastCustom.toastError(this,getString(R.string.network_error));
        }
    }

    private void initId(){
        /******Initialize ProgressDialog and set Message******/
        dialog= new ProgressDialog(this);
        dialog.setMessage(getString(R.string.wait_msg));

        back_btn = findViewById(R.id.back_btn);
        mCompanyname = findViewById(R.id.company_name);
        mTicketSubject = findViewById(R.id.ticket_subject);
        mUsername = findViewById(R.id.username);
        mDepartment = findViewById(R.id.department);
        mstatus = findViewById(R.id.ticket_status);
        mTicketDate = findViewById(R.id.date);
        mEditReply = findViewById(R.id.ed_reply);
        mBtnAddReply = findViewById(R.id.btn_add_reply);
        tv_AttachmentName = findViewById(R.id.tv_attachment_name);
        recyclerView = findViewById(R.id.recycler_list);

        /*****Set data on views*******/
        mTicketSubject.setText(mSubject);
        mUsername.setText(username);
        mDepartment.setText(Department);
        mstatus.setText(mStatus);
        mstatus.setBackgroundResource(R.drawable.in_progress);
        mTicketDate.setText(mDate);

        /******Set OnClick Listener*******/
        tv_AttachmentName.setOnClickListener(this);
        mBtnAddReply.setOnClickListener(this);
        back_btn.setOnClickListener(this);

        /******Get Current Date Time******/
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c);

        /******Set LinearLayout Manager on RecyclerView*******/
        linearManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearManager);
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {

        }
    }

    public void getCompanyName() {
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<CompanyData> call= service.getCompanyData("2", Urls.API_KEY);
        call.enqueue(new Callback<CompanyData>() {
            @Override
            public void onResponse(Call<CompanyData> call, Response<CompanyData> response) {
                Log.e("response","response"+response.body());
                dialog.dismiss();
                /********If response status is 1 than set company name
                 * otherwise diaplay error message********/
                if(response.body().getStatus()==1){
                    mCompanyname.setText(response.body().getData().get(0).getValue());
                }else{
                    ToastCustom.toastError(UserReplyActivity.this,response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CompanyData> call, Throwable t) {
                Log.e("Failure","Failure"+" "+t.getMessage());
                dialog.dismiss();

                /********If api call fails this toast will be displayed *******/
                ToastCustom.toastError(UserReplyActivity.this,t.toString());
            }
        });
    }

    private void getReplyDetail(String id) {
        dialog.show();
        ApiInterface service= RetrofitClient.getClient().create(ApiInterface.class);
        Call<ReplyDetailModel> call= service.getreplyDetail(id, Urls.API_KEY);
        call.enqueue(new Callback<ReplyDetailModel>() {
            @Override
            public void onResponse(Call<ReplyDetailModel> call, Response<ReplyDetailModel> response) {
                Log.e(TAG,"getReplyDetail"+ response.body().getStatus());
                /********If Response status is 1 than set adapter on recyclerView
                 *  otherwise Show error message ********/
                dialog.dismiss();
                if(response.body().getStatus()==1) {
                    listData.clear();
                    listData.addAll(response.body().getData());
                    adapter= new ReplyListAdapter(UserReplyActivity.this,listData,username);
                    recyclerView.setAdapter(adapter);
                    linearManager.scrollToPosition(listData.size()-1);
                    adapter.notifyDataSetChanged();
                }
                else{
                    ToastCustom.toastError(UserReplyActivity.this,response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ReplyDetailModel> call, Throwable t) {
                Log.e(TAG,"Failure"+ t.toString());
                dialog.dismiss();

                /********If api call fails this toast will be displayed *******/
                ToastCustom.toastError(UserReplyActivity.this,t.getMessage());
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                boolean_permission = true;
            }
        }
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_attachment_name:
                /*****Open dialog box to add files****/
                showDialog();
                break;
            case R.id.attach_file:
                /*********Redirect user to add pdf files*********/
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), Pdf_RESULT_CODE);
                mdialog.dismiss();
                break;
            case R.id.gallery:
                /**** addImage() called *****/
                addImage();
                mdialog.dismiss();
                break;
            case R.id.cancel:
                mdialog.dismiss();
                break;
            case R.id.btn_add_reply:
                /*******Hide Keyboard******/
                InputMethodManager immm = (InputMethodManager)this. getSystemService(Context.INPUT_METHOD_SERVICE);
                immm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                /*****Add user reply to the ticket*****/
                addTicketReply();
                break;
            case R.id.back_btn:
               onBackPressed();
                break;
        }
    }

    /******************Dialog to choose Image***********************/
    private void showDialog() {
        View view = getLayoutInflater().inflate (R.layout.add_attachment_layout, null);
        TextView fromFile = (TextView)view.findViewById( R.id.attach_file);
        TextView fromGallery = (TextView)view.findViewById( R.id.gallery);
        TextView cancel=(TextView)view.findViewById(R.id.cancel);
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setView(view);
        mdialog= builder.create();
        mdialog.show();

        /********OnclickListener*********/
        fromFile.setOnClickListener(this);
        fromGallery.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    /******* check for validated data to send in reply *******/
    private void addTicketReply(){
        String userReply = mEditReply.getText().toString().trim();
        if(TextUtils.isEmpty(userReply)){
            ToastCustom.toastInfo(this,getString(R.string.reply_check));
        }
        else {
            /******* Check for ticketid value
             * if value is null display toast with message
             * otherwise addReply() is called ********/
            if(!ticket_id.isEmpty()) {
                addReply(ticket_id,Urls.API_KEY,mEditReply.getText().toString().trim(),formattedDate,user_id);
            }else {
                ToastCustom.toastInfo(this, getString(R.string.ticket_id_check));
            }
        }
    }

    /*******request permission and than open gallery to add images******/
    private void addImage() {
        requestPermission();
        Intent intent_gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent_gallery.resolveActivity(this.getPackageManager()) != null) {
        }
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {

        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.app.crm_app.fileprovider",
                    photoFile);
            intent_gallery.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent_gallery, PICKFILE_RESULT_CODE);
        }
    }

    /*******Create File*******/
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",

                storageDir
        );
        imagePath = image.getAbsolutePath();
        return image;
    }

    /*******Api Call to Add User Reply to the selected ticket********/
    private void addReply(final String ticketId, String api_key, String add_reply, String date, String contact_id) {
        RequestBody ticket_idBody = null;
        RequestBody api_keyBody = null;
        RequestBody messageBody = null;
        RequestBody dateBody = null;
        RequestBody contactBody = null;

        dialog.show();
        MultipartBody.Part body = null;
        if(!TextUtils.isEmpty(imagePath)) {
            File file = new File(imagePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("file_name", file.getName(), requestFile);
        }
        else if (!TextUtils.isEmpty(imagePathPdf)) {
            File file = new File(imagePathPdf);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("file_name", file.getName(), requestFile);
        }

        ticket_idBody = RequestBody.create(MediaType.parse("text/plain"), ticketId);
        api_keyBody = RequestBody.create(MediaType.parse("text/plain"), api_key);
        messageBody = RequestBody.create(MediaType.parse("text/plain"), add_reply);
        dateBody = RequestBody.create(MediaType.parse("text/plain"), date);
        contactBody = RequestBody.create(MediaType.parse("text/plain"), contact_id);

        ApiInterface service = RetrofitClient.getClient().create(ApiInterface.class);
        Call<AddTicketModel> call = service.addTicketReply(ticket_idBody, api_keyBody, messageBody, dateBody, body, contactBody);
        call.enqueue(new Callback<AddTicketModel>() {
            @Override
            public void onResponse(Call<AddTicketModel> call, Response<AddTicketModel> response) {
                Log.e(TAG, "Response" + response.body());
                dialog.dismiss();

                /*********If Response Success code is 200 means your reply is added to the ticket
                 *  call getReplyDetail method to show that reply into the list
                 *  otherwise error message is displayed *********/
                if (response.body().getSuccess() == 200) {
                        //ToastCustom.toastSuccess(UserReplyActivity.this, response.body().getMessage());
                    mEditReply.setText("");
                    tv_AttachmentName.setText("");
                    getReplyDetail(ticket_id);
                } else{
                    ToastCustom.toastError(UserReplyActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<AddTicketModel> call, Throwable t) {
                Log.e(TAG, "Failure" + t.toString());

                /********If api call fails this toast will be displayed *******/
                ToastCustom.toastError(UserReplyActivity.this, t.getMessage());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        /*******Swich case on Result code if Result code matched to gallery
         * or pdf file than case executed accordingly*******/
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (requestCode == PICKFILE_RESULT_CODE) {
                   try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagePath = cursor.getString(columnIndex);
                    cursor.close();
                    tv_AttachmentName.setText(imagePath + "\n");
                   }catch (Exception e){
                       e.getStackTrace();
                   }
                }
                break;

            case Pdf_RESULT_CODE:
                if(resultCode==RESULT_OK){
                   try {
                    selectedImage= data.getData();
                        Log.e("ImagePdf", "image" + selectedImage);
                        imagePathPdf = FilePath.getPath(this, selectedImage);
                        tv_AttachmentName.setText(imagePathPdf + "\n");
                    }catch (Exception e){
                       e.getStackTrace();
                   }
                }
            break;
        }
    }

    /******** ask user for the specified permission at runtime *********/
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
