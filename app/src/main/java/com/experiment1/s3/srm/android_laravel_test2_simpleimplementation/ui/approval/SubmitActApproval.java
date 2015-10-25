package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval;

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
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.SettingActivity;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval.tabs.Tab1generalApr;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval.tabs.Tab2ChecklistApr;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval.tabs.Tab3TeamApr;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval.tabs.Tab4EndrosApr;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.dialog.full.login.LoginDialogActivity;

import java.util.List;
import java.util.Vector;


public class SubmitActApproval extends FragmentActivity
        implements OnClickListener {


    public String TAG = this.getClass().getSimpleName();

    Tab1GeneralFragmentEventConnector tab1GeneralFragmentEventConnector;
    Tab2CheckListFragmentEventConnector tab2CheckFragmentEventConnector;

    PermitDBHelper ptwTypeTemplateDBHelper;
    private FragmentTabHost mTabHost;
    Button approveButton, rejectButton;
    GlobalVars globalVars;
    private ViewPager pager;
    private CustomPagerAdapter pagerAdapter;

    Permit globalPermit;


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



        setContentView(R.layout.activity_submit_validate);


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


        approveButton = (Button) findViewById(R.id.validate_button);
        rejectButton = (Button) findViewById(R.id.reject_button);


        approveButton.setText("APPROVE");


        approveButton.setOnClickListener(this);
        rejectButton.setOnClickListener(this);


        ptwTypeTemplateDBHelper = new PermitDBHelper(this);

        projectNameTv = (TextView) findViewById(R.id.ptw_type_name_textView);


        populateCheckList();
        Log.d(TAG + "submit ativity ==end3 ", "");// + currentProject.name);

    }

    private void tabHostInit() {


        pager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new Vector<Fragment>();


        fragments.add(Fragment.instantiate(this, Tab1generalApr.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab2ChecklistApr.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab3TeamApr.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab4EndrosApr.class.getName()));

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


        int state_reject = Constants.state_reject_login_approve;

        switch (view.getId()) {


            case R.id.reject_button:
                state_reject = Constants.state_reject_login_napprove;

            case R.id.validate_button:


                Log.d(TAG + " == ", "button clicked");

                //confirmation dialog

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm Please!!");


                View view2 = getLayoutInflater().inflate(R.layout.submit_act_on_submit_conf_dialog, null);


                TextView confTv = (TextView)view2.findViewById(R.id.confirmation_question_textView);
                confTv.setText("I am fully satisfied that a thorough inspection and proper assessment of " +
                        "\n the work area and its surrounding have been " +
                        "\n conducted and the piling operation can be carried out safely.");




                Button ok, cancel;

                ok = (Button) view2.findViewById(R.id.ok_button);
                cancel = (Button) view2.findViewById(R.id.cancel_button);
                builder.setView(view2);


                AlertDialog alert = builder.create();
                onClickListenerSetup(ok, cancel, builder, alert, state_reject);

                alert.show();

                //saveToPermitTable();

                break;






        }

    }

    private void onClickListenerSetup(Button ok, Button cancel, final AlertDialog.Builder builder
            , final AlertDialog alert ,final int state_reject) {
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                GlobalVars globalVars = (GlobalVars) getApplication();
                globalVars.setIsLoginActStdFromSubmit(true);

                //close dialog
                alert.cancel();


//                 tab1GeneralFragmentEventConnector = (Tab1GeneralFragmentEventConnector)
//                        globalVars.getSubmitActTab1GenInterfaceVal();


                tab1GeneralFragmentEventConnector = globalVars.getSubmitActTab1GenInterfaceVal();
                tab2CheckFragmentEventConnector = globalVars.getSubmitActTab2ChecklistInterfaceVal();


                Log.d(TAG + " == ", "Submit activity ,onclicklistnersetup ,interface ");

                Permit returnedPermitObject = tab1GeneralFragmentEventConnector.onSubmitButtonClick();

                List<PermitDetails> returnedPermitDetailsOvjList =
                        tab2CheckFragmentEventConnector.onSubmitButtonClick();


                //adding validate status
                returnedPermitObject.status = Constants.PERMIT_STATUS_TO_APPROVE;

                returnedPermitObject.server_permit_id = globalVars.getPermit().server_permit_id;
                returnedPermitObject.auto_gen_permit_no = globalVars.getPermit().auto_gen_permit_no;


                //go to login page
                Intent intent = new Intent(SubmitActApproval.this, LoginDialogActivity.class);
                startActivityForResult(intent, 2);


                Log.d(TAG + " ==+++ ", "returned permit server id = " + returnedPermitObject.server_permit_id);


                saveToPermitTable(returnedPermitObject, returnedPermitDetailsOvjList, getPermitPermission() ,state_reject);

//                saveToPermitTable(returnedPermitObject);
//                saveToPermitDetailsTable(returnedPermitDetailsOvjList);


            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });
    }




    private void saveToPermitTable(Permit returnedPermitObject , List<PermitDetails> returnedPermitDetailsList
            ,PermitPermission permitPermission ,int state_reject) {




        Log.d(TAG+" == ", " submit act ,save to permit");


        returnedPermitObject.auto_gen_permit_no = globalPermit.auto_gen_permit_no;
        returnedPermitObject.permit_template_id = globalVars.getPermit().permit_template_id;


        Log.d(" == " , "Submit activity , permit template id"+returnedPermitObject.permit_template_id);
        returnedPermitObject.permit_name = globalPermit.permit_name;

        backgroundTaskHelper.saveToPermitTable(returnedPermitObject, this ,returnedPermitDetailsList,permitPermission ,state_reject);


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



    public PermitPermission getPermitPermission(){


        PermitPermission permitPermission = new PermitPermission();

        permitPermission.status = Constants.PERMIT_STATUS_APPROVED;
        permitPermission.permit_id = globalVars.getPermit().id;
        permitPermission.user_id = saveDataHelper.getCurrentUserId();




        return permitPermission;
    }

    private void saveToPermitTable(Permit returnedPermitObject) {




        Log.d(TAG + " == ", " submit act ,save to permit");

//
//        Calendar calendar = Calendar.getInstance();


        returnedPermitObject.auto_gen_permit_no = globalPermit.auto_gen_permit_no;
        returnedPermitObject.permit_template_id = globalPermit.permit_template_id;
        returnedPermitObject.permit_name = globalPermit.permit_name;
//        returnedPermitObject.created_by = globalVars.getCurrentLoggedInUser().permit_id; //todo change temporary




//        backgroundTaskHelper.saveToPermitTable(returnedPermitObject, this);


    }
}
