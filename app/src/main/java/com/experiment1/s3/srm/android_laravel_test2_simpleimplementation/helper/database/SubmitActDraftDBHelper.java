package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view.CheckList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-8.1 on 10/15/2015.
 */
public class SubmitActDraftDBHelper extends DatabaseHelper {
    Context context;

    public SubmitActDraftDBHelper(Context context) {
        super(context);
        this.context = context;
    }


    public void saveGeneralTabData(Permit generalTabDataAsPermit ,Activity activity){


        SQLiteDatabase db = this.getWritableDatabase();

        //delete previous draft data
      //  db.rawQuery("DELETE FROM general_tab_draft_table",null);

        //insert data make new draft

        ContentValues contentValues = new ContentValues();
        contentValues.put("project_id" ,generalTabDataAsPermit.project_id);
        contentValues.put("project_name" ,generalTabDataAsPermit.project_name);
        contentValues.put("permit_template_id" ,generalTabDataAsPermit.permit_template_id);
        contentValues.put("permit_name" ,generalTabDataAsPermit.permit_name);
        contentValues.put("permit_no" ,generalTabDataAsPermit.auto_gen_permit_no);
        contentValues.put("contractor" ,generalTabDataAsPermit.contractor);
        contentValues.put("location" ,generalTabDataAsPermit.location);
        contentValues.put("work_activity" ,generalTabDataAsPermit.work_activity);
        contentValues.put("permit_date" ,generalTabDataAsPermit.permit_date);
        contentValues.put("start_time", generalTabDataAsPermit.start_time);
        contentValues.put("end_time", generalTabDataAsPermit.end_time);


        String[] args = new String[] {generalTabDataAsPermit.auto_gen_permit_no};


        Log.d(" == ", "save " + generalTabDataAsPermit.auto_gen_permit_no);

        int number= db.update("permit", contentValues, "permit_no =?"
                , args);


        if(number <= 0){
          //   long savedId = db.insert("permit" ,null ,contentValues);
            Log.d("==", "insert ");
        }


//        +", permit_id integer "
//                +", project_id integer "
//                +", project_name varchar(255)"
//                +", permit_template_id integer "
//                +", permit_name varchar(255)"
//                +", contractor varchar(255)"
//                +", location varchar(255)"
//                +", work_activity varchar(255)"
//                +", permit_date datetime"
//                +", start_time datetime"
//                +", end_time datetime"
    }

    public void saveCheckListTabData(List<CheckList> checkListFroYesOptions) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues;


