package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.dialog.tab3;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
//import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt3.dialog.usert2.Tab1GeneralFragmentEventConnector;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.page.adapter.submit.activity.DialogTab3UserFragmentAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Mhr on 10/7/2015.
 */
public class UserSearchDialogActivity extends FragmentActivity
        implements TabHost.OnTabChangeListener
        ,ViewPager.OnPageChangeListener
         {


    TabHost tabHost;
    ViewPager viewPager;
    HashMap<String ,TabInfo> mapTabInfo
            = new HashMap<String ,TabInfo>();

    ListView listView;
    DialogTab3UserFragmentAdapter adapter;
    EditText searchEditText ;

//    PagerAdapter pagerAdapter;
//    private PagerAdapter pageAdapter;

    DialogTab3UserFragmentAdapter dialogTab3Adapter;
//    Tab1GeneralFragmentEventConnector userInterface;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.90);
        //not used
        //int height = (int) (metrics.widthPixels * 0.90);

        setContentView(R.layout.submit_activity_tab3_user_dialog_layout);
        setTitle("choose user");


        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        searchEditText = (EditText) findViewById(R.id.search_editText);



        this.initializeTabHost(savedInstanceState);


        //===
        getWindow().setLayout(width, ActionBar.LayoutParams.WRAP_CONTENT);

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


    public void searchHelper (){
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
 //               userInterface.onChangeTextInEditText(charSequence);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

        Log.d("UserSearchDialog Ac ==","tab sele");

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

        UserSearchDialogActivity.AddTab(this, this.tabHost,
                this.tabHost.newTabSpec("User").setIndicator("User")
                , (tabInfo = new TabInfo("User", UserTab1Fragment.class, savedInstanceState)));

        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        Log.d("UserSearchDialog Ac ==", "3");

        UserSearchDialogActivity.AddTab(this, this.tabHost,
                this.tabHost.newTabSpec("Team").setIndicator("Team")
                , (tabInfo = new TabInfo("Team", TeamTab2Fragment.class, savedInstanceState)));

        this.mapTabInfo.put(tabInfo.tag, tabInfo);


        this.tabHost.setOnTabChangedListener(this);

        Log.d("UserSearchDialog Ac ==", "4");

    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initializeViewPager(){

        List<Fragment> fragments = new Vector<Fragment>();


        Log.d("UserSearchDialog Ac ==", "10");

        fragments.add(Fragment.instantiate(this, UserTab1Fragment.class.getName()));

        Log.d("UserSearchDialog Ac ==", "10.1");

        fragments.add(Fragment.instantiate(this, TeamTab2Fragment.class.getName()));

        Log.d("UserSearchDialog Ac ==", "11");

        this.dialogTab3Adapter = new DialogTab3UserFragmentAdapter(super.getSupportFragmentManager(),fragments);
        //
        Log.d("UserSearchDialog Ac ==", "12");

        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.viewPager.setAdapter(this.dialogTab3Adapter);
        this.viewPager.setOnPageChangeListener(this);


        Log.d("UserSearchDialog Ac ==", "13");

    }


    private static void AddTab(UserSearchDialogActivity
                                       userSearchDialogActivity
            , TabHost tabHost
            , TabHost.TabSpec tabSpec
            , TabInfo user1) {


        tabSpec.setContent(new TabFactory(userSearchDialogActivity));
        tabHost.addTab(tabSpec);

    }



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