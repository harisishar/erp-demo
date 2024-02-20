package com.app.crmapp.ApiCalls;

import com.app.crmapp.Models.AddTicketModel;
import com.app.crmapp.Models.CompanyData;
import com.app.crmapp.Models.CompanyProfile;
import com.app.crmapp.Models.ContractDetail;
import com.app.crmapp.Models.DepartmentModel;
import com.app.crmapp.Models.InvoiceDetail;
import com.app.crmapp.Models.Login;
import com.app.crmapp.Models.ProjectData;
import com.app.crmapp.Models.ProjectModel;
import com.app.crmapp.Models.ProposalDetail;
import com.app.crmapp.Models.ReplyDetailModel;
import com.app.crmapp.Models.SupportImageModel;
import com.app.crmapp.Models.SupportTicket;
import com.app.crmapp.Models.TicketImageModel;
import com.app.crmapp.Models.TicketStatus;
import com.app.crmapp.Models.UpdateCompanyProfileModel;
import com.app.crmapp.Models.UserInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LBMSOLUTIONS on 23-10-2019.
 */

public interface ApiInterface {
    public static final String PROFILE_IMAGE=Urls.BASE_URL+"/uploads/client_profile_images/4/thumb_";
    public static final String LOGO_BASE=Urls.BASE_URL+"/uploads/company/";
    public static final String TICKETPROFILE = Urls.BASE_URL+"/uploads/ticket_attachments/";

    @FormUrlEncoded
    @POST("/api/mapi/applogin")
    Call<Login> loginUser(@Field("api_key") String key, @Field("email") String email, @Field("password") String password);

    @GET("/api/mapi/companyDetail/{userid}")
    Call<CompanyProfile> getProfile(@Path("userid") String userId, @Query("api_key") String key);

    @GET("/api/mapi/ticketDetail/{userid}")
    Call<SupportTicket> getSupportTicket(@Path("userid") String  userId, @Query("api_key") String key);

    @GET("/api/mapi/ticketImage")
    Call<TicketImageModel> getTicketImage(@Query("api_key") String key, @Query("ticketid") String  ticketId, @Query("replyid") String replyId);


    @GET("/api/mapi/ticketMainImage")
    Call<SupportImageModel> getTicketSupportImage(@Query("api_key") String key, @Query("ticketid") String  ticketId);

    @GET("/api/mapi/proposalDetail/{userid}")
    Call<ProposalDetail> getProposalList(@Path("userid") String userId, @Query("api_key") String key);

    @FormUrlEncoded
    @POST("/api/mapi/updateProfile")
    Call<UpdateCompanyProfileModel> updateCompanyProfile(@Field("userid") String userId, @Field("api_key") String apiKey,
                                                         @Field("company") String company, @Field("gstin_number") String gstin_number,
                                                         @Field("phonenumber") String phonenumber, @Field("website") String website,
                                                         @Field("country") String country, @Field("city") String city, @Field("zip") String zip,
                                                         @Field("state") String state);

    @GET("/api/mapi/replyDetail/{userid}")
    Call<ReplyDetailModel> getreplyDetail(@Path("userid") String userId, @Query("api_key") String key);

    @Multipart
    @POST("/api/mapi/addTicketReply")
    Call<AddTicketModel> addTicketReply(@Part("ticketid") RequestBody ticketid, @Part("api_key") RequestBody api_key,
                                        @Part("message") RequestBody message, @Part("date") RequestBody date,
                                        @Part MultipartBody.Part file_name, @Part("contactid") RequestBody contactid);

    @GET("/api/mapi/projectDetail/{userid}")
    Call<ProjectData> getProjectDetail(@Path("userid") String userId,@Query("api_key")String key);

    @GET("/api/mapi/invoiceDetail")
    Call<InvoiceDetail> getInvoiceDetail(@Query("clientid") String userId, @Query("api_key")String key);

    @GET("/api/mapi/contractDetail/{clientid}")
    Call<ContractDetail> getContractDetail(@Path("clientid") String userId, @Query("api_key")String key);

    @Multipart
    @POST("/api/mapi/addTicket")
    Call<AddTicketModel> createTicket(@Part("userid") RequestBody user_id, @Part("api_key") RequestBody api_key, @Part("name")RequestBody user_name,
                                 @Part("subject")RequestBody sub, @Part("projectid") RequestBody projectId, @Part("department") RequestBody deptId,
                                 @Part("status")RequestBody status, @Part("date")RequestBody date, @Part("message")RequestBody msg, @Part MultipartBody.Part file);

    @GET("/api/mapi/department")
    Call<DepartmentModel> getDepartment(@Query("api_key")String apiKey);

    @GET("/api/mapi/projectList/{userid}")
    Call<ProjectModel> getProjectList(@Path("userid")String userId,@Query("api_key")String apiKey);

    @GET("/api/mapi/LogoDetail/{id}")
    Call<CompanyData> getCompanyData(@Path("id") String s, @Query("api_key") String apiKey);

    @GET("/api/mapi/UserDetail/{userid}")
    Call<UserInfo> getUserInfo(@Path("userid") String userId, @Query("api_key") String apiKey);

   /* @GET("/api/mapi/ticketStatus")
    Call<TicketStatus> getTicketStatus(@Query("api_key") String apiKey);*/
}
