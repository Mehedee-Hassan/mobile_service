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
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;

/**
 * Created by Mhr on 10/3/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Tab1general extends Fragment implements Tab1GeneralFragmentEventConnector {

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


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = this.getActivity().getBaseContext();


        globalVars = (GlobalVars) getActivity().getApplication();
        globalVars.setSubmitActTab1GenInterface(this);
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

        projectNameEt = (EditText) root.findViewById(R.id.editText);
        subContractorNameTe3 = (EditText)  root.findViewById(R.id.editText3);
        locationEt4 = (EditText)  root.findViewById(R.id.editText4);
        descriptionWorkEt5 = (EditText)  root.findViewById(R.id.editText5);
        dateEt6 = (EditText)  root.findViewById(R.id.editText6);
        startEt7 = (EditText)  root.findViewById(R.id.editText7);
        endEt8 = (EditText)  root.findViewById(R.id.editText8);





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
        permit.permit_date = dateEt6.getText().toString();
        permit.start_time = startEt7.getText().toString();
        permit.end_time = endEt8.getText().toString();


        permit.work_activity =descriptionWorkEt5.getText().toString();


        Log.d("==" ,"permit name =" + permit.permit_name);

        return permit;

    }


    @Override
    public Permit onSubmitButtonClick() {



        return createPermitObjectFromFields();


    }
}
