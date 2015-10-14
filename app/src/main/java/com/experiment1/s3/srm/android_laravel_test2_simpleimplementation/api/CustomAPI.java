package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.LoginMessage;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Token;
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




    @GET("/api/v1/projects") //get projects
    public void getProjectList(
            @Query("access_token") String access_token
            ,@Query("token_type")  String token_type
            , Callback<List<Project>> callBack);// login request and get project list



    @FormUrlEncoded
    @POST("api/permits") // store permit general tab
    public void storeGeneralTabToPermitTabel(
            @Field("permit_name") String permit_name,
            @Field("permit_template_id") int permit_template_id,
            @Field("project_id") int project_id,
            @Field("project_name") String project_name,
            @Field("permit_no") String permit_no,
            @Field("location") String location,
            @Field("contractor") String contractor,
            @Field("work_activity") String work_activity,
            @Field("permit_date") String permit_date,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time
            ,Callback<ServerMessage> callback
    );



//    @FormUrlEncoded
//    @POST("/permit/store") // store permit general tab
//    public Permit storeGeneralTabToPermitTabelWithPermitRet(
//            @Field("permit_name") String permit_name,
//            @Field("permit_template_id") int permit_template_id,
//            @Field("project_id") int project_id,
//            @Field("project_name") String project_name,
//            @Field("permit_no") String permit_no,
//            @Field("location") String location,
//            @Field("contractor") String contractor,
//            @Field("work_activity") String work_activity,
//            @Field("permit_date") String permit_date,
//            @Field("start_time") String start_time,
//            @Field("end_time") String end_time
//            ,Callback<PermitStoreToServer> callback
//    );




}