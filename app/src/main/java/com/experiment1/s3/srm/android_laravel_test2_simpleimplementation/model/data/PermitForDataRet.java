package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.data;

/**
 * Created by Mhr on 10/3/2015.
 */
public class PermitForDataRet {

    public long id; // server id

//    public long server_permit_id;

//    public int permit_id;
    public String permit_no;
    public int project_id;
    public String project_name;
    public int permit_template_id;
    public String permit_name;
    public String contractor ;
    public String location;
    public String work_activity;
    public String permit_date;
    public String start_time;
    public String end_time;
    public int created_by;

    public String status;

    public PermitForDataRet(){
        created_by = 0;
    }
}


