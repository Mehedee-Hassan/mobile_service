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
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
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
    List<PermitDetails> ptdetailsList;
    List<CheckList> checkLists;
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


        //todo get permit_id

//        GlobalVars globalVars = (GlobalVars) getActivity().getApplication();
//todo change
        long nowPermitTemplateDetailsId = ((GlobalVars) getActivity().getApplication()).getPermit().id;


        Log.d("save == ","2 permit id = " + nowPermitTemplateDetailsId );



        ptdetailsList =
                permitTemplateDetailsDBHelper.getPermitTemplateDetailsListWherePTId(nowPermitTemplateDetailsId);



        checkLists = new ArrayList<CheckList>();


        for(PermitDetails permitDetails : ptdetailsList){
            checkLists.add(new CheckList(permitDetails.id , permitDetails.question , permitDetails.status));
        }

        CheckListAdapter adapter = new CheckListAdapter(getActivity() , checkLists);
        listViw = (ListView) view.findViewById(R.id.checklist_tab2_listView);
        listViw.setAdapter(adapter);

        return view;
    }

    private void handleSavedDraftLoading() {


        long permitId = globalVars.getPermit().id;
        int permitTemplateId = globalVars.getPermit().permit_template_id;

        Log.d("save == ","permit id = " + permitId +" permit template id = "+ permitTemplateId);


        checkIfPermiQuestionIsEmpetyFor(permitId ,permitTemplateId);


    }

    private void checkIfPermiQuestionIsEmpetyFor(long permitId ,int permitTemplateId) {
        if(databaseHelper.checkIfPermiQuestionIsEmpetyFor(permitId)){
            databaseHelper.transferPermitTempQToPermitDet(permitId ,permitTemplateId);
        }

    }



    private void saveCheckList() {


        Log.d("save","sdf" + checkLists.size());
        if(checkLists.size() > 0){

            for(CheckList checkList : checkLists){
                databaseHelper.saveCheckListStatus(checkList);
                Log.d("save", "sdf" + checkList.yesOptions);

            }
        }

    }




    @Override
    public void onPause() {
        super.onPause();

        saveCheckList();

    }


    @Override
    public void onStop() {
        super.onStop();
        saveCheckList();
    }


    @Override
    public void onResume() {
        super.onResume();
    }





}
