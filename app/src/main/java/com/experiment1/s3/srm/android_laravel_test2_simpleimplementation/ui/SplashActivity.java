package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.BackgroundTaskHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.SaveDataHelper;

public class SplashActivity extends Activity {



    private AlarmManager alaramManager;
    private PendingIntent  pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);


        SaveDataHelper saveDataHelper = new SaveDataHelper(this);
        BackgroundTaskHelper backgroundTaskHelper =
                new BackgroundTaskHelper(saveDataHelper,this);

        handleActivityToStart(saveDataHelper, backgroundTaskHelper);






        //schedule service
        //data loading
        //
        serviceScheduling();

    }

    private void serviceScheduling() {


        Intent intent = new Intent();
        intent.setAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.BACKGROUND_DATA_LOADING_INTENT_SERVICE");

//        Intent intent = new Intent(this, BackgroundDataLoadingIntentService.class);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP
                , System.currentTimeMillis() + (10000)
                , Constants.SERVICE_WEAK_INTERVAL
                , pendingIntent);
    }







    private void handleActivityToStart(SaveDataHelper saveDataHelper, BackgroundTaskHelper backgroundTaskHelper) {


        Intent intent;
        String[] token = saveDataHelper.getAccessTokenDetails();


        //if token is not saved

        if(token[0] != "" ){



            //if token is time out
            backgroundTaskHelper.getLoginToCheckIfLoggedIn(this,token);

            //go to project activity not needed
            //intent = new Intent(SplashActivity.this,ProjectActivity.class);
            //startActivity(intent);

        }
        else{


            //go to login activity
            intent = new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
        }


    }

}
