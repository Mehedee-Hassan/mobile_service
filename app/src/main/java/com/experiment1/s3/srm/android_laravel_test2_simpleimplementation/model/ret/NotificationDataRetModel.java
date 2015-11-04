package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.ret;

/**
 * Created by User-8.1 on 11/4/2015.
 */
public class NotificationDataRetModel {

    public String getPermitPermissionStatus() {
        return permitPermissionStatus;
    }
    public String getProjectName() {
        return projectName;
    }


    String permitPermissionStatus;
    String projectName;

    public int getPermitPermissionServerId() {
        return permitPermissionServerId;
    }

    int permitPermissionServerId;




    public NotificationDataRetModel(){

    }

    public NotificationDataRetModel(String permitPermissionStatus
            ,String projectName
            ,int permitPermissionServerId){

        this.permitPermissionServerId = permitPermissionServerId;
        this.permitPermissionStatus = permitPermissionStatus;
        this.projectName = projectName;

    }




}
