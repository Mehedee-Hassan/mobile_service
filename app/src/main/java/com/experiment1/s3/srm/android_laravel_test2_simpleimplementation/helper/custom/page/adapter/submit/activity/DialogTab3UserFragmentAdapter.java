package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.page.adapter.submit.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by Mhr on 10/7/2015.
 */
public class DialogTab3UserFragmentAdapter
        extends FragmentPagerAdapter {


    private List<Fragment> fragments;

//    public DialogTab3UserFragmentAdapter(FragmentManager fm ,List<Fragment> fragmens) {
//        super(fm);
//        this.fragments = fragments;
//    }

    public DialogTab3UserFragmentAdapter(FragmentManager supportFragmentManager, List<android.support.v4.app.Fragment> fragments) {
        super(supportFragmentManager);

        Log.d("UserSearchDialog Ac ==", "15");

        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
