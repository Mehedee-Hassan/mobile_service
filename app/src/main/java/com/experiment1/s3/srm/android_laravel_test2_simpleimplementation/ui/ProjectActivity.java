package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api.CustomAPI;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.BackgroundTaskHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter.ProjectActivityListViewAdapter;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.ProjectDatabaseHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.variables.CurrentVars;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProjectActivity extends Activity {

    ListView listview;
    ArrayList<String> list;
    ArrayAdapter adapter;
    BackgroundTaskHelper backgroundTaskHelper;

    ProjectDatabaseHelper databaseHelper;
    BroadcastReceiver uiUpdateBReceiver;

    ProjectActivityListViewAdapter customAdapter;
    ProjectActivity projectActivity;

    IntentFilter uiIntentFilter;

    //not used
    ProgressBar progressBar;


    GlobalVars globalVars;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        globalVars = (GlobalVars) getApplication();
        //get itselt
        projectActivity = this;



        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9900")));
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>Projects </font>"));

        backgroundTaskHelper = new BackgroundTaskHelper(this);



        listview = (ListView) findViewById(R.id.listview);
        list = new ArrayList<String>();



        customAdapter = new ProjectActivityListViewAdapter(this , list);
        listview.setAdapter(customAdapter);
        listview.setOnItemClickListener(new ListViewItemClickListner());






        //adapter will be called in inner class so that must be declared final
        //and adapter must be initialized .. so cannot call background task
        //todo find solution
        //====================================================================

        //loadProjectList(this, access_token, token_type);

        //load from database

        databaseHelper = new ProjectDatabaseHelper(this);


        Log.d("project Activity", "1");

        backgroundTaskHelper.loadProjectListFromDatabase(projectActivity
                , databaseHelper, list, customAdapter);

        //active service


        createUIUpdateBroadcastReciever();
        registerUIUpdateBroadcastReceiver();


        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d("inside intent","chechk my broadcast");
                sendBroadcast(new Intent("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.PROJECT_ACTIVITY_LIST_VIEW_UPDATE"));

            }
        }, 10000);


    }

    private void registerUIUpdateBroadcastReceiver() {

        uiIntentFilter = new IntentFilter();
        uiIntentFilter.addAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.BACKGROUND_DATA_LOADING_INTENT_SERVICE");
        uiIntentFilter.addAction("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.PROJECT_ACTIVITY_LIST_VIEW_UPDATE");

        registerReceiver(uiUpdateBReceiver, uiIntentFilter);

    }

    private void createUIUpdateBroadcastReciever() {

        uiUpdateBReceiver  = new BroadcastReceiver(){



            @Override
            public void onReceive(Context context, Intent intent) {


                Log.d("receiver", "project class");
                //  customAdapter.notifyDataSetChanged();

                //give 30second to load data from server to database
                //after 30 sec update ui
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //progressBar.setVisibility(View.VISIBLE);

                        backgroundTaskHelper.loadProjectListFromDatabase(projectActivity
                                , databaseHelper, list, customAdapter);

                        //progressBar.setVisibility(View.GONE);

                    }
                }, 10000);


            }
        };


    }


    //from database
    public  void loadProjectList(Context context,ProjectDatabaseHelper databaseHelper){

        List<Project> projects = new ArrayList<Project>();

        projects = databaseHelper.getProjects();

        list.clear();
        adapter.clear();

        for (Project project : projects) {
            Log.d("now2 ", project.name);
            list.add(project.name);
        }





        adapter = new ArrayAdapter<String>(context,
                R.layout.row_layout, list);


        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }



    //from server
    public void loadProjectList(final Activity act
            ,String access_token ,String token_type
                                ){


        Log.d("now ", "load project list");

        RestAdapter restAdapter;
        CustomAPI loginApi;


        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();

        loginApi = restAdapter.create(CustomAPI.class);

        if(Constants.access_token == "" || Constants.token_type =="")
            return ;



        loginApi.getProjectList(Constants.access_token, Constants.token_type, new Callback<List<Project>>() {


            @Override
            public void success(List<Project> projects, Response response) {

                Log.d("now ", "load project list success");


                list.clear();
                adapter.clear();

                for (Project project : projects) {
                    Log.d("now ", project.name);
                    list.add(project.name);
                }





                adapter = new ArrayAdapter<String>(act,
                        R.layout.row_layout, list);


                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();



            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("now ", "load project list fail");

            }
        });





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent i = new Intent(ProjectActivity.this ,SettingActivity.class);
            startActivity(i);

        }

        if (id == R.id.action_refresh) {

            startService(new Intent("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.BACKGROUND_DATA_LOADING_INTENT_SERVICE"));
//            sendBroadcast(new Intent("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.BACKGROUND_DATA_LOADING_INTENT_SERVICE"));
            sendBroadcast(new Intent("com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.PROJECT_ACTIVITY_LIST_VIEW_UPDATE"));
        }

        if(id == R.id.action_logout){

            //token removed
            Constants.access_token = "";

            Intent i = new Intent(ProjectActivity.this ,LoginActivity.class);
            startActivity(i);

            Toast.makeText(this, "Bye!!", Toast.LENGTH_SHORT);

        }

        return super.onOptionsItemSelected(item);
    }



//overrident class


    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(uiUpdateBReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();


        backgroundTaskHelper.loadProjectListFromDatabase(projectActivity
                , databaseHelper, list, customAdapter);
        //registered again
        registerUIUpdateBroadcastReceiver();

    }





    class ListViewItemClickListner implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            Project project = new Project(databaseHelper.getProjectAt(pos));

            //todo change curretn vars
//            CurrentVars.PROJECT =project;

            globalVars.setProject(project);



            Intent intent = new Intent(ProjectActivity.this , PermitToWorkActivity.class);
            startActivity(intent);



        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
