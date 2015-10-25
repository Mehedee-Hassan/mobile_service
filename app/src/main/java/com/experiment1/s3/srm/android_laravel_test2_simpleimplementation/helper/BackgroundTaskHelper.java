package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api.CustomAPI;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Flags;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter.ProjectActivityListViewAdapter;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.ProjectDatabaseHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.LoginMessage;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitPermission;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Token;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.message.ServerMessage;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.LoginActivity;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.ProjectActivity;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.SplashActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mhr on 9/22/2015.
 */
public class BackgroundTaskHelper  {



    private String LOCAL_BASE_URL ;
    private String TAG = this.getClass().getSimpleName();

    RestAdapter restAdapter;
    CustomAPI loginApi;
    SaveDataHelper saveDataHelper ;


    GlobalVars globalVars ;

    //Flags.tokenReceiveSuccessFlag
    //===
    //=0 //no state
    //=1 //true
    //=2 //false



//    public BackgroundTaskHelper(){}

    public BackgroundTaskHelper(Activity activity){

        globalVars = (GlobalVars) activity.getApplication();


        saveDataHelper = new SaveDataHelper(activity);

        this.LOCAL_BASE_URL = saveDataHelper.getBaseUrl();


        Flags.tokenReceiveSuccessFlag = 0;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOCAL_BASE_URL)  //call your base url
                .build();
        loginApi = restAdapter.create(CustomAPI.class);



    }

    public BackgroundTaskHelper(SaveDataHelper saveDataHelper,Activity activity){

        globalVars = (GlobalVars) activity.getApplication();

        saveDataHelper = new SaveDataHelper(activity);
        this.LOCAL_BASE_URL = saveDataHelper.getBaseUrl();


        Flags.tokenReceiveSuccessFlag = 0;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOCAL_BASE_URL)  //call your base url
                .build();
        loginApi = restAdapter.create(CustomAPI.class);

        this.saveDataHelper = saveDataHelper;


    }


    public int tokenHelper3(final String username ,final String password ,final Activity loginActivity
            , final SaveDataHelper saveDataHelper, final Activity activity
            , final boolean isReqFromLoginDialog){




        checkIfLoggedIn();



                String client_id = saveDataHelper.getPrefClientId();
                String client_secret  = saveDataHelper.getPrefClientSecret();

        Log.d("now==", "get access");
                loginApi.getAccessToken(Constants.grant_type
                        , client_id
                        , client_secret //Constants.CLIENT_SECRET
                        , username///Constants.username//username
                        , password
                        , Constants.redirectUrl  //no affect yet or nt used
                        , new Callback<Token>()

                {




            @Override
            public void success(Token token, Response response) {
                //System.out.println(token.access_token + "  response = " + response.toString());

                Constants.access_token = token.access_token;
                Constants.token_type = token.token_type;




                saveDataHelper.saveToken(token.access_token, token.token_type);
                saveDataHelper.savePrefUsername(username);
                saveDataHelper.savePrefPassword(password);


                Flags.tokenReceiveSuccessFlag = 1;

                Toast.makeText(loginActivity.getApplicationContext()
                        ,"Got Access Token\n"+Constants.access_token ,Toast.LENGTH_SHORT).show();

                Flags.LOGIN_SUCCESS_FLAG = true;





                    loginHelper3(token.access_token
                            , token.token_type
                            , activity
                            , isReqFromLoginDialog

                    );



            }

            public void failure(RetrofitError retrofitError) {

                Log.d("now==get" ,""+retrofitError.getMessage());
                Log.d("===error ===", retrofitError.toString());

                Flags.tokenReceiveSuccessFlag = 2;

                Flags.LOGIN_SUCCESS_FLAG = false;


                Toast.makeText(loginActivity.getApplicationContext()
                        ,"Error!!!  Please check Internet Connection" +
                        "\n Please check client permit_id and Secret\n"+retrofitError.getMessage() ,Toast.LENGTH_SHORT).show();


            }


        });

        return Flags.tokenReceiveSuccessFlag;
    }




    public void loadProjectList(final ArrayAdapter adapter ,String access_token ,String token_type,
                                ListView listView){


        Log.d("now ","load project list");



        restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOCAL_BASE_URL)  //call your base url
                .build();

        loginApi = restAdapter.create(CustomAPI.class);

        if(Constants.access_token == "" || Constants.token_type =="")
            return ;



        loginApi.getProjectList(Constants.access_token ,Constants.token_type,new Callback<List<Project>>(){




            @Override
            public void success(List<Project> projects, Response response) {

                Log.d("now ", "load project list success");

                for(Project project : projects){
                    Log.d("now ", project.name);

                }

                adapter.clear();

                List<String> temp = new ArrayList<String>();

                int i = 0 ;


                for(Project project : projects){
                    temp.add(project.name);
                    adapter.add(project.name);
                }



                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("now ","load project list fail");

            }
        });





    }






    //=========================================
    //
    //=========================================
    boolean returnTokenIsOk = false;
    public boolean loginHelper3(final String access_token
            , final String token_type
            , final Activity activity
            , final boolean ifRequestFromLoginDialogSubmitActivity
            )  {

        returnTokenIsOk = false;

        Log.d("==now here ==", "in login3");



        RestAdapter restAdapter;
        CustomAPI loginApi;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOCAL_BASE_URL)  //call your base url
                .build();

        loginApi = restAdapter.create(CustomAPI.class);
        if(access_token == "" || token_type =="")
            return false;


        Log.d("==now here ==", "in method"+Constants.access_token);

