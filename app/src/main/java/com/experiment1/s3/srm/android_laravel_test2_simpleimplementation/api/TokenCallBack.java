package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api;

import android.content.Context;
import android.content.Intent;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.ProjectListActivity;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mhr on 9/24/2015.
 */



public class TokenCallBack implements Callback<Project>{

    Context context;
    WeakReference<Context> ncontext;

    public TokenCallBack(Context context){
        this.context = context;
        ncontext = new WeakReference<Context>(context);

    }


    @Override
    public void success(Project project, Response response) {

        Context context = ncontext.get();

        if(context != null){
            Intent intent = new Intent(context, ProjectListActivity.class);
            context.startActivity(intent);

        }

    }

    @Override
    public void failure(RetrofitError error) {

    }
}
