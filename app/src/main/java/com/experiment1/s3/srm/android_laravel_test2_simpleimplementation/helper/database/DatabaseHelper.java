package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;

/**
 * Created by Mhr on 9/28/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public String PROJECT_TABLE_NAME= "Projects";
    public String PROJECT_TABLE_COL_PROJECT_ID = "project_id";
    public String PROJECT_TABLE_COL_PROJECT_NAME = "project_name";

    protected SQLiteDatabase mainDatabase;


   public DatabaseHelper(Context context){

       super(context, Constants.DATABASE_NAME ,null ,1);
   }

    public void initDatabase(){



    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        mainDatabase = db;

        db.execSQL("CREATE TABLE IF NOT EXISTS " +PROJECT_TABLE_NAME
                +"(id integer primary key autoincrement," +
                PROJECT_TABLE_COL_PROJECT_ID+" integer ," +
                PROJECT_TABLE_COL_PROJECT_NAME+" text);");


        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" WORKER_TABLE" +
                "(id integer primary key autoincrement"
                +", name varchar(200)"
                +", designation varchar(100)"
                +", NRIC integer"
                +");");


        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" PTW_TYPE_TABLE" +
                "(id integer primary key autoincrement"
                +", type varchar(200)"
                +", project_id integer"
                +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
                +");");




        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" CHECK_LIST_QUESTION_TABLE" +
                "(id integer primary key autoincrement"
                +", question text"
                +", type text"
                +", ptw_id integer"
                +", FOREIGN KEY(ptw_id) REFERENCES PTW_TYPE_TABLE(id)"
                +");");



        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" ANSWER_TABLE" +
                "(id integer primary key autoincrement"
                +", textra_text text"
                +", option integer"
                +", check_list_que_id integer"
                +", project_id integer"
                +", FOREIGN KEY(check_list_que_id) REFERENCES Check_list_QUESTION_TABLE(id)"
                +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
                +");");




        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" PTW" +
                "(id integer primary key autoincrement"
                +", workdescription text"
                +", location text"
                +", contractor text"
                +", start_date_time datetime"
                +", status varchar(10)"
                +", approved_date_time datetime"
                +", approved_by integer"
                +", validate_date_time datetime"
                +", validate_by integer"
                +", applied_date_time datetime"
                +", applied_by integer"
                +", end_date_time datetime"
                +", project_id integer"
                +", ptw_type_id integer"
                +", worker_id integer"
                +", answer_id integer"
                +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
                +", FOREIGN KEY(ptw_type_id) REFERENCES PTW_TYPE_TABLE(id)"
                +", FOREIGN KEY(worker_id) REFERENCES WORKER_TABLE(id)"
                +", FOREIGN KEY(answer_id) REFERENCES ANSWER_TABLE(id)"
                +");");



        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" USER_TABLE" +
                "(id integer primary key autoincrement"
                +", name varchar(200)"
                +", email varchar(200)"
                +", password varchar(60)"
                +", picture varchar(200)"
                +", remember_token varchar(200)"
                +", company_id integer"
                +", role integer"
                +", project_id integer"
                +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
                +");");


//      new table

        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" permit" +
                "(_id integer primary key autoincrement"
                +", permit_id integer "
                +", permit_no integer "
                +", project_id integer "
                +", project_name varchar(255)"
                +", permit_template_id integer "
                +", permit_name varchar(255)"
                +", contractor varchar(255)"
                +", location varchar(255)"
                +", work_activity varchar(255)"
                +", permit_date datetime"
                +", start_time datetime"
                +", end_time datetime"
                +", created_by integer"
                +", FOREIGN KEY(created_by) REFERENCES USER_TABLE(id)"
                +", FOREIGN KEY(permit_template_id) REFERENCES permit_templates(id)"
                +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
                +");");








        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" permit_details" +
                "(_id integer primary key autoincrement"
                +", question varchar(255)"
                +", extra_text varchar(50)"
                +", status varchar(5)"
                +", allowed_text integer "
                +", permit_id integer "
                +", FOREIGN KEY(permit_id) REFERENCES permits(id)"
                +");");


        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" permit_template_details" +
                "(_id integer primary key autoincrement"
                +", question varchar(255)"
                +", extra_text integer"
                +", sno integer "
                +", permit_template_id integer "
                +", FOREIGN KEY(permit_template_id) REFERENCES permit_templates(id)"
                +");");


        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" permit_templates" +
                "(_id integer primary key autoincrement"
                +", name varchar(50)"
                +", company_id integer"
                +");");


        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" general_tab_draft_table" +
                "(_id integer primary key autoincrement"
                +", permit_id integer "
                +", project_id integer "
                +", project_name varchar(255)"
                +", permit_template_id integer "
                +", permit_name varchar(255)"
                +", contractor varchar(255)"
                +", location varchar(255)"
                +", work_activity varchar(255)"
                +", permit_date datetime"
                +", start_time datetime"
                +", end_time datetime"
                +");");

        db.execSQL("CREATE TABLE IF NOT EXISTS"
                +" check_list_tab_draft_table" +
                "(_id integer primary key autoincrement"
                +", permit_template_details_id int"
                +", options int"
                +", FOREIGN KEY (permit_template_details_id) REFERENCES permit_details(id)"
                +");");






//        db.execSQL("CREATE TABLE IF NOT EXISTS " +
//                "(id integer primary key autoincrement,");

        Log.d("sqlite ","on create");



        //insert dummy database
                insertDummy(db);
        //===

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PROJECT_TABLE_NAME);
        onCreate(db);
    }



    public void insertDummy (SQLiteDatabase db){



        try{

            insertDummyProject(db);
            insertDummyWorker(db);
            insertDummyPTWType(db);
            insertDummyCheckList(db);
            insertDummyAnswer(db);
            insertDummyPTW(db);
            insertDummyUser(db);
            insertDummyPermitTemplateDetails(db);

        }catch(Exception e){
            Log.d("dbinsert ==", e.getMessage());
        }

    }

    private void insertDummyPermitTemplateDetails(SQLiteDatabase db) {


//        db.execSQL("CREATE TABLE IF NOT EXISTS"
//                +" permit_template_details" +
//                "(id integer primary key autoincrement"
//                +", question varchar(255)"
//                +", extra_text integer"
//                +", sno integer "
//                +", permit_template_id integer "
//                +", FOREIGN KEY(permit_template_id) REFERENCES permit_templates(id)"
//                +");");


        ContentValues contentValues = new ContentValues();
        contentValues.put("question", "abc1 question");
        contentValues.put("extra_text", "abc1 extra text");
        contentValues.put("sno", 1);
        contentValues.put("permit_template_id", 1);
        db.insert("permit_template_details", null, contentValues);

         contentValues = new ContentValues();
        contentValues.put("question", "abc2 question");
        contentValues.put("extra_text", "abc2 extra text");
        contentValues.put("sno", 1);
        contentValues.put("permit_template_id", 1);
        db.insert("permit_template_details", null, contentValues);

         contentValues = new ContentValues();
        contentValues.put("question", "abc3 question");
        contentValues.put("extra_text", "abc3 extra text");
        contentValues.put("sno", 1);
        contentValues.put("permit_template_id", 1);
        db.insert("permit_template_details", null, contentValues);

    }


    private void insertDummyProject(SQLiteDatabase db){


//        db.execSQL("CREATE TABLE IF NOT EXISTS"
//                +" ANSWER_TABLE" +
//                "(id integer primary key autoincrement"
//                +", textra_text text"
//                +", option integer"
//                +", check_list_que_id integer"
//                +", project_id integer"
//                +", FOREIGN KEY(check_list_que_id) REFERENCES Check_list_QUESTION_TABLE(id)"
//                +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
//                +");");


            ContentValues contentValues = new ContentValues();
            contentValues.put("project_id", "1");
            contentValues.put("project_name", "abc project 1");;
            db.insert("Projects", null, contentValues);


             contentValues = new ContentValues();
            contentValues.put("project_id", "2");
            contentValues.put("project_name", "abc project 2");
            db.insert("Projects", null, contentValues);


        }


    private void insertDummyUser(SQLiteDatabase db){





            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "abc user1");
            contentValues.put("email", "abcuser1@gmail.com");;
            contentValues.put("role", 1);
            db.insert("USER_TABLE", null, contentValues);




           contentValues = new ContentValues();
            contentValues.put("name", "abc user2");
            contentValues.put("email", "abcuser2@gmail.com");;
            contentValues.put("role", 1);
            db.insert("USER_TABLE", null, contentValues);




           contentValues = new ContentValues();
            contentValues.put("name", "abc user3");
            contentValues.put("email", "abcuser3@gmail.com");;
            contentValues.put("role", 1);
            db.insert("USER_TABLE", null, contentValues);



        }


    private void insertDummyAnswer(SQLiteDatabase db){


//        db.execSQL("CREATE TABLE IF NOT EXISTS"
//                +" ANSWER_TABLE" +
//                "(id integer primary key autoincrement"
//                +", textra_text text"
//                +", option integer"
//                +", check_list_que_id integer"
//                +", project_id integer"
//                +", FOREIGN KEY(check_list_que_id) REFERENCES Check_list_QUESTION_TABLE(id)"
//                +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
//                +");");




        ContentValues contentValues = new ContentValues();
        contentValues.put("textra_text", "abc text");
        contentValues.put("option", 1);
        contentValues.put("check_list_que_id", 1);
        contentValues.put("project_id", 1);
        db.insert("ANSWER_TABLE", null, contentValues);


    }


    private void insertDummyWorker(SQLiteDatabase db){

//        db.execSQL("CREATE TABLE IF NOT EXISTS"
//                +" WORKER_TABLE" +
//                "(id integer primary key autoincrement"
//                +", name varchar(200)"
//                +", designation varchar(100)"
//                +", NRIC integer"
//                +");");



        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "abc worker");
        contentValues.put("designation", "abc1 designation");
        contentValues.put("NRIC", 1);
        db.insert("WORKER_TABLE", null, contentValues);
    }


    private void insertDummyPTW(SQLiteDatabase db){

//        db.execSQL("CREATE TABLE IF NOT EXISTS"
//                +" PTW" +
//                "(id integer primary key autoincrement"
//                +", workdescription text"
//                +", location text"
//                +", contractor text"
//                +", start_date_time datetime"
//                +", status varchar(10)"
//                +", approved_date_time datetime"
//                +", approved_by integer"
//                +", validate_date_time datetime"
//                +", validate_by integer"
//                +", applied_date_time datetime"
//                +", applied_by integer"
//                +", end_date_time datetime"
//                +", project_id integer"
//                +", ptw_type_id integer"
//                +", worker_id integer"
//                +", answer_id integer"
//                +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
//                +", FOREIGN KEY(ptw_type_id) REFERENCES PTW_TYPE_TABLE(id)"
//                +", FOREIGN KEY(worker_id) REFERENCES WORKER_TABLE(id)"
//                +", FOREIGN KEY(answer_id) REFERENCES ANSWER_TABLE(id)"
//                +");");



        ContentValues contentValues = new ContentValues();
        contentValues.put("location", "location 1");
        contentValues.put("contractor", "contractor 1");
        contentValues.put("start_date_time", "2015-11-03 10:20");
        contentValues.put("end_date_time", "2015-11-03 11:20");
        contentValues.put("status", "new");

        contentValues.put("project_id", 1);
        contentValues.put("ptw_type_id", 1);
        contentValues.put("worker_id", 1);
        contentValues.put("answer_id", 1);
        db.insert("PTW", null, contentValues);


        contentValues = new ContentValues();
        contentValues.put("location", "location 2");
        contentValues.put("contractor", "contractor 1");
        contentValues.put("start_date_time", "2015-11-04 09:05");
        contentValues.put("end_date_time", "2015-11-04 12:40");
        contentValues.put("status", "new");

        contentValues.put("project_id", 1);
        contentValues.put("ptw_type_id", 1);
        contentValues.put("worker_id", 1);
        contentValues.put("answer_id", 1);
        db.insert("PTW", null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("location", "location 3");
        contentValues.put("contractor", "contractor 1");
        contentValues.put("start_date_time", "2015-11-04 18:10");
        contentValues.put("end_date_time", "2015-11-04 22:30");
        contentValues.put("status", "new");

        contentValues.put("project_id", 1);
        contentValues.put("ptw_type_id", 1);
        contentValues.put("worker_id", 1);
        contentValues.put("answer_id", 1);
        db.insert("PTW", null, contentValues);


    }


    private void insertDummyPTWType(SQLiteDatabase db){
//        db.execSQL("CREATE TABLE IF NOT EXISTS"
//                +" PTW_TYPE_TABLE" +
//                "(id integer primary key autoincrement"
//                +", type varchar(200)"
//                +", project_id integer"
//                +", FOREIGN KEY(project_id) REFERENCES Projects(id)"
//                +");");





        ContentValues contentValues = new ContentValues();
        contentValues.put("type", "abc1 type");
        contentValues.put("project_id", 1);
        db.insert("PTW_TYPE_TABLE", null, contentValues);

        //
        //insert to permit_template
         contentValues = new ContentValues();
        contentValues.put("name", "abc1 type");
        contentValues.put("company_id", 1);
        db.insert("permit_templates", null, contentValues);

    }


    private void insertDummyCheckList(SQLiteDatabase db) {


//        db.execSQL("CREATE TABLE IF NOT EXISTS"
//                +" CHECK_LIST_QUESTION_TABLE" +
//                "(id integer primary key autoincrement"
//                +", question text"
//                +", type text"
//                +", ptw_id integer"
//                +", FOREIGN KEY(ptw_id) REFERENCES PTW_TYPE_TABLE(id)"
//                +");");




        ContentValues contentValues = new ContentValues();
        contentValues.put("question", "abc question1");
        contentValues.put("type", "abc1 type");
        contentValues.put("ptw_id", 1);
        db.insert("CHECK_LIST_QUESTION_TABLE", null, contentValues);

         contentValues = new ContentValues();
        contentValues.put("question", "abc question2");
        contentValues.put("type", "abc2 type");
        contentValues.put("ptw_id", 1);
        db.insert("CHECK_LIST_QUESTION_TABLE", null, contentValues);

         contentValues = new ContentValues();
        contentValues.put("question", "abc question3");
        contentValues.put("type", "abc3 type");
        contentValues.put("ptw_id", 1);
        db.insert("CHECK_LIST_QUESTION_TABLE", null, contentValues);

    }

}
