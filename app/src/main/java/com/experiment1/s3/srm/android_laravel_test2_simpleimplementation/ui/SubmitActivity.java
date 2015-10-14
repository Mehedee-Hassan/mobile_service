package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.BackgroundTaskHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt1.Tab1GeneralFragmentEventConnector;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.PTWTypeTemplateDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.lib.ext.SlidingTabLayout;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PTWType;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.dialog.full.login.LoginDialogActivity;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab1general;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab2Checklist;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab3Team;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab4Ordinal;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;


public class SubmitActivity extends FragmentActivity
        implements OnClickListener {


    public String TAG = this.getClass().getSimpleName();



    PTWTypeTemplateDBHelper ptwTypeTemplateDBHelper;
    private FragmentTabHost mTabHost;
    Button submitButton;
    GlobalVars globalVars;
    private ViewPager pager;
    private CustomPagerAdapter pagerAdapter;
    PTWType permitTemplate;
    EditText projectNameEt, subContractorNameTe3,
            locationEt4, descriptionWorkEt5, dateEt6, startEt7, endEt8;
    private TextView ptwTypeNameTextView;

    List<Fragment> fragments;
    public Project currentProject;
    private TextView projectNameTv;

    BackgroundTaskHelper backgroundTaskHelper;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_submit);


        globalVars = ((GlobalVars) getApplication());

        Bundle extras = getIntent().getExtras();
        currentProject = new Project((Project) extras.getParcelable("CURRENT_PROJECT_OBJECT"));
        int permitTemplateItemAt = extras.getInt("list_position");




        globalVars.setProject(currentProject);

        String permitNumber = extras.getString("permit_number");


        setTitle(permitNumber);

        ptwTypeTemplateDBHelper = new PTWTypeTemplateDBHelper(this);
        backgroundTaskHelper = new BackgroundTaskHelper(this);

        initComponent();


         permitTemplate =  ((GlobalVars) getApplication()).getPermitTemplate();


        Log.d(TAG+" == ","submitactivity on create1");
        ptwTypeNameTextView.setText(permitTemplate.name);
        //change activity back color
        ptwTypeNameTextView.getRootView().setBackgroundColor(Color.WHITE);

    }

    private void initComponent() {

        ptwTypeNameTextView = (TextView) findViewById(R.id.ptw_type_name_textView);

//        projectNameEt = (EditText) findViewById();
//        subContractorNameTe3 = (EditText) findViewById();
//        locationEt4 = (EditText) findViewById();
//        descriptionWorkEt5 = (EditText) findViewById();
//        dateEt6 = (EditText) findViewById();
//        startEt7 = (EditText) findViewById();
//        endEt8 = (EditText) findViewById();


        tabHostInit();


        submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(this);
        ptwTypeTemplateDBHelper = new PTWTypeTemplateDBHelper(this);

        projectNameTv = (TextView) findViewById(R.id.ptw_type_name_textView);


        populateCheckList();
        Log.d(TAG + "submit ativity ==end3 ", "");// + currentProject.name);

    }

    private void tabHostInit() {


        pager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new Vector<Fragment>();


        fragments.add(Fragment.instantiate(this, Tab1general.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab2Checklist.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab3Team.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab4Ordinal.class.getName()));

        pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragments);

        pager.setAdapter(pagerAdapter);


//        PagerTabStrip stripe = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
//        stripe.setTextColor(Color.BLACK);


        SlidingTabLayout s = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        s.setDistributeEvenly(true);
        s.setSelectedIndicatorColors(Color.rgb(255, 145, 0));
        s.setViewPager(pager);


    }

    private void populateCheckList() {


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {
            case android.R.id.home:
                finish();
        }


        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.submit_button:


                Log.d(TAG+" == ", "button clicked");

                //confirmation dialog

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm Please!!");


                View view2 = getLayoutInflater().inflate(R.layout.submit_act_on_submit_conf_dialog, null);


                TextView confTv = (TextView)view2.findViewById(R.id.confirmation_question_textView);
                confTv.setText("I have checked and confirmed " +
                        "that the following safety requirements " +
                        "have been complied and fully " +
                        "understand the nature of work " +
                        "and safety compliance");




                Button ok, cancel;

                ok = (Button) view2.findViewById(R.id.ok_button);
                cancel = (Button) view2.findViewById(R.id.cancel_button);
                builder.setView(view2);


                AlertDialog alert = builder.create();
                onClickListenerSetup(ok, cancel, builder, alert);

                alert.show();

                //saveToPermitTable();

                break;

        }

    }

    private void onClickListenerSetup(Button ok, Button cancel, final AlertDialog.Builder builder
            , final AlertDialog alert) {
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                GlobalVars globalVars = (GlobalVars) getApplication();
                globalVars.setIsLoginActStdFromSubmit(true);

                //close dialog
                alert.cancel();


                Tab1GeneralFragmentEventConnector tab1GeneralFragmentEventConnector = (Tab1GeneralFragmentEventConnector)
                        globalVars.getSubmitActTab1GenInterface();


                Log.d(TAG + " == ", "Submit activity ,onclicklistnersetup ,interface ");
                Permit returnedPermitObject = tab1GeneralFragmentEventConnector.onSubmitButtonClick();


                //go to login page
                Intent intent = new Intent(SubmitActivity.this, LoginDialogActivity.class);
                startActivityForResult(intent, 2);


                Log.d(TAG + " == ", "returned permit object = " + returnedPermitObject.project_name);
                saveToPermitTable(returnedPermitObject);


            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });
    }

    private void saveToPermitTable(Permit returnedPermitObject) {






        Log.d(TAG+" == ", " submit act ,save to permit");


        Calendar calendar = Calendar.getInstance();


        returnedPermitObject.auto_gen_permit_no = globalVars.getPermitNumber();
        returnedPermitObject.permit_template_id = globalVars.getPermitTemplate().id;
        returnedPermitObject.permit_name = returnedPermitObject.auto_gen_permit_no; //todo confusion
//        returnedPermitObject.created_by = globalVars.getCurrentLoggedInUser().id; //todo change temporary


        //sqlite format yyyy-mm-dd

//        returnedPermitObject.created_at = ""+calendar.get(Calendar.YEAR)
//                +"-"+calendar.get(Calendar.MONTH)
//                +"-"+calendar.get(Calendar.DAY_OF_MONTH)
//                +" "+calendar.get(Calendar.HOUR_OF_DAY)
//                +":"+calendar.get(Calendar.MINUTE)
//                +":"+calendar.get(Calendar.SECOND);
//
//
//        returnedPermitObject.updated_at = ""+calendar.get(Calendar.YEAR)
//                +"-"+calendar.get(Calendar.MONTH)
//                +"-"+calendar.get(Calendar.DAY_OF_MONTH)
//                +" "+calendar.get(Calendar.HOUR_OF_DAY)
//                +":"+calendar.get(Calendar.MINUTE)
//                +":"+calendar.get(Calendar.SECOND);





//        ptwTypeTemplateDBHelper.saveToPermitTabel(returnedPermitObject);

        backgroundTaskHelper.saveToPermitTable(returnedPermitObject,this);


    }




    class CustomPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public CustomPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            return this.fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {


            switch (position) {


                case 0:
                    return "GENERAL";


                case 1:
                    return "CHECKLIST";


                case 2:
                    return "TEAM";

                case 3:
                    return "ENDORSE";


            }

            return "";
        }

    }
}
