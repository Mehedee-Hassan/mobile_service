package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants;

import android.app.Application;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt1.Tab1GeneralFragmentEventConnector;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PTWType;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.User;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.dialog.tab3.UserTab1Fragment;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab1general;

/**
 * Created by Mhr on 10/5/2015.
 */
public class GlobalVars extends Application {


        private static GlobalVars  globalVars;

        public Project nonStaticProject;
        public int currentPermitTemplateId;
        private Tab1GeneralFragmentEventConnector tab1GeneralFragmentEventConnector;
        private boolean isLoginActStdFromSubmit = false;
        private Tab1GeneralFragmentEventConnector submitActTab1GenInterface;
        private boolean ifLoggedIn;
        private String permitNumber;
        private PTWType permitTemplate;
        private User currentLoggedInUser;



    public static GlobalVars getInstance(){

           return globalVars;

       }

        public Project getProject(){
            return nonStaticProject;
        }
        public void setProject(Project project){
            nonStaticProject = project;
        }


    public Tab1GeneralFragmentEventConnector setUsrFragmentEventConnectorInterface(){
        return this.tab1GeneralFragmentEventConnector;
    }

    public void setUsrFragmentEventConnectorInterface(Tab1GeneralFragmentEventConnector tab1GeneralFragmentEventConnector) {
        this.tab1GeneralFragmentEventConnector = tab1GeneralFragmentEventConnector;
    }

    public void setIsLoginActStdFromSubmit(boolean isLoginActStdFromSubmit) {
        this.isLoginActStdFromSubmit = isLoginActStdFromSubmit;
    }


    public boolean getIsLoginActStdFromSubmit() {
        return  this.isLoginActStdFromSubmit;
    }

    public void setSubmitActTab1GenInterface(Tab1general submitActTab1GenInterface) {
        this.submitActTab1GenInterface = submitActTab1GenInterface;
    }

    public Tab1GeneralFragmentEventConnector getSubmitActTab1GenInterface() {
        return this.submitActTab1GenInterface;
    }

    public void setIfLoggedIn(boolean ifLoggedIn) {
        this.ifLoggedIn = ifLoggedIn;
    }

    public boolean getIfLoggedIn() {
        return this.ifLoggedIn;
    }

    public void setPermitNumber(String permitNumber) {
        this.permitNumber = permitNumber;
    }


    public String getPermitNumber() {
        return this.permitNumber;
    }

    public void setPermitTemplate(PTWType permitTemplate) {
        this.permitTemplate = permitTemplate;
    }

    public PTWType getPermitTemplate() {
       return this.permitTemplate;
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }


}
