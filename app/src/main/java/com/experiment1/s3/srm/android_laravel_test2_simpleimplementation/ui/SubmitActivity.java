package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.BackgroundTaskHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.SaveDataHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt1.Tab1GeneralFragmentEventConnector;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt1.Tab2CheckListFragmentEventConnector;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.PermitDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.lib.ext.SlidingTabLayout;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitPermission;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.dialog.full.login.LoginDialogActivity;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab1general;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab2Checklist;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab3Team;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab4Endros;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;


public class SubmitActivity extends FragmentActivity
        implements OnClickListener {


    public String TAG = this.getClass().getSimpleName();

    Tab1GeneralFragmentEventConnector tab1GeneralFragmentEventConnector;
    Tab2CheckListFragmentEventConnector tab2CheckFragmentEventConnector;

    PermitDBHelper ptwTypeTemplateDBHelper;
    private FragmentTabHost mTabHost;
    Button submitButton;
    GlobalVars globalVars;
    private ViewPager pager;
    private CustomPagerAdapter pagerAdapter;

    Permit globalPermit;
    EditText projectNameEt, subContractorNameTe3,
            locationEt4, descriptionWorkEt5, dateEt6, startEt7, endEt8;
    private TextView ptwTypeNameTextView;

    List<Fragment> fragments;
    public Project currentProject;
    private TextView projectNameTv;
    SaveDataHelper saveDataHelper;

    BackgroundTaskHelper backgroundTaskHelper;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_submit);


        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9900")));
        bar.setDisplayHomeAsUpEnabled(true);



        globalVars = ((GlobalVars) getApplication());

        Bundle extras = getIntent().getExtras();
        currentProject = globalVars.getProject();

//old logic
        //new Project((Project) extras.getParcelable("CURRENT_PROJECT_OBJECT"));
//        int permitTemplateItemAt = extras.getInt("list_position");
//        globalVars.setProject(currentProject);


        String permitNumber = extras.getString("permit_number");



        bar.setTitle(Html.fromHtml("<font color='#ffffff'>" +
                permitNumber +
                "</font>"));


        saveDataHelper  = new SaveDataHelper(this);
        ptwTypeTemplateDBHelper = new PermitDBHelper(this);
        backgroundTaskHelper = new BackgroundTaskHelper(this);


        initComponent();


        globalPermit = globalVars.getPermit();

        Log.d(TAG+" == ","submitactivity on create1" + globalPermit.permit_name);
        ptwTypeNameTextView.setText(globalPermit.permit_name);
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
        ptwTypeTemplateDBHelper = new PermitDBHelper(this);

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
        fragments.add(Fragment.instantiate(this, Tab4Endros.class.getName()));

        pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragments);

        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(4);

//        PagerTabStrip stripe = (PagerTabStrip) findViewById(R.permit_id.pager_tab_strip);
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
                return true;


              case R.id.action_settings:
                  Intent intent = new Intent(this, SettingActivity.class);
                  startActivityForResult(intent ,103);
                  break;
        }





        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.submit_button:


                Log.d(TAG + " == ", "button clicked");

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


                 tab1GeneralFragmentEventConnector = (Tab1GeneralFragmentEventConnector)
                        globalVars.getSubmitActTab1GenInterface();


                 tab1GeneralFragmentEventConnector = globalVars.getSubmitActTab1GenInterface();
                tab2CheckFragmentEventConnector = globalVars.getSubmitActTab2ChecklistInterface();




                Log.d(TAG + " == ", "Submit activity ,onclicklistnersetup ,interface ");

                Permit returnedPermitObject = tab1GeneralFragmentEventConnector.onSubmitButtonClick();


                returnedPermitObject.status = Constants.PERMIT_STATUS_APPROVED;



                List<PermitDetails> returnedPermitDetailsOvjList =
                        tab2CheckFragmentEventConnector.onSubmitButtonClick();




                //go to login page
                Intent intent = new Intent(SubmitActivity.this, LoginDialogActivity.class);
                startActivityForResult(intent, 2);


                Log.d(TAG + " == ", "returned permit object = " + returnedPermitObject.project_name);


                // saving without login notification
                // so login doesn't matters
                // todo correct issue

                saveToPermitTable(returnedPermitObject, returnedPermitDetailsOvjList, getPermitPermission());


//                saveToPermitDetailsTable(returnedPermitDetailsOvjList);
//                saveToPermitPermissionTable(getPermitPermission());


            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });
    }



    public PermitPermission getPermitPermission(){


        PermitPermission permitPermission = new PermitPermission();

        permitPermission.status = Constants.PERMIT_STATUS_SUBMITTED;
        permitPermission.permit_id = globalVars.getPermit().id;
        permitPermission.user_id = saveDataHelper.getCurrentUserId();




        return permitPermission;
    }




    private void saveToPermitTable(Permit returnedPermitObject , List<PermitDetails> returnedPermitDetailsList,PermitPermission permitPermission) {



        Log.d(TAG + " == ", "status = " + permitPermission.status);

        Log.d(TAG+" == ", " submit act ,save to permit");

//
//        Calendar calendar = Calendar.getInstance();


        returnedPermitObject.auto_gen_permit_no = globalPermit.auto_gen_permit_no;
        returnedPermitObject.permit_template_id = globalVars.getPermit().permit_template_id;


        Log.d(" == " , "Submit activity , permit template id"+returnedPermitObject.permit_template_id);

        returnedPermitObject.permit_name = globalPermit.permit_name;
//        returnedPermitObject.created_by = globalVars.getCurrentLoggedInUser().permit_id; //todo change temporary



        int permitTableOPFlag = Constants.PERMIT_TABLE_OPERATION_FLAG_INSERT;

        // 0 = means no effect to rejection of permitpermission tabel to server
        backgroundTaskHelper.saveToPermitTable(returnedPermitObject, this ,returnedPermitDetailsList,permitPermission,0 ,permitTableOPFlag);


    }

    private void saveToPermitPermissionTable(PermitPermission returnedPermitObject) {




        Log.d(TAG+" == ", " submit act ,save to permit");

//
//        Calendar calendar = Calendar.getInstance();


        returnedPermitObject.permit_id = globalPermit.id;
        returnedPermitObject.user_id = saveDataHelper.getCurrentUserRole();
//        returnedPermitObject.created_by = globalVars.getCurrentLoggedInUser().permit_id; //todo change temporary




        backgroundTaskHelper.saveToPermitPermissionTable(returnedPermitObject, this);


    }

    private void saveToPermitDetailsTable(List<PermitDetails> returnedPermitDetailsObjectList) {




        Log.d(TAG+" == ", " submit act ,save to permit");

//
//        Calendar calendar = Calendar.getInstance();


        for(PermitDetails permitDetails : returnedPermitDetailsObjectList){

            backgroundTaskHelper.saveToPermitDetailsTable(permitDetails, this);


        }

    }


    @Override
    protected void onPause() {
        super.onPause();


        handleSavedData();
    }

    private void handleSavedData() {

//        Permit permitObjectFromGeneralTab = tab1GeneralFragmentEventConnector.onSubmitActivityPause();
 //       saveDataHelper.setGeneralTabData(permitObjectFromGeneralTab);

    }


    @Override
    protected void onStop() {
        super.onStop();
        handleSavedData();

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