//        loginApi.loginWithAccessToken(access_token,
//                token_type, projectCallBack);


        loginApi.loginWithAccessToken(access_token,
                token_type, new Callback<LoginMessage>() {

                    public void failure(RetrofitError arg0) {
                        returnTokenIsOk = false;
                        globalVars.setIfLoggedIn(false);





                        Flags.LOGIN_SUCCESS_FLAG = false;

                        Toast.makeText(
                                activity.getApplicationContext()
                                , "Login Failed.\n" + arg0.getMessage()
                                , Toast.LENGTH_LONG).show();

                        activity.findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                        Log.d("===login with token==", "error = " + arg0.getMessage());
                        //set login false

                        globalVars.setIfLoggedIn(false);

                        //set user role [logged in for ui]


//
//
// saveDataHelper.setIfLoggedIn(false);

                    }

                    public void success(LoginMessage loginMessage, Response arg1) {

                        //set login true
                        globalVars.setIfLoggedIn(true);
                        saveDataHelper.setIfLoggedIn(true);

//                        globalVars.setCurrentUserRole();
                        saveDataHelper.setCurrentUserRole(loginMessage.user_role);
                        saveDataHelper.setCurrentUserId(loginMessage.user_id);


                        Log.d("login string == ", loginMessage.message);
                        Log.d("login string == ", ""+loginMessage.user_role);
                        returnTokenIsOk = true;




                        Flags.LOGIN_SUCCESS_FLAG = true;

                        if (Flags.LOGIN_SUCCESS_FLAG) {


                            //set constatns
                            Constants.access_token = access_token;
                            Constants.token_type = token_type;


                            if (ifRequestFromLoginDialogSubmitActivity == false) {
                                //start service for the first time
                                Intent intent2 = new Intent();
                                intent2.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.BACKGROUND_DATA_LOADING_INTENT_SERVICE");
                                activity.startService(intent2);
                                //start activity


                                Intent intent1 = new Intent(activity, ProjectActivity.class);
                                activity.startActivity(intent1);


                            } else if (ifRequestFromLoginDialogSubmitActivity) {
                                activity.finish();
                            }


                            Log.d("==now here ==", "login helper flag true");
                        } else
                            Log.d("==now here ==", "false");


                        activity.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                        //start activity for project list


                    }


                });


        return returnTokenIsOk;

    }




    //==========================================
    //
    //==========================================



    public boolean loginHelper4Dialog(final String access_token
            , final String token_type
            , final Activity activity )  {

        Log.d("==now here ==", "in login3");



        RestAdapter restAdapter;
        CustomAPI loginApi;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOCAL_BASE_URL)  //call your base url
                .build();

        loginApi = restAdapter.create(CustomAPI.class);
        if(access_token == "" || token_type =="")
            return false;


        Log.d("==now here ==", "in method"+Constants.access_token);

