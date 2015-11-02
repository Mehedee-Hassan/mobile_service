package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.LoginMessage;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitPermission;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplateDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Token;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.data.PermitForDataRet;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.message.PermitStoreToServer;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.message.ServerMessage;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Mhr on 9/22/2015.
 */


public interface CustomAPI{

    //save as it is
    @FormUrlEncoded
    @POST("/oauth/access_token") //get access token
    public void getAccessToken(@Field("grant_type") String grant_type
            ,@Field("client_id") String client_id
            ,@Field("client_secret") String client_secret
            ,@Field("username") String username
            ,@Field("password") String password
            ,@Field("redirect_uri") String redirect_uri
            ,Callback<Token> callback); //login token request


    @GET("/api/v1/login") //get login
    public void loginWithAccessToken(
            @Query("access_token") String access_token
            , @Query("token_type") String token_type
            , Callback<LoginMessage> callBack);// login request


    @GET("/api/v1/permits/details_download") //get projects
    public void getPermitDetails(
            @Query("access_token") String access_token,
            @Query("token_type")  String token_type

            ,Callback<List<PermitDetails>> callback);// login request and get permit list where status ==  validate
    @GET("/api/v1/permits_download") //get projects
    public void getPermit(
            @Query("access_token") String access_token,
            @Query("token_type")  String token_type


            ,Callback<List<PermitForDataRet>> callback);// login request and get permit list where status ==  validate




    @GET("/api/v1/projects") //get projects
    public void getProjectList(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            , Callback<List<Project>> callBack);// login request and get project list



    @GET("/api/v1/permit/template")
    public void getPermitTemplateList(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            , Callback<List<PermitTemplate>> callBack);// login request and get permit template



    @GET("/api/v1/permit/template/details")
    public void getPermitTemplateDetailsList(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            , Callback<List<PermitTemplateDetails>> callBack);// login request and get permit template details list



    @GET("/api/v1/permits/upload")
    public void storeGeneralTabToPermitTable(
            @Query("access_token") String access_token,
            @Query("token_type") String token_type,
            @Query("permit_no") String permit_no,
            @Query("project_id") int project_id,
            @Query("project_name") String project_name,
            @Query("permit_template_id") int permit_template_id,
            @Query("permit_name") String permit_name,
            @Query("contractor") String contractor,
            @Query("location") String location,
            @Query("work_activity") String work_activity,
            @Query("permit_date") String permit_date,
            @Query("start_time") int start_time,
            @Query("end_time") int end_time,
            @Query("created_by") int created_by,
            @Query("status") String status,
            @Query("permit_server_id") long permit_server_id

            , Callback<List<ServerMessage>> callback);// login request and send permits


    @GET("/api/v1/permits/download") //get projects
    public void retrieveGeneralTabToPermitTable(
            @Query("access_token") String access_token,
            @Query("token_type")  String token_type,
            @Query("permit_no") String permit_no,
            @Query("project_id") int project_id,
            @Query("project_name") String project_name,
            @Query("permit_template_id") int permit_template_id,
            @Query("permit_name") String permit_name,
            @Query("contractor") String contractor,
            @Query("location") String location,
            @Query("work_activity") String work_activity,
            @Query("permit_date") String permit_date,
            @Query("start_time") int start_time,
            @Query("end_time") int end_time,
            @Query("created_by") int created_by,
            @Query("status") String status


            ,Callback<List<ServerMessage>> callback);// login request and get permit list where status ==  validate




    @GET("/api/v1/permits/details_upload") //get projects
    public void sendPermitDetails(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            ,@Query("permit_id") long permit_id
            ,@Query("sno") int sno
            ,@Query("question") String question
            ,@Query("allowed_text") int allowed_text
            ,@Query("extra_text") String extra_text
            ,@Query("status") String status
            ,@Query("server_id") long server_id
            , Callback<List<ServerMessage>> callBack);// login request and get project list


    @GET("/api/v1/permit/permission/upload")
    public void sendPermitPermission(
            @Query("access_token") String access_token,
            @Query("token_type")  String token_type,
            @Query("user_id") int user_id,
            @Query("permit_id") long permit_id,
            @Query("status") String status //

//            first stauts = submitted, second status = validated/rejected , third status = approved/rejected
            , Callback<List<ServerMessage>> callBack
    );


    @GET("/api/v1/permit/permission_download")
    public void getPermitPermission(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            , Callback<List<PermitPermission>> callBack);


}