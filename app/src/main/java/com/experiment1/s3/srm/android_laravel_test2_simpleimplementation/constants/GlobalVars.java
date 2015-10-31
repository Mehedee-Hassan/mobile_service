package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants;

import android.app.Application;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt1.Tab1GeneralFragmentEventConnector;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.interf.submit.actt1.Tab2CheckListFragmentEventConnector;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.User;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval.tabs.Tab1generalApr;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.approval.tabs.Tab2ChecklistApr;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab1general;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.submit.activity.tabs.Tab2Checklist;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.validate.tabs.Tab1generalVal;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui.validate.tabs.Tab2ChecklistVal;

/**
 * Created by Mhr on 10/5/2015.
 */
public class GlobalVars extends Application {


        private static GlobalVars  globalVars;

        public Project nonStaticProject;
//        public int currentPermitTemplateId;

        private Tab1GeneralFragmentEventConnector tab1GeneralFragmentEventConnector;
        private Tab1GeneralFragmentEventConnector submitActTab1GenInterface;

        private Tab1GeneralFragmentEventConnector tab1GeneralFragmentEventConnector2;
        private Tab1GeneralFragmentEventConnector submitActTab1GenInterface2;
        private Tab1GeneralFragmentEventConnector submitActTab1GenInterface3;


    private boolean isLoginActStdFromSubmit = false;
        private Tab2CheckListFragmentEventConnector submitActTab2CheckInterface;
        private Tab2CheckListFragmentEventConnector submitActTab2CheckInterface2;
        private Tab2CheckListFragmentEventConnector submitActTab2CheckInterface3;
        private boolean ifLoggedIn;
        private String permitNumber;
        private PermitTemplate permitTemplate;
        private User currentLoggedInUser;
        private Permit permit;


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


    public void setSubmitActTab1GenInterfaceVal(Tab1generalVal submitActTab1GenInterface) {
        this.submitActTab1GenInterface2 = submitActTab1GenInterface;
    }

    public void setSubmitActTab1GenInterfaceApr(Tab1generalApr submitActTab1GenInterface) {
        this.submitActTab1GenInterface2 = submitActTab1GenInterface;
    }







    public void setSubmitActTab2CheckInterface(Tab2Checklist submitActTab2CheckInterface) {
        this.submitActTab2CheckInterface = submitActTab2CheckInterface;
    }



    public Tab1GeneralFragmentEventConnector getSubmitActTab1GenInterface() {
        return this.submitActTab1GenInterface;
    }


    public Tab1GeneralFragmentEventConnector getSubmitActTab1GenInterfaceApr() {
        return this.submitActTab1GenInterface3;
    }

    public Tab1GeneralFragmentEventConnector getSubmitActTab1GenInterfaceVal() {
        return this.submitActTab1GenInterface2;
    }

    public Tab2CheckListFragmentEventConnector getSubmitActTab2ChecklistInterface() {
        return this.submitActTab2CheckInterface;
    }


    public void setSubmitActTab2CheckInterfaceVal(Tab2ChecklistVal submitActTab2CheckInterface) {
        this.submitActTab2CheckInterface2 = submitActTab2CheckInterface;
    }
    public Tab2CheckListFragmentEventConnector getSubmitActTab2ChecklistInterfaceVal() {
        return this.submitActTab2CheckInterface2;
    }


    public void setSubmitActTab2CheckInterfaceApr(Tab2ChecklistApr submitActTab2CheckInterface) {
        this.submitActTab2CheckInterface3 = submitActTab2CheckInterface;
    }
    public Tab2CheckListFragmentEventConnector getSubmitActTab2ChecklistInterfaceApr() {
        return this.submitActTab2CheckInterface3;
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

    public void setPermitTemplate(PermitTemplate permitTemplate) {
        this.permitTemplate = permitTemplate;
    }

    public PermitTemplate getPermitTemplate() {
       return this.permitTemplate;
    }




    public void setPermit(Permit permit){
        this.permit = permit ;
    }
    public Permit getPermit(){

        return this.permit;

    }

    public Permit getNotNullPermit(){
        if(this.permit == null){
            this.permit = new Permit();
            return this.permit;
        }

        return this.permit;
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }


}
