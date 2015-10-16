package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.variables;

import android.app.Application;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;

/**
 * Created by Mhr on 10/4/2015.
 */
public class CurrentVars extends Application{


    public static Project PROJECT;
    public static PermitTemplate PERMIT_Template;
    public static CurrentVars current_vars;


    public Project nonStaticProj;

    public Project getProject(){
        return nonStaticProj;
    }
    public void setProject(Project project){
        nonStaticProj = project;
    }

}