        for(CheckList checkListObject :checkListFroYesOptions) {
            contentValues = new ContentValues();
            contentValues.put("potions", checkListObject.yesOptions);
            contentValues.put("permit_template_details_id", checkListObject.checkListTemplateDetailsId);
            db.insert("check_list_tab_draft_table",null ,contentValues);
        }



    }




    public Permit getGeneralTabData(){

        Permit permitForGEneratlTab = new Permit();
        SQLiteDatabase db  = this.getWritableDatabase();



        Cursor cr = db.rawQuery("SELECT * FROM " +
                "general_tab_draft_table;" ,null);

        cr.moveToFirst();


        while(!cr.isAfterLast()){
            permitForGEneratlTab.project_id = cr.getInt(cr.getColumnIndexOrThrow("project_id"));
            permitForGEneratlTab.project_name = cr.getString(cr.getColumnIndexOrThrow("project_name"));
            permitForGEneratlTab.permit_template_id = cr.getInt(cr.getColumnIndexOrThrow("permit_template_id"));
            permitForGEneratlTab.permit_name = cr.getString(cr.getColumnIndexOrThrow("permit_name"));
            permitForGEneratlTab.contractor = cr.getString(cr.getColumnIndexOrThrow("permit_name"));
            permitForGEneratlTab.location = cr.getString(cr.getColumnIndexOrThrow("permit_name"));
            permitForGEneratlTab.work_activity = cr.getString(cr.getColumnIndexOrThrow("permit_name"));
            permitForGEneratlTab.permit_date = cr.getString(cr.getColumnIndexOrThrow("permit_name"));
            permitForGEneratlTab.start_time = cr.getString(cr.getColumnIndexOrThrow("permit_name"));
            permitForGEneratlTab.end_time = cr.getString(cr.getColumnIndexOrThrow("permit_name"));


            cr.moveToNext();
        }

        db.rawQuery("DELETE FROM " +
                "general_tab_draft_table",null);

        cr.close();

        return permitForGEneratlTab;
    }


    public List<CheckList> getCheckListTabDataForServer( long permit_server_id){


        CheckList checkList;
        List<CheckList> returnList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("SELECT status FROM " +
                "permit_details " +
                " WHERE " +
                "permit_id = "

                +
                permit_server_id
                //permit_id
                +" " +
                "ORDER BY " +
                "sno " +
                Constants.PERMIT_DETAILS_QUESTION_ORDER
                , null);


        cr.moveToFirst();

        while (!cr.isAfterLast()){

            checkList = new CheckList();
            String status ="";
                   status = cr.getString(cr.getColumnIndexOrThrow("status"));



            Log.d(" =======* ",""+status);

            if(status.contentEquals("NULL") || status.contentEquals("null")){
                checkList.yesOptions = 0;
            }

            if(status.contentEquals("OK") || status.contentEquals("ok")){
                checkList.yesOptions = 1;

            }

            if(status.contentEquals("NOK") || status.contentEquals("nok") ){
                checkList.yesOptions = 2;

            }

            if(status.contentEquals("NA") || status.contentEquals("na")){
                checkList.yesOptions = 3;

            }



            returnList.add(checkList);
            cr.moveToNext();
        }



        cr.close();

        return returnList;
    }




    public List<CheckList> getCheckListTabData( long permit_id){


        CheckList checkList;
        List<CheckList> returnList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();


        //for user role 3 permit will be offline so the permit id will not support the server id

        Cursor cr = db.rawQuery("SELECT status FROM " +
                "permit_details " +
                " WHERE " +
                "permit_id_local = "

                +
                permit_id
                //permit_id
                + " " +
                "ORDER BY " +
                "sno " +
                Constants.PERMIT_DETAILS_QUESTION_ORDER
                , null);


        cr.moveToFirst();

        while (!cr.isAfterLast()){

            checkList = new CheckList();
            String status ="";
                   status = cr.getString(cr.getColumnIndexOrThrow("status"));



            Log.d(" =======* ",""+status);

            if(status.contentEquals("NULL") || status.contentEquals("null")){
                checkList.yesOptions = 0;
            }

            if(status.contentEquals("OK") || status.contentEquals("ok")){
                checkList.yesOptions = 1;

            }

            if(status.contentEquals("NOK") || status.contentEquals("nok") ){
                checkList.yesOptions = 2;

            }

            if(status.contentEquals("NA") || status.contentEquals("na")){
                checkList.yesOptions = 3;

            }



            returnList.add(checkList);
            cr.moveToNext();
        }



        cr.close();

        return returnList;
    }



    public boolean checkIfDraftGeneralIsEmpty(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("SELECT Count(*) FROM " +
                "general_tab_draft_table"
                ,null);


        cr.moveToFirst();


        int count = cr.getInt(0);
        if( count > 0){

            cr.close();
            return false;
        }

        cr.close();

        return true;
    }


    public boolean checkIfDraftCheckListIsEmpty(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("SELECT Count(*) FROM " +
                "check_list_tab_draft_table"
                , null);


        cr.moveToFirst();


        int count = cr.getInt(0);
        if( count > 0){

            cr.close();
            return false;
        }

        cr.close();

        return true;
    }


