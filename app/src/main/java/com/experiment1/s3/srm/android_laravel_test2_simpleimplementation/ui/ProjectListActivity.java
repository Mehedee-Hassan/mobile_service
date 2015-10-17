package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api.CustomAPI;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


//invalid not used
//todo delete activity
//=========================

public class ProjectListActivity extends ListActivity {


    List<Project> projectObjectList;
    List<String> projectList;
    ArrayAdapter<String> listAtapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_project_list);

       // retrieveData();


        projectList = new ArrayList<String>();
        projectObjectList = new ArrayList<Project>();


//        projectList.add("a");
//        projectList.add("b");

//        listAtapter = new ArrayAdapter<String>()
//
//        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,R.layout.row_layout ,R.permit_id.listItemTextView ,projectList);




//        listAtapter.addAll(projectList);

        setListAdapter(listAtapter);
    }

    private void retrieveData() {

        RestAdapter restAdapter;
        CustomAPI loginApi;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)  //call your base url
                .build();

        loginApi = restAdapter.create(CustomAPI.class);

        loginApi.getProjectList(Constants.access_token,
                Constants.token_type, new Callback<List<Project>>() {



                    @Override
                    public void success(List<Project> projects, Response response) {
//                        projectObjectList = projects;

                        for(Project pro : projects){
                            projectList.add(pro.name);
                        }

                        listAtapter.clear();
                       // ((BaseAdapter)listAtapter.getAdapter()).notifyDataSetChanged();

                        Log.d("project activity list", "success");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("project activity list" , "Fail");

                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_list, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent i = new Intent(ProjectListActivity.this ,SettingActivity.class);
            startActivity(i);

        }

        if(id == R.id.action_logout){

            //token removed
            Constants.access_token = "";

            Intent i = new Intent(ProjectListActivity.this ,LoginActivity.class);
            startActivity(i);

            Toast.makeText(this ,"Bye!!",Toast.LENGTH_SHORT);

        }

        return super.onOptionsItemSelected(item);
    }

}


// class ProjectListActivity2 extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_project_list);
//
//
//        Toast.makeText(this.getApplicationContext(), "Welcome!!!", Toast.LENGTH_SHORT).show();
//
//    }
//
//
//
//
//}
