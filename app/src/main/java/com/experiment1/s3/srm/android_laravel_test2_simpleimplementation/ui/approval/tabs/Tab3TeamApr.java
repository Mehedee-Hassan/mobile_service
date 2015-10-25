package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval.tabs;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.dialog.tab3.UserSearchDialogActivity;

/**
 * Created by Mhr on 10/3/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Tab3TeamApr extends Fragment {

    View view;
    Button userButton;
    ViewGroup container;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.submit_activity_tab3team, container, false);


        this.container = container;



        //todo in next face of the app
        //todo uncomment and use
//        userButton = (Button) view.findViewById(R.permit_id.user_button);
//        userButton.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        showDialog();
//                    }
//                }
//        );


        return view;
    }



    private void showDialog() {

        Intent intent = new Intent(getActivity(), UserSearchDialogActivity.class);
        startActivity(intent);
//
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.setTitle("Choose User");
//
////        dialog.setContentView(R.layout.submit_activity_tab3_user_dialog_layout);
//        LayoutInflater inflater = dialog.getLayoutInflater();
//
//
//        View dialogView = inflater.inflate(R.layout.submit_activity_tab3_user_dialog_layout, container, false);
//
////        ListView listView = (ListView)dialogView.findViewById(R.permit_id.user_dialog_listView);
////        dialog.
//
//
//        String[] list = new String[]{"aa" ,"bb","aa" ,"aa" ,"bb"};
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext() ,R.layout.row_layout ,list);
//
////        listView.setAdapter(adapter);
//
//
//
//
//        dialog.setContentView(dialogView);
//
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT
//                , ViewGroup.LayoutParams.MATCH_PARENT);
//        dialog.show();



    }


}
