package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api.CustomAPI;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.BackgroundTaskHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.SaveDataHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.ProjectDatabaseHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BackgroundDataLoadingIntentService extends IntentService {



    BackgroundTaskHelper backgroundTaskHelper;
    SaveDataHelper saveDataHelper;
    private Object data;

    public BackgroundDataLoadingIntentService() {
        super("BackgroundDataLoadingIntentService");



    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("net con", "changed");


        if(isNetworkAvailable(this)){
            getData();

            //stop after data loading finished
            stopSelf();
        }
        else {
            stopSelf();
        }


    }


    public Object getData() {

        RestAdapter restAdapter;
        CustomAPI loginApi;
        final ProjectDatabaseHelper projectDatabaseHelper = new ProjectDatabaseHelper(this);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();

        saveDataHelper = new SaveDataHelper(this);

        loginApi = restAdapter.create(CustomAPI.class);


        String[] token = saveDataHelper.getAccessTokenDetails();

        loginApi.getProjectList(token[0],
                token[1], new Callback<List<Project>>() {

                    public void failure(RetrofitError arg0) {
                        Log.d("===service login ==", "error = " + arg0.getMessage());

                    }

                    public void success(List<Project> projects, Response arg1) {
                        Log.d("login string service= ", "success");

//                        databaseHelper.saveProjects(projects);
                        projectDatabaseHelper.insertProjects(projects);


//                      after insertion to sqlite database
                        Log.d("starting==","ui update===");
                        Intent intent = new Intent();
                        intent.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.PROJECT_ACTIVITY_LIST_VIEW_UPDATE");
                        sendBroadcast(intent);


                    }


                });



        return data;
    }


    private boolean isNetworkAvailable(Context context) {

        boolean n1 = false;
        boolean n2 = false;


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();

        for(NetworkInfo ni : networkInfos){
            if(ni.getTypeName().equalsIgnoreCase("WIFI")){
                if(ni.isConnected()){
                    n1  =true;
                }
            }
            if(ni.getTypeName().equalsIgnoreCase("MOBILE")){
                if(ni.isConnected()){
                    n2  =true;
                }
            }
        }


        return (n1 || n2);
    }

}
