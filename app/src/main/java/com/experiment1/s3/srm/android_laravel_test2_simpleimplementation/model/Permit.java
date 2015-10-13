package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model;

/**
 * Created by Mhr on 10/3/2015.
 */
public class Permit {

    public int id;
//    public int permit_id;
    public String auto_gen_permit_no;
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


}



//+" permit" +
//        "(id integer primary key autoincrement"
//        +", permit_id integer "
//        +", project_name varchar(255)"
//        +", permit_template_id integer "
//        +", permit_name varchar(255)"
//        +", contractor varchar(255)"
//        +", location varchar(255)"
//        +", work_activity varchar(255)"
//        +", permit_date datetime"
//        +", start_time datetime"
//        +", end_time datetime"
//        +", created_by integer"
//        +", FOREIGN KEY(created_by) REFERENCES USER_TABLE(id)"
//        +", FOREIGN KEY(permit_template_id) REFERENCES permit_templates(id)"
//        +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
//        +");");