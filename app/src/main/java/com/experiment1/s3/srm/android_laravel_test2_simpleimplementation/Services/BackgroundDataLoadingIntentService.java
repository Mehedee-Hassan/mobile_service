package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.services;

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
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.PermitDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.PermitTemplateDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.ProjectDatabaseHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitPermission;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplateDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.data.PermitForDataRet;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BackgroundDataLoadingIntentService extends IntentService {



    BackgroundTaskHelper backgroundTaskHelper;
    SaveDataHelper saveDataHelper;
    final PermitTemplateDBHelper permitTemplateDBHelper;

    PermitDBHelper permitDBHelper;

    public BackgroundDataLoadingIntentService() {
        super("BackgroundDataLoadingIntentService");

         permitTemplateDBHelper =
                new PermitTemplateDBHelper(this);


    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("net con", "changed");


        if(isNetworkAvailable(this)){
            saveDataHelper = new SaveDataHelper(this);

            getProjectListFromServer(saveDataHelper);

            if(saveDataHelper.getCurrentUserRole() == 3) {
                getPermitTemplateListFromServer(saveDataHelper);
                getPermitTemplateDetailsListFromServer(saveDataHelper);
                getPermitPermissionFromServer(saveDataHelper);
                //stop after data loading finished
            }
            else if(saveDataHelper.getCurrentUserRole() == 2 || saveDataHelper.getCurrentUserRole() == 1 ){

                permitDBHelper = new PermitDBHelper(this);

                getPermitListFromServer(saveDataHelper);
                getPermitDetailsListFromServer(saveDataHelper);
            }


            stopSelf();
        }
        else {
            stopSelf();
        }


    }


    public void getProjectListFromServer(SaveDataHelper saveDataHelper) {

        RestAdapter restAdapter;
        CustomAPI loginApi;
        final ProjectDatabaseHelper projectDatabaseHelper = new ProjectDatabaseHelper(this);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();


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
                        Log.d("starting==", "ui update===");
                        Intent intent = new Intent();
                        intent.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.PROJECT_ACTIVITY_LIST_VIEW_UPDATE");
                        sendBroadcast(intent);


                    }


                });



    }


    private void getPermitDetailsListFromServer(SaveDataHelper saveDataHelper) {


        RestAdapter restAdapter;
        CustomAPI loginApi;


        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();


        loginApi = restAdapter.create(CustomAPI.class);


        String[] token = saveDataHelper.getAccessTokenDetails();

        Log.d("===service login  ==", " permit token = " + token[0]);

        loginApi.getPermitDetails(token[0],
                token[1]
                , new Callback<List<PermitDetails>>() {

                    public void failure(RetrofitError arg0) {
                        Log.d("===service login  ==", " permit details= " + arg0.getMessage());

                    }

                    public void success(List<PermitDetails> permitDetailsList, Response arg1) {
                        Log.d("login string service= ", " permit details,success = " + permitDetailsList.get(0).question+" --- +++ size = "+permitDetailsList.size());


                        permitDBHelper.saveToPermitDetailsTable(permitDetailsList);

                    }


                });


    }


    private void getPermitListFromServer(SaveDataHelper saveDataHelper) {
        RestAdapter restAdapter;
        CustomAPI loginApi;


        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();


        loginApi = restAdapter.create(CustomAPI.class);


        String[] token = saveDataHelper.getAccessTokenDetails();

        Log.d("===service login  ==", " permit token = " + token[0]);

        loginApi.getPermit(token[0],
                token[1]
                , new Callback<List<PermitForDataRet>>() {

                    public void failure(RetrofitError arg0) {
                        Log.d("===service login  ==", " permit = " + arg0.getMessage());

                    }

                    public void success(List<PermitForDataRet> permitForDataRetList, Response arg1) {
                        Log.d("login string service= ", " permit ,success = " + permitForDataRetList.get(0).permit_no);


                        permitDBHelper.saveToPermitTable(permitForDataRetList);


                    }


                });



    }

    public void getPermitTemplateListFromServer(SaveDataHelper saveDataHelper) {

        RestAdapter restAdapter;
        CustomAPI loginApi;
//        final PermitTemplateDBHelper permitTemplateDBHelper =
//                new PermitTemplateDBHelper(this);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();


        loginApi = restAdapter.create(CustomAPI.class);


        String[] token = saveDataHelper.getAccessTokenDetails();

        loginApi.getPermitTemplateList(token[0],
                token[1], new Callback<List<PermitTemplate>>() {

                    public void failure(RetrofitError arg0) {
                        Log.d("===service login  ==", " permit template error = " + arg0.getMessage());

                    }

                    public void success(List<PermitTemplate> permitTemplate, Response arg1) {
                        Log.d("login string service= ", " permit template  ,success");

//                        databaseHelper.saveProjects(projects);
                        permitTemplateDBHelper.insertPermitTemplate(permitTemplate);


                    }


                });


    }

    public void getPermitTemplateDetailsListFromServer(SaveDataHelper saveDataHelper) {

        RestAdapter restAdapter;
        CustomAPI loginApi;
//        final PermitTemplateDBHelper permitTemplateDBHelper =
//                new PermitTemplateDBHelper(this);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();


        loginApi = restAdapter.create(CustomAPI.class);


        String[] token = saveDataHelper.getAccessTokenDetails();

        loginApi.getPermitTemplateDetailsList(token[0],
                token[1], new Callback<List<PermitTemplateDetails>>() {

                    public void failure(RetrofitError arg0) {
                        Log.d("===service login  ==", " permit template details error = " + arg0.getMessage());

                    }

                    public void success(List<PermitTemplateDetails> permitTemplateDetails, Response arg1) {
                        Log.d("login string service= ", " permit template details ,success");

//                        databaseHelper.saveProjects(projects);
                        permitTemplateDBHelper.insertPermitTemplateDetails(permitTemplateDetails);


                    }


                });



    }


    public void getPermitPermissionFromServer(SaveDataHelper saveDataHelper) {

        RestAdapter restAdapter;
        CustomAPI loginApi;


        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();


        loginApi = restAdapter.create(CustomAPI.class);


        String[] token = saveDataHelper.getAccessTokenDetails();

        Log.d("===service login  ==", " permit permission error token = " + token[0]);

        loginApi.getPermitPermission(token[0],
                token[1]
                , new Callback<List<PermitPermission>>() {

                    public void failure(RetrofitError arg0) {
                        Log.d("===service login  ==", " permit permission error = " + arg0.getMessage());

                    }

                    public void success(List<PermitPermission> permitPermissionList, Response arg1) {
                        Log.d("login string service= ", " permit permission ,success = " + permitPermissionList.get(0).status);


                        permitTemplateDBHelper.insertPermitPermission(permitPermissionList);


                    }


                });



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