//        loginApi.loginWithAccessToken(access_token,
//                token_type, projectCallBack);


        //todo check if logged in
        boolean ifLoggedin = this.checkIfLoggedIn();



        if(!ifLoggedin) {
            loginApi.loginWithAccessToken(access_token,
                    token_type, new Callback<LoginMessage>() {

                        public void failure(RetrofitError arg0) {


                            Flags.LOGIN_SUCCESS_FLAG = false;

                            Toast.makeText(
                                    activity.getApplicationContext()
                                    , "Login Failed.\n" + arg0.getMessage()
                                    , Toast.LENGTH_LONG).show();

                            activity.findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                            Log.d("===login with token==", "error = " + arg0.getMessage());

                        }

                        public void success(LoginMessage loginMessage, Response arg1) {
                            Log.d("login string = ", loginMessage.message);


                            Flags.LOGIN_SUCCESS_FLAG = true;

                            if (Flags.LOGIN_SUCCESS_FLAG) {


                                //set constatns
                                Constants.access_token = access_token;
                                Constants.token_type = token_type;


                                //start service for the first time
                                Intent intent2 = new Intent();
                                intent2.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.BACKGROUND_DATA_LOADING_INTENT_SERVICE");
                                activity.startService(intent2);
                                //===




                                Log.d("==now here ==", "login helper flag true");
                            } else
                                Log.d("==now here ==", "false");


                            activity.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                        }


                    });
        }

        return true;

    }

    private boolean checkIfLoggedIn() {


        boolean ifLoggedIn = false;


        String access_token = saveDataHelper.getAccessTokenDetails()[0];






        return ifLoggedIn;
    }


    public void getLoginToCheckIfLoggedIn(final SplashActivity activity, final String[] token) {



        Log.d("==now here ==", "in method"+Constants.access_token);



        loginApi.loginWithAccessToken(token[0],
                token[1], new Callback<LoginMessage>() {

                    public void failure(RetrofitError arg0) {


                        Flags.LOGIN_SUCCESS_FLAG = false;

                        Toast.makeText(
                                activity.getApplicationContext()
                                , "Please Login", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(activity, LoginActivity.class);
                        // intent.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.LOGIN_ACTIVITY");
                        activity.startActivity(intent);

                        //im done with the activity splash
                        activity.finish();


                        Log.d("===login with token==", "error = " + arg0.getMessage());

                    }

                    public void success(LoginMessage loginMessage, Response arg1) {
                        Log.d("login string = ", loginMessage.message);


                        Intent intent1 = new Intent(activity, ProjectActivity.class);

                        //set constatns
                        Constants.access_token = token[0];
                        Constants.token_type = token[1];

                        //intent1.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.PROJECT_ACTIVITY");

                        activity.startActivity(intent1);

                        //im done with the activity splash
                        activity.finish();

                        Log.d("==now here ==", "login helper flag true");

                    }


                });


    }



    public void loadProjectListFromDatabase(ProjectActivity projectActivity,
                                            ProjectDatabaseHelper databaseHelper
            , ArrayList<String> list
            , ProjectActivityListViewAdapter customAdapter
            ) {


        List<Project> projects;

        projects = databaseHelper.getProjects();

        list.clear();
        //customAdapter.notifyDataSetChanged();

        for (Project project : projects) {
            Log.d("now2 ", project.name);
            list.add(project.name);
        }

        customAdapter.notifyDataSetChanged();


    }



    public boolean loginHelper(String access_token ,String token_type
             )  {

        if(access_token == "" || token_type =="")
            return false;



        loginApi.loginWithAccessToken(access_token,
                token_type, new Callback<LoginMessage>() {

                    public void failure(RetrofitError arg0) {

                        //todo start activity for project list


//                        Toast.makeText(
//                                loginActivity.getApplicationContext()
//                                ,"Login Failed.\n"+arg0.getMessage()
//                                ,Toast.LENGTH_LONG).show();


                        Log.d("===login with token==", "error = " + arg0.getMessage());

                    }

                    public void success(LoginMessage loginMessage, Response arg1) {
                        Log.d("login string = ", loginMessage.message);


//                            Toast.makeText(
//                                    loginActivity.getApplicationContext()
//                                    ,"Welcome!!" ,Toast.LENGTH_LONG).show();

                    }


                });


        return true;

    }

    public boolean loginHelper234(String access_token ,String token_type, final Context context
    )  {

        if(access_token == "" || token_type =="")
            return false;



        loginApi.loginWithAccessToken(access_token,
                token_type, new Callback<LoginMessage>() {

                    public void failure(RetrofitError arg0) {

                        //todo start activity for project list


                        Toast.makeText(
                                context
                                , "Login Failed.\n" + arg0.getMessage()
                                , Toast.LENGTH_LONG).show();


                        Log.d("===login with token==", "error = " + arg0.getMessage());

                    }

                    public void success(LoginMessage loginMessage, Response arg1) {
                        Log.d("login string = ", loginMessage.message);


                        Toast.makeText(
                                context
                                , "Welcome!!", Toast.LENGTH_LONG).show();


                    }


                });


        return true;

    }

    //test token helper method
    //todo delete
    public int tokenHelper() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://192.168.1.100/php/laravel/test/android_laravel_v1/laravel/public")  //call your base url
                .build();


        loginApi = restAdapter.create(CustomAPI.class);


        Log.d("now==", "get access");
        loginApi.getAccessToken("password", "2", "tt", "user1@gmail.com", "pass@word1"
                , "/home", new Callback<Token>()

        {

            @Override
            public void success(Token token, Response response) {
                Log.d("now", "success");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("now", "fail");

            }
        });

        return  1;
    }


    public void dataloadingForService() {
    }



    public void saveToPermitTable(Permit permit, final Activity submitActivity , final List<PermitDetails> permitDetailsesList
            , final PermitPermission permitPermission ,int state_reject) {

        //todo delete temporary
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOCAL_BASE_URL)  //call your base url
                .build();

        loginApi = restAdapter.create(CustomAPI.class);
        //


        if(state_reject == Constants.state_reject_login_nvalidate){
            permitPermission.status = Constants.PERMIT_STATUS_VALIDATE_REJECT;
            saveToPermitPermissionToServer(permitPermission ,globalVars.getPermit().server_permit_id ,submitActivity);

            return;

        }
        else
        if(state_reject == Constants.state_reject_login_napprove){
            permitPermission.status = Constants.PERMIT_STATUS_VALIDATE_REJECT;

            saveToPermitPermissionToServer(permitPermission ,globalVars.getPermit().server_permit_id ,submitActivity);

            return;

        }
        else if(state_reject == Constants.state_reject_login_approve){

            permitPermission.status = Constants.PERMIT_STATUS_APPROVED;

        }


        Log.d("== submit button" ,"clicked" + "project_id = "+permit.project_id);
        Log.d("== submit button" ,"clicked" + " permit_server_id = "+permit.server_permit_id);

        int startTime = Integer.parseInt(permit.start_time);
        int endTime = Integer.parseInt(permit.end_time);

        loginApi.storeGeneralTabToPermitTable(
                Constants.access_token,
                Constants.token_type
                , permit.auto_gen_permit_no
                , permit.project_id
                , permit.project_name
                , permit.permit_template_id
                , permit.permit_name
                , permit.contractor
                , permit.location
                , permit.work_activity
                , permit.permit_date
                , startTime
                , endTime
                , permit.created_by
                , permit.status
                , permit.server_permit_id
                , new Callback<List<ServerMessage>>() {
                    @Override
                    public void success(List<ServerMessage> serverReturnMessage, Response response) {


                        Toast.makeText(submitActivity, "Permit Saved", Toast.LENGTH_LONG);
                        Log.d(TAG + " == back task ", " success saveToPermitTable =" + serverReturnMessage.get(1).message);


                        // getting server permit id to submit checklist
                        globalVars.getPermit().server_permit_id = Integer.parseInt(serverReturnMessage.get(1).message);
                        long permitId = Integer.parseInt(serverReturnMessage.get(1).message);

                        //get the server permit id
                        //then send the checklist permit details to keep server id on permit derails

                        saveToPermitDetailsToserver(permitDetailsesList, permitId,submitActivity);


                        saveToPermitPermissionToServer(permitPermission ,permitId ,submitActivity);

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.d(TAG + " == back task ", " fail saveToPermitTable =" + error.getMessage());

                    }
                }
        );


    }



    private void saveToPermitDetailsToserver(List<PermitDetails> returnedPermitDetailsObjectList ,long server_id, Activity activity) {




        Log.d(TAG+" == ", " submit act ,save to permit");

//
//        Calendar calendar = Calendar.getInstance();


        for(PermitDetails permitDetails : returnedPermitDetailsObjectList){

            permitDetails.permit_id = server_id;
            saveToPermitDetailsTable(permitDetails, activity);


        }

    }




    public void saveToPermitPermissionTable(PermitPermission permitPermission, final Activity submitActivity) {

        //todo delete temporary
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOCAL_BASE_URL)  //call your base url
                .build();

        loginApi = restAdapter.create(CustomAPI.class);


        Log.d(TAG + " == ", "permit permission requesting ....  ");


        loginApi.sendPermitPermission(
                Constants.access_token,
                Constants.token_type
                ,permitPermission.user_id
                ,permitPermission.permit_id
                ,permitPermission.status
                ,new Callback<List<ServerMessage>>(){
                    @Override
                    public void success(List<ServerMessage> serverReturnMessage, Response response) {


                        Toast.makeText(submitActivity, "Permit Saved", Toast.LENGTH_LONG);
                        Log.d(TAG + " == back task 3", " permission permit success saveToPermitPermissionTable =" + serverReturnMessage.get(0).message);

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.d(TAG + " == back task 3", "permit permission fail saveToPermitPermissionTable =" + error.getMessage());

                    }
                }
        );


    }



    public void saveToPermitDetailsTable(PermitDetails permitDetails, final Activity submitActivity) {

        //todo delete temporary
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOCAL_BASE_URL)  //call your base url
                .build();

        loginApi = restAdapter.create(CustomAPI.class);
        //



        Log.d("== submit button" ," check list clicked" + "status = "+permitDetails.status  +" permit id = "+ permitDetails.permit_id
                +"sno = "+ permitDetails.sno
                + "question =" + permitDetails.question
                + "allowed text = " + permitDetails.allowed_text
                + "extra text = " +permitDetails.extra_text
                + "status = "+permitDetails.status);




