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
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Token;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.message.PermitStoreToServer;
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

        Flags.tokenReceiveSuccessFlag = 0;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();
        loginApi = restAdapter.create(CustomAPI.class);



    }

    public BackgroundTaskHelper(SaveDataHelper saveDataHelper,Activity activity){

        globalVars = (GlobalVars) activity.getApplication();

        Flags.tokenReceiveSuccessFlag = 0;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();
        loginApi = restAdapter.create(CustomAPI.class);

        this.saveDataHelper = saveDataHelper;


    }


    public int tokenHelper3(String username ,String password ,final Activity loginActivity
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





                saveDataHelper.saveToken(token.access_token ,token.token_type);



                Flags.tokenReceiveSuccessFlag = 1;

                Toast.makeText(loginActivity.getApplicationContext()
                        ,"Got Access Token\n"+Constants.access_token ,Toast.LENGTH_SHORT).show();

                Flags.LOGIN_SUCCESS_FLAG = true;





                    loginHelper3(token.access_token
                            , token.token_type, activity,
                            isReqFromLoginDialog);



            }

            public void failure(RetrofitError retrofitError) {

                Log.d("now==get" ,""+retrofitError.getMessage());
                Log.d("===error ===", retrofitError.toString());

                Flags.tokenReceiveSuccessFlag = 2;

                Flags.LOGIN_SUCCESS_FLAG = false;


                Toast.makeText(loginActivity.getApplicationContext()
                        ,"Error!!!  Please check Internet Connection" +
                        "\n Please check client id and Secret\n"+retrofitError.getMessage() ,Toast.LENGTH_SHORT).show();


            }


        });

        return Flags.tokenReceiveSuccessFlag;
    }




    public void loadProjectList(final ArrayAdapter adapter ,String access_token ,String token_type,
                                ListView listView){


        Log.d("now ","load project list");



        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
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

//                adapter.clear();
//
//                List<String> temp = new ArrayList<String>();
//
//                int i = 0 ;
//
//
//                for(Project project : projects){
////                    temp.add(project.name);
////                    adapter.add(project.name);
//                }



//                adapter.notifyDataSetChanged();
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
            , final Activity activity,
                                final boolean ifRequestFromLoginDialogSubmitActivity)  {

        returnTokenIsOk = false;

        Log.d("==now here ==", "in login3");



        RestAdapter restAdapter;
        CustomAPI loginApi;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
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

                    }

                    public void success(LoginMessage loginMessage, Response arg1) {
                        Log.d("login string = ", loginMessage.message);
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
                                //===


                                //start activity
                                Intent intent1 = new Intent(activity, ProjectActivity.class);
                                // intent1.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.PROJECT_ACTIVITY");

                                //intent1.putExtra("access_token",Constants.access_token);

                                activity.startActivity(intent1);
                            } else if (ifRequestFromLoginDialogSubmitActivity) {


//                                globalVars.setIfLoggedIn(true);

//                                Intent returnInt = new Intent();
//                                returnInt.putExtra("login_status",false);
//                                activity.setResult(Activity.RESULT_OK ,returnInt);
//


                                activity.finish();
                            }


                            Log.d("==now here ==", "login helper flag true");
                        } else
                            Log.d("==now here ==", "false");

//                            Toast.makeText(
//                                    getApplicationContext()
//                                    ,"Welcome!!" ,Toast.LENGTH_LONG).show();

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
                .setEndpoint(Constants.BASE_URL)  //call your base url
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


                        Intent intent = new Intent(activity,LoginActivity.class);
                        // intent.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.LOGIN_ACTIVITY");
                        activity.startActivity(intent);

                        //im done with the activity splash
                        activity.finish();


                        Log.d("===login with token==", "error = " + arg0.getMessage());

                    }

                    public void success(LoginMessage loginMessage, Response arg1) {
                        Log.d("login string = ", loginMessage.message);


                        Intent intent1 = new Intent(activity,ProjectActivity.class);

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



    public void saveToPermitTable(Permit permit, final Activity submitActivity) {

        //todo delete temporary
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();

        loginApi = restAdapter.create(CustomAPI.class);
        //

        loginApi.storeGeneralTabToPermitTabel(
                permit.permit_name
                , permit.permit_template_id
                , permit.project_id
                , permit.project_name
                , permit.auto_gen_permit_no
                , permit.location
                , permit.contractor
                , permit.work_activity
                , permit.permit_date
                , permit.start_time
                , permit.end_time
                , new Callback<ServerMessage>() {
                    @Override
                    public void success(ServerMessage serverReturnMessage, Response response) {


                        Toast.makeText(submitActivity, "Permit Saved", Toast.LENGTH_LONG);
                        Log.d(TAG+" == " ," success "+serverReturnMessage.message);

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.d(TAG+" == " ," fail "+error.getMessage());

                    }
                }
        );

    }
}