//    dummy

    public void currentStateIsDraftedInDB(PermitTemplate permitTemplate, Project project, String permitNumber) {
//not used

//        SQLiteDatabase db = this.getWritableDatabase();
//
//
//        long permit_id = insertIntoPermiTable(permitTemplate, project, permitNumber, db);
//
//
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("permit_id" ,permit_id);
//        contentValues.put("status" ,"draft");
//
//        db.insert("permit_details",null ,contentValues);


    }



    private long insertIntoPermiTable(PermitTemplate permitTemplate, Project project, String permitNumber, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("project_id" ,project.id);
        contentValues.put("permit_no"  ,permitNumber);
        contentValues.put("project_name"  ,project.name);
        contentValues.put("permit_template_id", permitTemplate.id);
        contentValues.put("permit_name", permitTemplate.name);

       long rowId =  db.insert("permit", null, contentValues);

        return  rowId;
    }



    public Permit getPermitDraftWith(long id ,String auto_gen_permit_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("SELECT * FROM " +
                " permit " +
                "WHERE " +
                "_id = " + id
                +" ;"
                , null);


        cr.moveToFirst();


        Permit permit = new Permit();
        while (!cr.isAfterLast()){

            permit.project_id = cr.getInt(cr.getColumnIndexOrThrow("project_id"));
            permit.server_permit_id = cr.getInt(cr.getColumnIndexOrThrow("permit_id"));
            permit.project_name =cr.getString(cr.getColumnIndexOrThrow("project_name"));
            permit.permit_template_id = cr.getInt(cr.getColumnIndexOrThrow("permit_template_id"));
            permit.permit_name = cr.getString(cr.getColumnIndexOrThrow("permit_name"));
            permit.auto_gen_permit_no = auto_gen_permit_no;
            permit.work_activity = cr.getString(cr.getColumnIndexOrThrow("work_activity"));
            permit.contractor = cr.getString(cr.getColumnIndexOrThrow("contractor"));
            permit.location = cr.getString(cr.getColumnIndexOrThrow("location"));
            permit.permit_date = cr.getString(cr.getColumnIndexOrThrow("permit_date"));
            permit.start_time = cr.getString(cr.getColumnIndexOrThrow("start_time"));
            permit.end_time = cr.getString(cr.getColumnIndexOrThrow("end_time"));
            permit.created_by = cr.getInt(cr.getColumnIndexOrThrow("created_by"));




            cr.moveToNext();


        }


        return permit;
    }

    public Permit getPermitDraftServerSavedWith(long permit_server_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("SELECT * FROM " +
                " permit " +
                "WHERE " +
                " permit_id = " + permit_server_id
                +" ;"
                , null);


        cr.moveToFirst();


        Permit permit = new Permit();
        while (!cr.isAfterLast()){

            permit.project_id = cr.getInt(cr.getColumnIndexOrThrow("project_id"));
            permit.server_permit_id = cr.getInt(cr.getColumnIndexOrThrow("permit_id"));
            permit.project_name =cr.getString(cr.getColumnIndexOrThrow("project_name"));
            permit.permit_template_id = cr.getInt(cr.getColumnIndexOrThrow("permit_template_id"));
            permit.permit_name = cr.getString(cr.getColumnIndexOrThrow("permit_name"));
            permit.auto_gen_permit_no = cr.getString(cr.getColumnIndexOrThrow("permit_no"));
            permit.work_activity = cr.getString(cr.getColumnIndexOrThrow("work_activity"));
            permit.contractor = cr.getString(cr.getColumnIndexOrThrow("contractor"));
            permit.location = cr.getString(cr.getColumnIndexOrThrow("location"));
            permit.permit_date = cr.getString(cr.getColumnIndexOrThrow("permit_date"));
            permit.start_time = cr.getString(cr.getColumnIndexOrThrow("start_time"));
            permit.end_time = cr.getString(cr.getColumnIndexOrThrow("end_time"));
            permit.created_by = cr.getInt(cr.getColumnIndexOrThrow("created_by"));




            cr.moveToNext();


        }


        return permit;
    }

    public boolean checkIfPermiQuestionIsEmpetyFor(long permitId) {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cr = db.rawQuery("SELECT Count(*) FROM " +
                " permit_details " +
                " WHERE " +
                " permit_id = "
                + permitId
                , null);

        Log.d("permitid ", "" + permitId);

        cr.moveToFirst();
        int count = cr.getInt(0);

        if(count > 0) {

            cr.close();

            return false;
        }
cr.close();
        return true;
    }

    public void transferPermitTempQToPermitDet(long permitId ,int permitTemplateId) {

        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cr = db.rawQuery("SELECT * FROM " +
                " permit_template_details " +
                " WHERE " +
                " permit_template_id = "
                + permitTemplateId
                , null);


        cr.moveToFirst();
        ContentValues contentValues;


        while (!cr.isAfterLast()){

            contentValues = new ContentValues();

            contentValues.put("question" ,cr.getString(cr.getColumnIndexOrThrow("question")));
            contentValues.put("permit_id_local" , permitId);
            contentValues.put("status" , "NULL");
            contentValues.put("sno" , cr.getInt(cr.getColumnIndexOrThrow("sno")));

            db.insert("permit_details" ,null ,contentValues);

            cr.moveToNext();
        }

        cr.close();

    }


    public void saveCheckListStatus(int permitId ,String status){

            SQLiteDatabase db = this.getWritableDatabase();

                String[] args = new String[] {permitId+""};

                ContentValues contentValues;
                contentValues = new ContentValues();

        contentValues.put("status" , status);

        db.update("permit_details", contentValues, " permit_id = ?", args);



    }

    public void saveCheckListStatus(CheckList checkList){

            SQLiteDatabase db = this.getWritableDatabase();

//                String[] args = new String[] {checkList.permit_id};

                ContentValues contentValues;
                contentValues = new ContentValues();


        String status = "";

        if(checkList.yesOptions == 0){
            contentValues.put("status" , "NULL");
            status = "NULL";

        }
        else
        if(checkList.yesOptions == 1){
            contentValues.put("status" , "OK");
            status = "OK";

        }
        else
        if(checkList.yesOptions == 2){
            contentValues.put("status" , "NOK");
            status = "NOK";

        }
        else
        if(checkList.yesOptions == 3){
            contentValues.put("status" , "NA");
            status = "NA";

        }



        Log.d("save", "update ==" + checkList.yesOptions + " at  =" + checkList.permit_id);
//        db.update("permit_details", contentValues, " permit_id = ? " ,args );

        db.execSQL("UPDATE permit_details " +
                "SET status='" + status + "'"
                + " WHERE _id=" + checkList._id);



    }

    public int getPermitDetailsServerId(int id) {


        SQLiteDatabase db = this.getWritableDatabase();

        int idToRet = 0;
        Cursor cr = db.rawQuery("SELECT server_id FROM " +
                " permit_details " +
                " WHERE " +
                " _id = "
                + id
                , null);

        if(cr.getCount() >0){
            cr.moveToFirst();

            try{

               idToRet = cr.getInt(cr.getColumnIndexOrThrow("server_id"));

            }catch (Exception e){
                e.getMessage();
            }


        }

        if(cr!= null)
            cr.close();

    return  idToRet;
    }


}
