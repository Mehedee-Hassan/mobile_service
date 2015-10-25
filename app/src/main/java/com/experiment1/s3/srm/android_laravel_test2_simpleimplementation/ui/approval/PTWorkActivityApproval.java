package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter.PermitToWorkActListAdapter;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.PermitDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.SubmitActDraftDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

//import android.support.v7.app.AppCompatActivity;
//permit to work activity validate


public class PTWorkActivityApproval extends Activity
implements View.OnClickListener, OnItemClickListener {

    public String TAG = this.getClass().getSimpleName();


    Dialog dialog ;

    PermitDBHelper permitDBHelper;
    List<String> listOfPtwTypes;
//    ProjectActivityListViewAdapter ptwTypeListDialogAdapter;
    ListView dialogListView;
    ListView ptwListview;
    Project CurretnProject;
    PermitToWorkActListAdapter customAdapter;
    GlobalVars globalVars;


    //todo change db helper
    SubmitActDraftDBHelper submitActDraftDBHelper;



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_to_work_validate);


        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9900")));

        bar.setDisplayHomeAsUpEnabled(true);

        globalVars = (GlobalVars) getApplication();

        initComponent(bar);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initComponent(ActionBar bar) {

        CurretnProject =globalVars.getProject();


        Log.d(" == global var" ,""+CurretnProject.name);


        bar.setTitle(Html.fromHtml("<font color='#ffffff'>" +
                CurretnProject.name +
                "</font>"));




        permitDBHelper = new PermitDBHelper(this);

        //dialogListView = (ListView) findViewById(R.permit_id.permitToWorkDialoglistView);

        ptwListview = (ListView) findViewById(R.id.permit_to_work_before_listView);
        ptwListview.setOnItemClickListener(this);

        listOfPtwTypes = new ArrayList<>();



        submitActDraftDBHelper = new SubmitActDraftDBHelper(this);
        globalVars = (GlobalVars) getApplication();

        permitListViewSetup(1);
    }

    private void handleDialog() {



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_permit_to_work, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch(id){

            case R.id.action_settings:
                Intent intent = new Intent(this ,SettingActivity.class);
                startActivityForResult(intent, 102);
                break;

            case android.R.id.home:

                this.finish();
                return true;

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

//        switch (view.getId()){
//
//        }

    }



    private void savePermitDraftHandler(PermitTemplate permitTemplate, String permitNumber) {


       Project project = globalVars.getProject();


        //permit draft saved
       submitActDraftDBHelper.currentStateIsDraftedInDB(permitTemplate ,project,permitNumber);


        return ;
    }









    private void permitListViewSetup(int item) {



        Log.d(TAG+" == ","1 "+globalVars.getProject().name);

        List<Permit> list = permitDBHelper.getListOfPermitSaved(globalVars.getProject());



        List<Permit> ptwForViews = new ArrayList<>();
        Permit permitForView ;

        for(Permit permit :list){

            String[] dateTimeSplitStart;
            String[] dateTimeSplitEnd;
            if(permit.start_time !=null) {
                dateTimeSplitStart = permit.start_time.split("\\ ");
                dateTimeSplitEnd = permit.end_time.split("\\ ");
            }

            else {
                    dateTimeSplitStart = new String[]{"",""};
                    dateTimeSplitEnd = new String[]{"",""};
            }

            permitForView = new Permit();
            permitForView.auto_gen_permit_no = ""+permit.auto_gen_permit_no;
            permitForView.work_activity = permit.work_activity;
            permitForView.location = permit.location;
            permitForView.start_time = permit.start_time;
            permitForView.end_time =  permit.end_time;


            ptwForViews.add(permitForView);
        }




        customAdapter = new PermitToWorkActListAdapter(this ,ptwForViews);

        ptwListview.setAdapter(customAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();

        permitListViewSetup(1);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Permit permit = permitDBHelper.getPermitDraftAt(position);


        Intent intent = new Intent(PTWorkActivityApproval.this ,SubmitActApproval.class);
        intent.putExtra("permit_number", permit.auto_gen_permit_no);

        globalVars.setPermit(permit);


        Log.d(TAG + " == ", "permit name" + permit.permit_name);
        Log.d(TAG +" == " ,"permit id" + permit.id);
        startActivity(intent);


    }
}
