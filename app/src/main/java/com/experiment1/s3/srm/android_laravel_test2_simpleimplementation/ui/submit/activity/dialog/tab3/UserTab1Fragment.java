package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.dialog.tab3;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt3.dialog.usert2.UserTextChange;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter.submit.tab3.dialog.UserListViewAdapter;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.UserDbHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.User;

import java.util.List;

/**
 * Created by Mhr on 10/8/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class UserTab1Fragment extends Fragment
        implements UserTextChange {


    View view;
    List<User> users;
    UserDbHelper userDatabaseHelper;
    ArrayAdapter<User> adapter;
    ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        userDatabaseHelper = new UserDbHelper(getActivity().getBaseContext());

//        GlobalVars globalVars = (GlobalVars) getActivity().getApplication();
//        globalVars.setUsrFragmentEventConnectorInterface(this);


        //optional
        //users.clear();

        Log.d("database == ", "database call");
        users = userDatabaseHelper.selectAllUser();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view  = inflater.inflate(R.layout.submit_act_t3_dialog_usert1_frag_layout ,container,false);


        listView = (ListView) view.findViewById(R.id.listView);

        adapter= new UserListViewAdapter(getActivity() ,users);
        listView.setAdapter(adapter);





        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }





    @Override
    public void onChangeTextInEditText(CharSequence charSequence) {
        adapter.getFilter().filter(charSequence);
    }
}
