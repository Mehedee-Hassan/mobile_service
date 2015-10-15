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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("tab2 ==", "oncreate 1");

        permitTemplateDetailsDBHelper = new PermitTemplateDetailsDBHelper(getActivity());

        Log.d("tab2 ==", "oncreate 2");



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.submit_activity_tab2checklist, container, false);



        Log.d("tab2 ==", "oncreateView 1");


        //todo get id

//        GlobalVars globalVars = (GlobalVars) getActivity().getApplication();

        int nowPermitTemplateDetailsId = ((GlobalVars) getActivity().getApplication()).currentPermitTemplateId;

        List<PermitTemplateDetails> ptdetailsList =
                permitTemplateDetailsDBHelper.getPermitTemplateDetailsListWherePTId(nowPermitTemplateDetailsId);

        Log.d("tab2 ==", "oncreateView 1.1");

        List<CheckList> checkLists = new ArrayList<CheckList>();

        Log.d("tab2 ==", "oncreateView 2");

        for(PermitTemplateDetails ptde: ptdetailsList){

            checkLists.add(new CheckList(ptde.id ,ptde.question));

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
}
