package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;

/**
 * Created by Mhr on 10/3/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Tab4Ordinal extends Fragment {

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);


        View view = inflater.inflate(R.layout.submit_activity_tab4ordinal, container, false);


        return view;
    }
}
