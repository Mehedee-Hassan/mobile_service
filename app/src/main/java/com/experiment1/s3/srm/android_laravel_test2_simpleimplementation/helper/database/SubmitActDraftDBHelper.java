package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view.CheckList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User-8.1 on 10/15/2015.
 */
public class SubmitActDraftDBHelper extends DatabaseHelper {
    public SubmitActDraftDBHelper(Context context) {
        super(context);
    }


    public void saveGeneralTabData(Permit generalTabDataAsPermit){


        SQLiteDatabase db = this.getWritableDatabase();

        //delete previous draft data
        db.rawQuery("DELETE FROM general_tab_draft_table",null);

        //insert data make new draft

        ContentValues contentValues = new ContentValues();
        contentValues.put("permit_id" ,generalTabDataAsPermit.auto_gen_permit_no); //todo confusion
        contentValues.put("project_id" ,generalTabDataAsPermit.project_id);
        contentValues.put("project_name" ,generalTabDataAsPermit.project_name);
        contentValues.put("permit_template_id" ,generalTabDataAsPermit.permit_template_id);
        contentValues.put("permit_name" ,generalTabDataAsPermit.permit_name);
        contentValues.put("contractor" ,generalTabDataAsPermit.contractor);
        contentValues.put("location" ,generalTabDataAsPermit.location);
        contentValues.put("work_activity" ,generalTabDataAsPermit.work_activity);
        contentValues.put("permit_date" ,generalTabDataAsPermit.permit_date);
        contentValues.put("start_time" ,generalTabDataAsPermit.start_time);
        contentValues.put("end_time", generalTabDataAsPermit.end_time);


        db.insert("general_tab_draft_table", null, contentValues);



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


    public List<CheckList> getCheckListTabData(){


        CheckList checkList;
        List<CheckList> returnList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("SELECT * FROM " +
                "check_list_tab_draft_table"
                , null);


        cr.moveToFirst();

        while (!cr.isAfterLast()){

            checkList = new CheckList();
            checkList.yesOptions = cr.getInt(cr.getColumnIndexOrThrow("options"));

            returnList.add(checkList);

        }

        //delete all draft after returning
        db.rawQuery("DELETE FROM " +
                "check_list_tab_draft_table"
                ,null);

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




}
