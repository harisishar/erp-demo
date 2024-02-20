package com.app.crmapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.app.crmapp.ApiCalls.ApiInterface;
import com.app.crmapp.ApiCalls.RetrofitClient;
import com.app.crmapp.ApiCalls.Urls;
import com.app.crmapp.Common.ConnectionDetection;
import com.app.crmapp.Common.SharedPrefHelper;
import com.app.crmapp.Common.ToastCustom;
import com.app.crmapp.Models.AddTicketModel;
import com.app.crmapp.Models.DepartmentModel;
import com.app.crmapp.Models.ProjectModel;
import com.app.crmapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTicketScreen extends AppCompatActivity implements  ActivityCompat.OnRequestPermissionsResultCallback,View.OnClickListener {
    private EditText ed_Subject,ed_description;
    private TextView userName,tv_Attachment_name;
    private TextView companyName,user_name,userCompanyName;
    private ImageView mBtnBack,mLogout;
    private CircleImageView user_image;
    private Button createTicket;
    private String userId;
    private ProgressDialog dialog;
    private String currentDate = "";
    private Spinner spinnerDepartment, spinnerProject;
    private List<DepartmentModel.DataBean> getListDeparment = new ArrayList<>();
    private List<ProjectModel.DataBean> getListProject = new ArrayList<>();
    private List<String> project_count;
    private List<String> dept_count;
    private ArrayAdapter<String> projectAdapter;
    private ArrayAdapter<String> deptAdapter;
    private List<String> dept_id;
    private List<String> project_id;
    private String dept_Id,project_Id;
    private AlertDialog mdialog;
    private String imagePath=null;
    private LinearLayout project_layout;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static final int GALLERY_RESULT_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ticket_screen_new);

        /********Get UserId fromSharedPreference********/
        userId= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserId();

        /********Check Internet connection If available than make api call to server,
         * getDepartment and Project Type
         * Otherwise display toast with Internet Connection error*********/
        if(ConnectionDetection.isInternetAvailable(getApplicationContext())){
            getDepartmentApi();
            getProjectApi(userId);
        }else{
            ToastCustom.toastError(this,getString(R.string.network_error));
        }

        /******Request Permission to Write External Storage *******/
        requestPermission();

        /*******Method to initialize layout components from XML file*******/
        initView();
    }

    private void initView() {
        /******Initialize ProgreesDialog
         *  set Waiting Message on it*******/
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.wait_msg));

        spinnerProject = findViewById(R.id.spinner_project);
        spinnerDepartment=findViewById(R.id.spinner_dept);
        userName = findViewById(R.id.name);
        ed_description = findViewById(R.id.ed_description);
        ed_Subject = findViewById(R.id.ed_subject);
        createTicket = findViewById(R.id.btn_create_new);
        tv_Attachment_name = findViewById(R.id.tv_attachment_name);
        mBtnBack = findViewById(R.id.back_btn);
        mLogout = findViewById(R.id.logout);
        project_layout=findViewById(R.id.project_layout);

        /*****ActionBar Components********/
        companyName=findViewById(R.id.app_name);
        user_image=findViewById(R.id.user_image);
        user_name=findViewById(R.id.user_name);
        userCompanyName=findViewById(R.id.company_name);

        /*********Get Data from SharedPreference and display it on the views********/
        userName.setText(SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getName());
        companyName.setText(SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getCompanyName());
        user_name.setText(SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getName());
        userCompanyName.setText(SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getUserCompName());
        String image= SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).getImage();

        /****** Picasso to load image on ImageView *******/
        Picasso.with(this).load(ApiInterface.PROFILE_IMAGE+image).into(user_image);

        /***** To get current date********/
        getCurrentdate();

        /******OnClick Listener********/
        mBtnBack.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        tv_Attachment_name.setOnClickListener(this);
        createTicket.setOnClickListener(this);

        // Department Spinner Drop down elements
        dept_count = new ArrayList<String>();
        deptAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dept_count);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept_Id= String.valueOf(dept_id.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Project Spinner Drop down elements
        project_count = new ArrayList<String>();
        projectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, project_count);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                project_Id= String.valueOf(project_id.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {

        }
    }

    private void getCurrentdate() {
        Date object = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDate = df.format(object);
    }

    /******************Dialog to choose Image***********************/
    private void showDialog() {
        View view = getLayoutInflater().inflate (R.layout.add_attachment_layout, null);
        TextView fromFile = (TextView)view.findViewById( R.id.attach_file);
        //TextView fromDrive = (TextView)view.findViewById( R.id.drive);
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

    private void getDepartmentApi() {
        ApiInterface service = RetrofitClient.getClient().create(ApiInterface.class);
        Call<DepartmentModel> call = service.getDepartment(Urls.API_KEY);
        call.enqueue(new Callback<DepartmentModel>() {
            @Override
            public void onResponse(Call<DepartmentModel> call, Response<DepartmentModel> response) {
                Log.e("Contract_Fragment", "OnResponse" + " " + response.body());
                /****** If response status is 1 than setdata() will be called
                 * otherwise Toast with error message displayed on the screen ******/
                if (response.body().getStatus() == 1) {
                    setData(response.body());
                } else {
                    ToastCustom.toastError(AddTicketScreen.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DepartmentModel> call, Throwable t) {
                Log.e("Failure", "Failure" + t.toString());
                ToastCustom.toastError(AddTicketScreen.this, t.toString());
            }
        });
    }

    private void setData(DepartmentModel body) {
        /***** If department list is Not empty than adapter will set on the list
         *  otherwise Toast with message Department List is Empty*****/
        if (body != null) {
            List<DepartmentModel.DataBean> tempData = body.getData();
            getListDeparment.clear();
            getListDeparment.addAll(tempData);
            dept_id=new ArrayList<>();
            for (int i = 0; i < getListDeparment.size(); i++) {
                dept_count.add(getListDeparment.get(i).getName());
                dept_id.add(getListDeparment.get(i).getDepartmentid());
            }
            spinnerDepartment.setAdapter(deptAdapter);
        }
        else if (body.getData().size() == 0) {
            ToastCustom.toastInfo(AddTicketScreen.this, "Sorry, Department List is Empty");
        }
    }

    private void getProjectApi(String userId) {
        ApiInterface service = RetrofitClient.getClient().create(ApiInterface.class);
        Call<ProjectModel> call = service.getProjectList(userId, Urls.API_KEY);
        call.enqueue(new Callback<ProjectModel>() {
            @Override
            public void onResponse(Call<ProjectModel> call, Response<ProjectModel> response) {
                /****** If response status is 1 than setDataProject() called
                 * otherwise error message will be displayed on it ******/
                if (response.body().getStatus() == 1) {
                    setDataProject(response.body());
                } else {
                    ToastCustom.toastError(AddTicketScreen.this, response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<ProjectModel> call, Throwable t) {
                Log.e("Failure", "Failure" + t.toString());
                ToastCustom.toastError(AddTicketScreen.this, t.toString());
            }
        });
    }

    private void setDataProject(ProjectModel body) {
        /****** If Project list is not empty than adapter set on the list
         * otherwise toast message will be displayed ******/
        if (body.getData().size()>0) {
            List<ProjectModel.DataBean> tempData = body.getData();
            getListProject.clear();
            getListProject.addAll(tempData);
            project_id= new ArrayList<>();
            for (int i = 0; i < getListProject.size(); i++) {
                project_count.add(getListProject.get(i).getName());
                project_id.add(getListProject.get(i).getId());
            }
            spinnerProject.setAdapter(projectAdapter);
        }
        else  {
            project_layout.setVisibility(View.GONE);
        }
    }

    private void addNewTicket(String userId, String apiKey, String username, String subject, String project_id, String dept_id, String s, String currentDate, String description, String imagePath) {
        dialog.show();
        MultipartBody.Part body = null;
        RequestBody projectId= null;
        if(!TextUtils.isEmpty(imagePath)) {
            File file = new File(imagePath);
            RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("file_name", file.getName(), requestFile2);
        }
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody api_key = RequestBody.create(MediaType.parse("text/plain"), apiKey);
        RequestBody user_name = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody sub = RequestBody.create(MediaType.parse("text/plain"), subject);
        try {
            projectId = RequestBody.create(MediaType.parse("text/plain"), project_id);
        }
        catch (NullPointerException ex){
            projectId = RequestBody.create(MediaType.parse("text/plain"), "");
        }

        RequestBody deptId = RequestBody.create(MediaType.parse("text/plain"), dept_id);
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), s);
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), currentDate);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), description);

        ApiInterface service = RetrofitClient.getClient().create(ApiInterface.class);
        Call<AddTicketModel> call = service.createTicket(user_id,api_key,user_name,sub,projectId,deptId,status,date,message,body);
        call.enqueue(new Callback<AddTicketModel>() {
            @Override
            public void onResponse(Call<AddTicketModel> call, Response<AddTicketModel> response) {
                dialog.dismiss();
                if (response.body().getSuccess() == 200) {
                    ed_description.setText("");
                    tv_Attachment_name.setText("");
                    ToastCustom.toastSuccess(AddTicketScreen.this, response.body().getMessage());
                    finish();
                }
                else {
                    ToastCustom.toastError(AddTicketScreen.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<AddTicketModel> call, Throwable t) {
                dialog.dismiss();
                ToastCustom.toastError(AddTicketScreen.this, t.getMessage());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_attachment_name:
                /****** Add ticket Image ******/
                addImage();
                break;
            case R.id.btn_create_new:
                /*****Hide soft keyboard*****/
                InputMethodManager immm = (InputMethodManager)this. getSystemService(Context.INPUT_METHOD_SERVICE);
                immm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                /******** Api call to add new ticket *******/
                createNewTicket();
                 break;

            case R.id.back_btn:
                super.onBackPressed();
                break;

            case R.id.logout:
                /******** Clear data from the shared preference **********/
                SharedPrefHelper.getInstance(SharedPrefHelper.USER_INFO_PREF).logoutUser();
                finishAffinity();
                break;
                default:
                    break;
        }
    }

    /******* ask for the permission and than open gallery intent ********/
    private void addImage() {
        requestPermission();
        Intent intent_gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        File photoFile=null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.app.crmapp.fileprovider",
                    photoFile);
            intent_gallery.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent_gallery, GALLERY_RESULT_CODE);
        }
    }
    private File createImageFile() throws IOException {
        /****** Create file with timestamp *******/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imagePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        /******* Switch case on Result code******/
        switch (requestCode) {
            case GALLERY_RESULT_CODE:
                if (requestCode == GALLERY_RESULT_CODE) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagePath = cursor.getString(columnIndex);
                    cursor.close();
                    tv_Attachment_name.setText(imagePath + "\n");
                }
                break;
        }
    }

    private void createNewTicket() {
        /******After validation check addNewTicket() will executed******/
        String username = userName.getText().toString().trim();
        String subject = ed_Subject.getText().toString().trim();
        String description = ed_description.getText().toString().trim();
        if(TextUtils.isEmpty(subject)){
            ToastCustom.toastInfo(this,getString(R.string.ticket_subject));
        }
        else if(TextUtils.isEmpty(description)){
            ToastCustom.toastInfo(this,getString(R.string.ticket_description));
        }
        else if(!ConnectionDetection.isInternetAvailable(this)){
            ToastCustom.toastError(this,getString(R.string.network_error));
        }
        else{
            addNewTicket(userId,Urls.API_KEY, username, subject, project_Id,dept_Id,"1", currentDate,description,imagePath);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
