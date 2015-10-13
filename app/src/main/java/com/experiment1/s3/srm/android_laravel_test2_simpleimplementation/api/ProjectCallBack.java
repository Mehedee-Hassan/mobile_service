package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api;

import android.content.Context;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.LoginMessage;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mhr on 9/24/2015.
 */



public class ProjectCallBack implements Callback<LoginMessage>{

    Context context;
    WeakReference<Context> ncontext;

    public ProjectCallBack(Context context){
        this.context = context;
        ncontext = new WeakReference<Context>(context);

    }


    @Override
    public void success(LoginMessage project, Response response) {

        Context context = ncontext.get();

        if(context != null){
//            Intent intent = new Intent(context, ProjectListActivity.class);
//            context.startActivity(intent);

        }

    }

    @Override
    public void failure(RetrofitError error) {

    }
}
