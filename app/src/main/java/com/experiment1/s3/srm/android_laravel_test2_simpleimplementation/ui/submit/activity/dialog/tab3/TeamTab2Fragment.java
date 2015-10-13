package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.dialog.tab3;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;

/**
 * Created by Mhr on 10/8/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TeamTab2Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.submit_act_t3_dialog_team2_frag_layout ,container,false);

//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
