package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.dialog.full.login;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
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
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.ProjectListActivity;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.SettingActivity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginDialogActivity extends Activity implements View.OnClickListener
        , CompoundButton.OnCheckedChangeListener{

    public String TAG = this.getClass().getSimpleName();


    Button loginButton, cancelButton;
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
        setTitle("Please Login");


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.90);
        getWindow().setLayout(width , ActionBar.LayoutParams.WRAP_CONTENT);


        //init saved data helper
        saveDataHelper = new SaveDataHelper(this);
        //init the helper
        backgroundTaskHelper = new BackgroundTaskHelper(saveDataHelper ,this);



        //if logged in
        checkIfLoggedIn(saveDataHelper);


        //else not logged in


        Log.d("==", "logindialogactivity oncreate1");
        globalVars = (GlobalVars) getApplication();
        Log.d("==" ,"logindialogactivity oncreate2");



        loginButton = (Button) findViewById(R.id.loginButton);
        cancelButton = (Button) findViewById(R.id.stay_local_button);

        //override xm layout button text
        cancelButton.setText("Cancel");


        saveCredentialCheckBox = (CheckBox) findViewById(R.id.saveCredentialCheckBox);
        showPasswordCheckBox = (CheckBox) findViewById(R.id.showPasswordCheckBox);

        passwordEditText = (EditText) findViewById(R.id.passwordeditText);
        usernameEditText = (EditText) findViewById(R.id.usernameeditText);


        passwordEditText.setText(saveDataHelper.getPrefPassword());
        usernameEditText.setText(saveDataHelper.getPrefUsername());

        //login button listner
        loginButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        showPasswordCheckBox.setOnCheckedChangeListener(this);
        saveCredentialCheckBox.setOnCheckedChangeListener(this);



    }

    private void checkIfLoggedIn(SaveDataHelper saveDataHelper) {

        boolean ifRequestFromLoginDialogSubmitActivity = true;

        //get the saved token
        String[] token = saveDataHelper.getAccessTokenDetails();

        Log.d("now here==" , ""+ token[0] );
        if( token[0] != ""){
        Log.d("now here==", "passed");


         backgroundTaskHelper.loginHelper3(token[0] //token
                                    , token[1] // token type
                     ,this //activity
                     ,ifRequestFromLoginDialogSubmitActivity
             );

        }
        else {
            // stay
        }




    }

    private void saveCredentialHandler() {



        if(saveCredentialCheckBox.isChecked() == true){
            saveDataHelper.savePrefUsername(usernameEditText.getText().toString());
            saveDataHelper.savePrefPassword(passwordEditText.getText().toString());

        }
        else {
            saveDataHelper.deleteCredential();

        }

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


            Intent i = new Intent(LoginDialogActivity.this ,SettingActivity.class);
            startActivity(i);


        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();


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


                Permit permit =  saveToPermitObject();

                backgroundTaskHelper.tokenHelper3(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        this,saveDataHelper,this , true);




                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                Log.d("==now here ==", "end");


                //save user credential
                saveCredentialHandler();


                break;
            }

            case R.id.stay_local_button:

               this.finish();

                break;

            case R.id.showPasswordCheckBox:
            {

            }
            case R.id.saveCredentialCheckBox:
            {

            }


        }

    }

    private Permit saveToPermitObject() {

        //todo create permit object



    return null;

    }




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
