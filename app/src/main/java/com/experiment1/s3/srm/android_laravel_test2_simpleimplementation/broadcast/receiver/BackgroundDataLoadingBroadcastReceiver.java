package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.Services.BackgroundDataLoadingIntentService;

/**
 * Created by Mhr on 9/28/2015.
 */
public class BackgroundDataLoadingBroadcastReceiver extends BroadcastReceiver{





    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d("on receive " ,"alarm manager");

        Intent intent2 = new Intent(context , BackgroundDataLoadingIntentService.class);

        if(isNetworkAvailable(context)){
            context.startService(intent2);
        }

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
