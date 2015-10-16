package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.helper.query;

/**
 * Created by User-8.1 on 10/15/2015.
 */
public class CurrentQuery {

    public int project_id;
    public String project_name;
    //todo sure that project object can be defined here


    public int permit_template_id;



    public CurrentQuery(int project_id ,String project_name ,int permit_template_id){
        this.project_id = project_id;
        this.project_name = project_name;
        this.permit_template_id = permit_template_id;

    }

}
