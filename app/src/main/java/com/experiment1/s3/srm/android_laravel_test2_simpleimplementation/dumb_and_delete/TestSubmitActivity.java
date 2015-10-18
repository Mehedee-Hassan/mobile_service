package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.dumb_and_delete;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.lib.ext.SlidingTabLayout;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab1general;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab2Checklist;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab3Team;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab4Endros;

import java.util.List;
import java.util.Vector;

public class TestSubmitActivity extends FragmentActivity {


    private FragmentTabHost mTabHost;
    MyPagerAdapter pagerAdapter;
    ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);







        pager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> fragments = new Vector<Fragment>();


        fragments.add(Fragment.instantiate(this, Tab1general.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab2Checklist.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab3Team.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab4Endros.class.getName()));

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);

        pager.setAdapter(pagerAdapter);


//        PagerTabStrip stripe = (PagerTabStrip) findViewById(R.permit_id.pager_tab_strip);
//        stripe.setTextColor(Color.BLACK);



        SlidingTabLayout s = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        s.setDistributeEvenly(true);
        s.setSelectedIndicatorColors(Color.rgb(255, 145, 0));
        s.setViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_test_submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return this.fragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 4;
    }


    @Override
    public CharSequence getPageTitle(int position) {




        switch (position) {


            case 0:
                return "GENERAL";


            case 1:
                return "CHECKLIST";


            case 2:
                return "TEAM";

            case 3:
                return "ORDINAL";



        }

        return "";
    }
}