package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.dumb_and_delete;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.page.adapter.submit.activity.DialogTab3UserFragmentAdapter;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab1general;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab2Checklist;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab3Team;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab4Endros;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Mhr on 10/7/2015.
 */
public class NewSubmitActivity extends FragmentActivity
        implements TabHost.OnTabChangeListener
        ,ViewPager.OnPageChangeListener
         {


    TabHost tabHost;
    ViewPager viewPager;
    HashMap<String ,TabInfo> mapTabInfo
            = new HashMap<String ,TabInfo>();

    ListView listView;
             Button submitButton;
             TextView blabal;




    DialogTab3UserFragmentAdapter dialogTab3Adapter;
//    Tab1GeneralFragmentEventConnector userInterface;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_submit_old);
        setTitle("Permit Number");







        this.initializeTabHost(savedInstanceState);


        //===
//        getWindow().setLayout(width, ActionBar.LayoutParams.WRAP_CONTENT);

        //===



        Log.d("UserSearchDialog Ac ==", "6");

        if(savedInstanceState != null){
            tabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));

        }

        Log.d("UserSearchDialog Ac ==", "7");

        this.initializeViewPager();

        Log.d("UserSearchDialog Ac ==", "8");


        GlobalVars globalVars = (GlobalVars) this.getApplication();
//        this.userInterface = globalVars.setUsrFragmentEventConnectorInterface();


//        searchHelper();

    }






    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("tab" ,tabHost.getCurrentTabTag());

        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("UserSearchDialog Ac ==", "pager scroll");

    }

    @Override
    public void onPageSelected(int position) {

        Log.d("UserSearchDialog Ac ==","tab sele"+position);

        this.tabHost.setCurrentTab(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d("UserSearchDialog Ac ==", "scroll state");

    }


    @Override
    public void onTabChanged(String s) {
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);
    }


    private void initializeTabHost(Bundle savedInstanceState) {

        Log.d("UserSearchDialog Ac ==","1");

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();


        Log.d("UserSearchDialog Ac ==", "2");

        TabInfo tabInfo = null;

        NewSubmitActivity.AddTab(this, this.tabHost,
                this.tabHost.newTabSpec("GENERAL").setIndicator("GENERAL"), (tabInfo = new TabInfo("GENERAL", Tab1general.class, savedInstanceState)));

        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        Log.d("UserSearchDialog Ac ==", "3");

        NewSubmitActivity.AddTab(this, this.tabHost,
                this.tabHost.newTabSpec("CHECKLIST").setIndicator("CHECKLIST"), (tabInfo = new TabInfo("CHECKLIST", Tab2Checklist.class, savedInstanceState)));

        this.mapTabInfo.put(tabInfo.tag, tabInfo);



        NewSubmitActivity.AddTab(this, this.tabHost,
                this.tabHost.newTabSpec("TEAM").setIndicator("TEAM"), (tabInfo = new TabInfo("TEAM", Tab3Team.class, savedInstanceState)));

        this.mapTabInfo.put(tabInfo.tag, tabInfo);


        NewSubmitActivity.AddTab(this, this.tabHost,
                this.tabHost.newTabSpec("ORDINAL").setIndicator("ORDINAL"), (tabInfo = new TabInfo("ORDINAL", Tab4Endros.class, savedInstanceState)));

        this.mapTabInfo.put(tabInfo.tag, tabInfo);


        this.tabHost.setOnTabChangedListener(this);

        Log.d("UserSearchDialog Ac ==", "4");

    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initializeViewPager(){

        List<Fragment> fragments = new Vector<Fragment>();


        Log.d("UserSearchDialog Ac ==", "10");

        fragments.add(Fragment.instantiate(this, Tab1general.class.getName()));

        Log.d("UserSearchDialog Ac ==", "10.1");

        fragments.add(Fragment.instantiate(this, Tab2Checklist.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab3Team.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab4Endros.class.getName()));

        Log.d("UserSearchDialog Ac ==", "11");

        this.dialogTab3Adapter = new DialogTab3UserFragmentAdapter(super.getSupportFragmentManager(),fragments);
        //
        Log.d("UserSearchDialog Ac ==", "12");

        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.viewPager.setAdapter(this.dialogTab3Adapter);
        this.viewPager.setOnPageChangeListener(this);


        Log.d("UserSearchDialog Ac ==", "13");

    }


    private static void AddTab(NewSubmitActivity newSubmitActivity  , TabHost tabHost
            , TabHost.TabSpec tabSpec
            , TabInfo tabinfo) {


        tabSpec.setContent(new TabFactory(newSubmitActivity));
        tabHost.addTab(tabSpec);

    }




             class TabInfo{
                 String tag;
                 Class<?> classvar;
                 Bundle bundle;

                 Fragment fragment;

                 TabInfo (String tag ,Class<?> classvar , Bundle bundle){
                     this.tag = tag;
                     this.classvar = classvar;
                     this.bundle = bundle;

                 }

             }

}





class TabFactory implements TabHost.TabContentFactory{

    Context context;



    public TabFactory(Context context){

        this.context = context;
    }


    @Override
    public View createTabContent(String s) {
        View v = new View(context);
        v.setMinimumHeight(0);
        v.setMinimumWidth(0);
        return v;
    }


}