//        permitDetails.server_id = 0;


        loginApi.sendPermitDetails(
                Constants.access_token,
                Constants.token_type
                , permitDetails.permit_id
                , permitDetails.sno
                , permitDetails.question
                , permitDetails.allowed_text
                , permitDetails.extra_text
                , permitDetails.status
                , permitDetails.server_id
                , new Callback<List<ServerMessage>>() {
                    @Override
                    public void success(List<ServerMessage> serverReturnMessage, Response response) {


                        Toast.makeText(submitActivity, "Permit Saved", Toast.LENGTH_LONG);
                        Log.d(TAG + " == back task 2", " success saveToPermitDetailsTable " + serverReturnMessage.get(0).message);

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.d(TAG + " == back task 2 ", " fail saveToPermitDetailsTable " + error.getMessage());

                    }
                }
        );
    }



    private void saveToPermitPermissionToServer(PermitPermission returnedPermitObject,long id , Activity activity) {




        Log.d(TAG+" == ", " submit act ,save to permit");

//
//        Calendar calendar = Calendar.getInstance();


        returnedPermitObject.permit_id = id;
        returnedPermitObject.user_id = saveDataHelper.getCurrentUserId();
//        returnedPermitObject.created_by = globalVars.getCurrentLoggedInUser().permit_id; //todo change temporary




        saveToPermitPermissionTable(returnedPermitObject, activity);


    }

}
