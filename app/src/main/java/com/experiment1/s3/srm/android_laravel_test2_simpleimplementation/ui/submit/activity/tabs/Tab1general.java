package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt1.Tab1GeneralFragmentEventConnector;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.SubmitActDraftDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view.GeneralTabSubmitAct;

/**
 * Created by Mhr on 10/3/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Tab1general extends Fragment implements Tab1GeneralFragmentEventConnector {


    public String TAG = this.getClass().getSimpleName();

    View view;
    private EditText projectNameEt;
    private EditText subContractorNameTe3;
    private EditText locationEt4;
    private EditText descriptionWorkEt5;
    private EditText dateEt6;
    private EditText startEt7;
    private EditText endEt8;
    Context context;

    GlobalVars globalVars;


    SubmitActDraftDBHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = this.getActivity().getBaseContext();


        globalVars = (GlobalVars) getActivity().getApplication();
        globalVars.setSubmitActTab1GenInterface(this);

        databaseHelper  = new SubmitActDraftDBHelper(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

         View view = inflater.inflate(R.layout.submit_activity_tab1_general, container, false);

        initLayoutComponents(view);


        return view;
    }

    private void initLayoutComponents(View root) {

        projectNameEt = (EditText) root.findViewById(R.id.project_name_editText);
        subContractorNameTe3 = (EditText)  root.findViewById(R.id.contractor_editText3);
        locationEt4 = (EditText)  root.findViewById(R.id.location_editText4);
        descriptionWorkEt5 = (EditText)  root.findViewById(R.id.description_work_editText5);
        dateEt6 = (EditText)  root.findViewById(R.id.date_editText6);
        startEt7 = (EditText)  root.findViewById(R.id.start_time_editText7);
        endEt8 = (EditText)  root.findViewById(R.id.end_time_editText8);





        Project currentProject = ((GlobalVars)getActivity().getApplication())
                .getProject();




        projectNameEt.setText(currentProject.name);
    }





    private Permit createPermitObjectFromFields() {

        Permit permit = new Permit();

        permit.project_id = globalVars.getProject().id;
        permit.project_name = globalVars.getProject().name;
        permit.contractor = subContractorNameTe3.getText().toString();
        permit.location = locationEt4.getText().toString();
        permit.work_activity = descriptionWorkEt5.getText().toString();
        permit.permit_date = dateEt6.getText().toString();
        permit.start_time = startEt7.getText().toString();
        permit.end_time = endEt8.getText().toString();


        permit.work_activity =descriptionWorkEt5.getText().toString();


        Log.d(TAG+ " ==* " ,"permit name =" + permit.work_activity);
        Log.d(TAG+ " ==* " ,"permit name =" + permit.project_name);
        Log.d(TAG+ " ==* " ,"permit name =" + permit.location);

        return permit;

    }


    @Override
    public Permit onSubmitButtonClick() {

        Log.d(TAG+" == " ,"onSubmitButtonClick, Tab1General.class");

        return createPermitObjectFromFields();


    }

    @Override
    public Permit onSubmitActivityPause() {
        return createPermitObjectFromFields();
    }


    @Override
    public void onPause() {
        super.onPause();


        databaseHelper.saveGeneralTabData(createPermitObjectFromFields());

    }
}
