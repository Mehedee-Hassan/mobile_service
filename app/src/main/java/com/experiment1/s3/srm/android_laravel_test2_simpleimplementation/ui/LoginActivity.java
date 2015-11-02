package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api.CustomAPI;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api.ProjectCallBack;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Flags;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.BackgroundTaskHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.SaveDataHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.LoginMessage;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends Activity implements View.OnClickListener , CompoundButton.OnCheckedChangeListener{

    public String TAG = this.getClass().getSimpleName();


    Button loginButton,stayLocalButton;
    BackgroundTaskHelper backgroundTaskHelper;
    CheckBox saveCredentialCheckBox ,showPasswordCheckBox;
    ProjectCallBack projectCallBack;

    private EditText passwordEditText,usernameEditText;
    SaveDataHelper saveDataHelper;


    GlobalVars globalVars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Welcome");





        //init saved data helper
        saveDataHelper = new SaveDataHelper(this);
        //init the helper
        backgroundTaskHelper = new BackgroundTaskHelper(saveDataHelper ,this);







        globalVars = (GlobalVars) getApplication();
        globalVars.setIfLoggedIn(saveDataHelper.getIfLoggedIn());




        boolean loginCon = saveDataHelper.getIfLoggedIn();
        if(loginCon == false){
            checkIfLoggedIn(saveDataHelper);
        }
        else{
            finish();
        }


        loginButton = (Button) findViewById(R.id.loginButton);
        stayLocalButton  = (Button) findViewById(R.id.stay_local_button);

        saveCredentialCheckBox = (CheckBox) findViewById(R.id.saveCredentialCheckBox);
        showPasswordCheckBox = (CheckBox) findViewById(R.id.showPasswordCheckBox);

        passwordEditText = (EditText) findViewById(R.id.passwordeditText);
        usernameEditText = (EditText) findViewById(R.id.usernameeditText);


        passwordEditText.setText(saveDataHelper.getPrefPassword());
        usernameEditText.setText(saveDataHelper.getPrefUsername());

        //login button listner
        loginButton.setOnClickListener(this);
        stayLocalButton.setOnClickListener(this);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        showPasswordCheckBox.setOnCheckedChangeListener(this);
        saveCredentialCheckBox.setOnCheckedChangeListener(this);




    }

    private void checkIfLoggedIn(SaveDataHelper saveDataHelper) {

        //get the saved token
        String[] token = saveDataHelper.getAccessTokenDetails();

        Log.d("now here==" , ""+ token[0] );
        if( token[0] != ""){
        Log.d("now here==", "passed");


            boolean ifRequestFromLoginDialogSubmitActivity = false;

             backgroundTaskHelper.loginHelper3(token[0] //token
                     , token[1] // token type
                     , this, //activity
                     ifRequestFromLoginDialogSubmitActivity);

        }
        else {
            // stay
        }


    }

    private void saveCredentialHandler() {

//          old logic
//        if(Flags.SAVE_CREDENTIAL_FLAG) {
//            saveDataHelper.savePrefUsername(usernameEditText.getText().toString());
//            saveDataHelper.savePrefPassword(passwordEditText.getText().toString());
//        }
//        if(!Flags.SAVE_CREDENTIAL_FLAG){
//            //delete if saved
//            saveDataHelper.deleteCredential();
//        }

//        if(saveCredentialCheckBox.isChecked() == true){
//            saveDataHelper.savePrefUsername(usernameEditText.getText().toString());
//            saveDataHelper.savePrefPassword(passwordEditText.getText().toString());
//
//        }
//        else {
//            saveDataHelper.deleteCredential();
//
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        if (id == R.id.action_settings) {


            Intent i = new Intent(LoginActivity.this ,SettingActivity.class);
           // i.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.SETTINGS_ACTIVITY");
           // i.putExtra("preferences" ,saveDataHelper.toList());

            startActivity(i);


        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(saveDataHelper.getIfLoggedIn() == true){
            finish();
        }
    }

    public void doStuff(){

    }

    @Override
    public void onClick(View v) {


        switch(v.getId()) {

            case  R.id.loginButton:
            {

                //loading animation
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                Toast.makeText(
                        this.getApplicationContext()
                        , "Logging in .. ", Toast.LENGTH_LONG).show();


                Constants.access_token = "";
                //get token
//                int tokenAccessFlag = backgroundTaskHelper.tokenHelper();



//                globalVars.setUserName( usernameEditText.getText().toString());



                backgroundTaskHelper.tokenHelper3(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        this,saveDataHelper,this , false);





        //todo handler for late login call and wait for 16 seconds [delete]
                //if(Flags.LOGIN_SUCCESS_FLAG == true)
                //{
                    //delay 5 seconds asycn call for access

//                    Handler handler = new Handler();
//
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//
//                            boolean result2 = loginHelper(Constants.access_token
//                                    , Constants.token_type, projectCallBack);
//
//                            Log.d("==now here ==", "result login with token" + Constants.access_token);
//                        }
//
//
//                    }, Constants.CALL_LOGIN_HELPER_DELAY);
                //}

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                Log.d("==now here ==", "end");


                //save user credential
                saveCredentialHandler();


                break;
            }

            case R.id.stay_local_button:

                Intent intent = new Intent(LoginActivity.this , ProjectActivity.class);
                startActivity(intent);

                break;

            case R.id.showPasswordCheckBox:
            {

            }
            case R.id.saveCredentialCheckBox:
            {

            }


        }

    }


    //not used
    //todo delete later == [ loginHelper3 is in test ]
    //=================
    public boolean loginHelper(String access_token ,String token_type,ProjectCallBack projectCallBack
    )  {

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


                        Flags.LOGIN_SUCCESS_FLAG = false;

                        Toast.makeText(
                                getApplicationContext()
                                ,"Login Failed.\n"+arg0.getMessage()
                                ,Toast.LENGTH_LONG).show();

                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                        Log.d("===login with token==", "error = " + arg0.getMessage());

                    }

                    public void success(LoginMessage loginMessage, Response arg1) {
                        Log.d("login string = ", loginMessage.message);


                            Flags.LOGIN_SUCCESS_FLAG = true;

                        if (Flags.LOGIN_SUCCESS_FLAG) {
//                            Intent intent1 = new Intent(LoginActivity.this, ProjectListActivity.class);



                            //intent1.putExtra("access_token",Constants.access_token);

//                            startActivity(intent1);
                            Log.d("==now here ==", "login helper flag true");
                        } else
                            Log.d("==now here ==", "false");

//                            Toast.makeText(
//                                    getApplicationContext()
//                                    ,"Welcome!!" ,Toast.LENGTH_LONG).show();

                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                        //start activity for project list

                    }


                });


        return true;

    }

    //================

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch(buttonView.getId()){
            case R.id.showPasswordCheckBox:
                if(showPasswordCheckBox.isChecked()){
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);

                break;

            case R.id.saveCredentialCheckBox:
                Log.d("==checkbox===","clicked");

                if(saveCredentialCheckBox.isChecked()){
                    Flags.SAVE_CREDENTIAL_FLAG = true;
                    Log.d("==checkbox===","clicked");
                }

                else {
                    Flags.SAVE_CREDENTIAL_FLAG = false;
                }
                break;

        }


    }


    @Override
    protected void onStop() {
        super.onStop();

        Flags.SAVE_CREDENTIAL_FLAG = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Flags.SAVE_CREDENTIAL_FLAG = false;
    }



}
