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
import android.widget.ListView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter.CheckListAdapter;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.PermitTemplateDetailsDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.SubmitActDraftDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetail;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplateDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view.CheckList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 10/3/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Tab2Checklist extends Fragment {

    View view;
    PermitTemplateDetailsDBHelper permitTemplateDetailsDBHelper;
    ListView listViw;
    Context context;
    SubmitActDraftDBHelper databaseHelper;

    GlobalVars globalVars;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("tab2 ==", "oncreate 1");

        permitTemplateDetailsDBHelper = new PermitTemplateDetailsDBHelper(getActivity());

        Log.d("tab2 ==", "oncreate 2");

        databaseHelper = new SubmitActDraftDBHelper(getActivity());
        globalVars = (GlobalVars) getActivity().getApplication();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.submit_activity_tab2checklist, container, false);



        //draft loading
        handleSavedDraftLoading();



        Log.d("tab2 ==", "oncreateView 1");


        //todo get id

//        GlobalVars globalVars = (GlobalVars) getActivity().getApplication();
//todo change
        int nowPermitTemplateDetailsId = ((GlobalVars) getActivity().getApplication()).getPermit().id;

        List<PermitDetail> ptdetailsList =
                permitTemplateDetailsDBHelper.getPermitTemplateDetailsListWherePTId(nowPermitTemplateDetailsId);

        Log.d("tab2 ==", "oncreateView 1.1");

        List<CheckList> checkLists = new ArrayList<CheckList>();

        Log.d("tab2 ==", "oncreateView 2");

        for(PermitDetail ptde: ptdetailsList){

            checkLists.add(new CheckList(ptde.id ,ptde.question ,ptde.status));

        }
        Log.d("tab2 ==", "oncreateView 3");


        CheckListAdapter adapter = new CheckListAdapter(getActivity() , checkLists);


        Log.d("tab2 ==", "oncreateView 4");

        listViw = (ListView) view.findViewById(R.id.checklist_tab2_listView);

        Log.d("tab2 ==", "oncreateView 5");

        listViw.setAdapter(adapter);



        Log.d("tab2 ==", "oncreateView 7");


        return view;
    }

    private void handleSavedDraftLoading() {


        int permitId = globalVars.getPermit().id;
        int permitTemplateId = globalVars.getPermit().permit_template_id;
        checkIfPermiQuestionIsEmpetyFor(permitId ,permitTemplateId);


    }

    private void checkIfPermiQuestionIsEmpetyFor(int permitId ,int permitTemplateId) {
        if(databaseHelper.checkIfPermiQuestionIsEmpetyFor(permitId)){
            databaseHelper.transferPermitTempQToPermitDet(permitId ,permitTemplateId);
        }

    }
}
