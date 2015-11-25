package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.LoginMessage;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitPermission;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplateDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Token;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.ret.PermitForDataRet;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.message.ServerMessage;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
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



    @FormUrlEncoded
    @POST("/mobile.api/v1/login") //get login
    public void loginWithAccessToken(
            @Field("access_token") String access_token
            ,@Field("token_type") String token_type
            ,
            Callback<LoginMessage> callBack);// login request



    @GET("/mobile.api/v1/projects") //get projects
    public void getProjectList(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            , Callback<List<Project>> callBack);// login request and get project list



    @GET("/mobile.api/v1/permit/template")
    public void getPermitTemplateList(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            , Callback<List<PermitTemplate>> callBack);// login request and get permit template



    @GET("/mobile.api/v1/permit/template/details")
    public void getPermitTemplateDetailsList(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            , Callback<List<PermitTemplateDetails>> callBack);// login request and get permit template details list


    @GET("/mobile.api/v1/permits") //get projects
    public void getPermit(
            @Query("access_token") String access_token,
            @Query("token_type")  String token_type


            ,Callback<List<PermitForDataRet>> callback);// login request and get permit list where status ==  validate

    @FormUrlEncoded
    @POST("/mobile.api/v1/permits")
    public void storeGeneralTabToPermitTable(
            @Field("access_token") String access_token,
            @Field("token_type") String token_type,
            @Field("permit_no") String permit_no,
            @Field("project_id") int project_id,
            @Field("project_name") String project_name,
            @Field("permit_template_id") int permit_template_id,
            @Field("permit_name") String permit_name,
            @Field("contractor") String contractor,
            @Field("location") String location,
            @Field("work_activity") String work_activity,
            @Field("permit_date") String permit_date,
            @Field("start_time") int start_time,
            @Field("end_time") int end_time,
            @Field("created_by") int created_by,
            @Field("status") String status,
            @Field("permit_server_id") long permit_server_id

            , Callback<List<ServerMessage>> callback);


    @GET("/mobile.api/v1/permits/download")
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


    @GET("/mobile.api/v1/permits/details") //get projects
    public void getPermitDetails(
            @Query("access_token") String access_token,
            @Query("token_type")  String token_type

            ,Callback<List<PermitDetails>> callback);// login request and get permit list where status ==  validate



    @FormUrlEncoded
    @POST("/mobile.api/v1/permits/details")
    public void sendPermitDetails(
            @Field("access_token") String access_token
            ,@Field("token_type")  String token_type
            ,@Field("permit_id") long permit_id
            ,@Field("sno") int sno
            ,@Field("question") String question
            ,@Field("allowed_text") int allowed_text
            ,@Field("extra_text") String extra_text
            ,@Field("status") String status
            ,@Field("server_id") long server_id
            , Callback<List<ServerMessage>> callBack);


    @FormUrlEncoded
    @POST("/mobile.api/v1/permit/permission")
    public void sendPermitPermission(
            @Field("access_token") String access_token,
            @Field("token_type")  String token_type,
            @Field("user_id") int user_id,
            @Field("permit_id") long permit_id,
            @Field("status") String status //

//            first stauts = submitted, second status = validated/rejected , third status = approved/rejected
            , Callback<List<ServerMessage>> callBack
    );


    @GET("/mobile.api/v1/permit/permission")
    public void getPermitPermission(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            , Callback<List<PermitPermission>> callBack);


}