package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter.PermitToWorkActListAdapter;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter.ProjectActivityListViewAdapter;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.PTWTypeTemplateDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.SubmitActDraftDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PTW;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view.PTWForView;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.variables.CurrentVars;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

//import android.support.v7.app.AppCompatActivity;

public class PermitToWorkActivity extends Activity
implements View.OnClickListener, OnItemClickListener {

    public String TAG = this.getClass().getSimpleName();


    Dialog dialog ;
    Button newPermitButton;
    PTWTypeTemplateDBHelper ptwTypeTemplateDBHelper;
    List<String> listOfPtwTypes;
    ProjectActivityListViewAdapter ptwTypeListDialogAdapter;
    ListView dialogListView;
    ListView ptwListview;
    Project CurretnProject;
    PermitToWorkActListAdapter customAdapter;
    GlobalVars globalVars;
    SubmitActDraftDBHelper submitActDraftDBHelper;



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_to_work);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        initComponent();

    }

    private void initComponent() {

        CurretnProject = CurrentVars.PROJECT;

        setTitle("  "+CurretnProject.name+"");


        newPermitButton = (Button) findViewById(R.id.new_permit_button);
        ptwTypeTemplateDBHelper = new PTWTypeTemplateDBHelper(this);
        newPermitButton.setOnClickListener(this);

        //dialogListView = (ListView) findViewById(R.id.permitToWorkDialoglistView);

        ptwListview = (ListView) findViewById(R.id.permit_to_work_before_listView);
        ptwListview.setOnItemClickListener(this);

        listOfPtwTypes = new ArrayList<>();



        submitActDraftDBHelper = new SubmitActDraftDBHelper(this);
        globalVars = (GlobalVars) getApplication();

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
                startActivityForResult(intent ,102);
                break;

            case android.R.id.home:

                this.finish();
                return true;

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.new_permit_button:


                final Dialog dialog = new Dialog(this);
                dialog.setTitle("Choose PTW Type");

                Log.d("== PermitToWorkActivity", "getlist click");

                List<String> temp;



                temp= ptwTypeTemplateDBHelper.getPtwTypeList();

                Log.d("== PermitToWorkActivity", "temp size" + temp.size());

                CharSequence[] ptwTypeList = temp.toArray(new CharSequence[temp.size()]);
             //   CharSequence[] ptwTypeList = new CharSequence[]{ "sfd" ,"sdf","sfd" ,"sdf","sfd" ,"sdf","sfd" ,"sdf","sfd" ,"sdf"};

                Log.d("== PermitToWorkActivity", "ptwTypeList size" + ptwTypeList.length);


                AlertDialog.Builder builder  = new AlertDialog.Builder(this);
                builder.setTitle("Choose PTW Type");




                builder.setItems(ptwTypeList, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {


                        //todo change CurrentVar to GlobalVars
                        PermitTemplate permitTemplate = ptwTypeTemplateDBHelper.getPTWTypeAt(item);


                        //
                        //set permit_template id for question
                        //find permit details

                        ((GlobalVars) getApplication()).currentPermitTemplateId = permitTemplate.id;

                        ((GlobalVars) getApplication()).setPermitTemplate(permitTemplate);





                        CurrentVars.PROJECT = CurretnProject;
                        Intent intent= new Intent(PermitToWorkActivity.this, SubmitActivity.class);
                        intent.putExtra("CURRENT_PROJECT_OBJECT", CurretnProject);
                        intent.putExtra("list_position",item);


                        String permitNumber = generatePermitNumber();
                        intent.putExtra("permit_number", permitNumber);




                        Toast.makeText(PermitToWorkActivity.this, "permit number generated:\n"+permitNumber+"" , Toast.LENGTH_LONG).show();
                        Log.d("==", permitNumber);
                        startActivity(intent);




                        savePermitDraftHandler(permitTemplate ,permitNumber);





                    }
                });
                AlertDialog alert = builder.create();

                alert.show();


                break;

        }

    }



    private void savePermitDraftHandler(PermitTemplate permitTemplate, String permitNumber) {


       Project project = globalVars.getProject();

       submitActDraftDBHelper.currentStateIsDraftedInDB(permitTemplate ,project,permitNumber);


        return ;
    }


    public String generatePermitNumber(){

        String permitNUmber = "";


        Date d = new Date();
        SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH");
        SimpleDateFormat dateFormatMin = new SimpleDateFormat("mm");
        SimpleDateFormat dateFormatSec = new SimpleDateFormat("ss");

        int sum = 0 ;
        sum += Integer.parseInt(dateFormatHour.format(d).toString())*3600;
        sum += Integer.parseInt(dateFormatMin.format(d).toString())*60;
        sum += Integer.parseInt(dateFormatSec.format(d).toString());




        SimpleDateFormat dateFormatY = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateFormatM = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatd = new SimpleDateFormat("dd");


        int yyyy =  Integer.parseInt(dateFormatY.format(d).toString());
        int mm =  Integer.parseInt(dateFormatM.format(d).toString());
        int dd =  Integer.parseInt(dateFormatd.format(d).toString());

        String username = Constants.username.substring(0, 2);







        if(true)
        {
            permitNUmber += yyyy
                    + "" + mm
                    + "" + dd
                    + "" + username.toUpperCase()
                    + sum;

        }


        globalVars.setPermitNumber(permitNUmber);
        return permitNUmber;

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }



    private void setPtwTypeDetailInListView(int item) {



        List<PTW> list = ptwTypeTemplateDBHelper.getListOfDetailsPtw(item);


        List<PTWForView> ptwForViews = new ArrayList<>();
        PTWForView ptwForView ;

        for(PTW ptw :list){


            String[] dateTimeSplitStart = ptw.startTime.split("\\ ");
            String[] dateTimeSplitEnd = ptw.endTime.split("\\ ");

            ptwForView = new PTWForView();
            ptwForView.rowTextTitle = ""+ptw.id;
            ptwForView.rowTextDescription = ptw.workDescription;
            ptwForView.rowTextLocation = ptw.location;
            ptwForView.rowTextTime = dateTimeSplitStart[1] + " "+ dateTimeSplitEnd[1];


            ptwForViews.add(ptwForView);
        }




        customAdapter = new PermitToWorkActListAdapter(this ,ptwForViews);

        ptwListview.setAdapter(customAdapter);

    }


}
