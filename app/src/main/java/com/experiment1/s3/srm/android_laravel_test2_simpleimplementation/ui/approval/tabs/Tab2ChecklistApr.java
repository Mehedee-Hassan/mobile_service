package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval.tabs;


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
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt1.Tab2CheckListFragmentEventConnector;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter.CheckListAdapterValidate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.PermitTemplateDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.SubmitActDraftDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view.CheckList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 10/3/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Tab2ChecklistApr extends Fragment
implements Tab2CheckListFragmentEventConnector {

    View view;
    PermitTemplateDBHelper permitTemplateDBHelper;
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

        permitTemplateDBHelper = new PermitTemplateDBHelper(getActivity());

        Log.d("tab2 ==", "oncreate 2");



        databaseHelper = new SubmitActDraftDBHelper(getActivity());
        globalVars = (GlobalVars) getActivity().getApplication();

        globalVars.setSubmitActTab2CheckInterfaceApr(this);
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
        long nowPermitTemplateDetailsId = ((GlobalVars) getActivity().getApplication()).getPermit().server_permit_id;


        Log.d("save == ","2 permit id = " + nowPermitTemplateDetailsId );



        ptdetailsList =
                permitTemplateDBHelper.getPtDetailsListWhereForValChkListPTId(nowPermitTemplateDetailsId);



        checkLists = new ArrayList<CheckList>();


        for(PermitDetails permitDetails : ptdetailsList){
            checkLists.add(new CheckList(permitDetails.id ,permitDetails.permit_id,
                    permitDetails.question , permitDetails.status,permitDetails.sno));
        }

        CheckListAdapterValidate adapter = new CheckListAdapterValidate(getActivity() , checkLists);


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


    @Override
    public List<PermitDetails> onSubmitButtonClick() {

        Log.d(" == response ", "call from tab2checklis");

        PermitDetails permitDetails;
        List<PermitDetails> permitDetailsList = new ArrayList<>();

        saveCheckList();


        for (CheckList checkList : checkLists){
            permitDetails = new PermitDetails();

            permitDetails.permit_id = checkList.permit_id;
            permitDetails.question = checkList.question;
            permitDetails.extra_text = "empty"; // temporary


            permitDetails.server_id = databaseHelper.getPermitDetailsServerId(permitDetails.id);

            if(checkList.yesOptions == 0)
                permitDetails.status = "NULL";

        if(checkList.yesOptions == 1)
                permitDetails.status = "OK";

        if(checkList.yesOptions == 2)
                permitDetails.status = "NOK";

        if(checkList.yesOptions == 3)
                permitDetails.status = "NA";


            permitDetailsList.add(permitDetails);

        }






        return permitDetailsList;
    }
}
