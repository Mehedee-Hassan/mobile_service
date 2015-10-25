package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitPermission;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplateDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 10/5/2015.
 */
public class PermitTemplateDBHelper extends DatabaseHelper {
    public PermitTemplateDBHelper(Context context) {
        super(context);
    }


    public void insertToPermitTemplateDetails(List<PermitTemplateDetails> permitTemplateDetails) {


    }





    public List<PermitDetails>getPtDetailsListWhereForValChkListPTId(long pt_id){
        List<PermitDetails> permitTemplateDetails2 = new ArrayList<PermitDetails>();

        Log.d("tab2 db helper ==", "permit_id 1");

        PermitDetails permitDetails1;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("select * " +
                "from " +
                "permit_details " +
                "WHERE " +
                " permit_id= "+pt_id +" " +
                "ORDER BY sno" +
                Constants.PERMIT_DETAILS_QUESTION_ORDER +
                ";", null);


        Log.d("tab2 db helper ==", "permit_id 2");

        cr.moveToFirst();

        while(!cr.isAfterLast())
        {

            permitDetails1 = new PermitDetails();

            permitDetails1.question = cr.getString(cr.getColumnIndexOrThrow("question"));
            permitDetails1.extra_text = cr.getString(cr.getColumnIndexOrThrow("extra_text"));
            permitDetails1.status = cr.getString(cr.getColumnIndexOrThrow("status"));
            permitDetails1.id = cr.getInt(cr.getColumnIndexOrThrow("_id"));
            permitDetails1.sno = cr.getInt(cr.getColumnIndexOrThrow("sno"));
            permitDetails1.permit_id = cr.getInt(cr.getColumnIndexOrThrow("permit_id"));

            Log.d("in ret from data ==", "" + permitDetails1.status);


            permitTemplateDetails2.add(permitDetails1);

            cr.moveToNext();


        }
        Log.d("tab2 db helper==", "permit_id 3");


        cr.close();




        return permitTemplateDetails2;
    }



    public List<PermitDetails>getPermitTemplateDetailsListWherePTId(long pt_id){
        List<PermitDetails> permitTemplateDetails2 = new ArrayList<PermitDetails>();

        Log.d("tab2 db helper ==", "permit_id 1");

        PermitDetails permitDetails1;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("select * " +
                "from " +
                "permit_details " +
                "WHERE " +
                " permit_id= "+pt_id +" " +
                "ORDER BY sno" +
                Constants.PERMIT_DETAILS_QUESTION_ORDER +
                ";", null);


        Log.d("tab2 db helper ==", "permit_id 2");

        cr.moveToFirst();

        while(!cr.isAfterLast())
        {

            permitDetails1 = new PermitDetails();

            permitDetails1.question = cr.getString(cr.getColumnIndexOrThrow("question"));
            permitDetails1.extra_text = cr.getString(cr.getColumnIndexOrThrow("extra_text"));
            permitDetails1.status = cr.getString(cr.getColumnIndexOrThrow("status"));
            permitDetails1.id = cr.getInt(cr.getColumnIndexOrThrow("_id"));
            permitDetails1.sno = cr.getInt(cr.getColumnIndexOrThrow("sno"));


            Log.d("in ret from data ==", "" + permitDetails1.status);


            permitTemplateDetails2.add(permitDetails1);

            cr.moveToNext();


        }
        Log.d("tab2 db helper==", "permit_id 3");


        cr.close();




        return permitTemplateDetails2;
    }



    public List<PermitDetails>getPermitTemplateDetailsListLocalWherePTId(long pt_id){
        List<PermitDetails> permitTemplateDetails2 = new ArrayList<PermitDetails>();

        Log.d("tab2 db helper ==", "permit_id 1");

        PermitDetails permitDetails1;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("select * " +
                "from " +
                "permit_details " +
                "WHERE " +
                " permit_id_local = "+pt_id +" " +
                "ORDER BY sno" +
                Constants.PERMIT_DETAILS_QUESTION_ORDER +
                ";", null);


        Log.d("tab2 db helper ==", "permit_id 2");

        cr.moveToFirst();

        while(!cr.isAfterLast())
        {

            permitDetails1 = new PermitDetails();

            permitDetails1.question = cr.getString(cr.getColumnIndexOrThrow("question"));
            permitDetails1.extra_text = cr.getString(cr.getColumnIndexOrThrow("extra_text"));
            permitDetails1.status = cr.getString(cr.getColumnIndexOrThrow("status"));
            permitDetails1.id = cr.getInt(cr.getColumnIndexOrThrow("_id"));
            permitDetails1.sno = cr.getInt(cr.getColumnIndexOrThrow("sno"));
            permitDetails1.permit_id = cr.getInt(cr.getColumnIndexOrThrow("permit_id"));


            Log.d("in ret from data ==", "" + permitDetails1.status);


            permitTemplateDetails2.add(permitDetails1);

            cr.moveToNext();


        }
        Log.d("tab2 db helper==", "permit_id 3");


        cr.close();




        return permitTemplateDetails2;
    }




    public boolean insertPermitTemplate(List<PermitTemplate> permitTemplates){

        SQLiteDatabase  db = this.getWritableDatabase();

        //retrieving json object list as the form PermitTemplate;



        Cursor cr = null;

        for(PermitTemplate permitTemplate : permitTemplates){

            Log.d("test==" , permitTemplate.name);

            cr = db.rawQuery("select " +
                    "name"+
                    " from "+"permit_templates"
                    +" where " +
                    " server_id ="+permitTemplate.id+"" +//here id used as server id do not confuse
                    " and "+
                     "name = '" + permitTemplate.name +"';"
                    , null);




            if(cr.getCount() == 0)
            {
                Log.d("test==", permitTemplate.name);

                ContentValues contentValues = new ContentValues();
                contentValues.put("server_id", permitTemplate.id); //here id used as server id do not confuse
                contentValues.put("name", permitTemplate.name);

                db.insert("permit_templates", null, contentValues);

            }


        }

        cr.close();


        return true;
    }



    public boolean insertPermitTemplateDetails(List<PermitTemplateDetails> permitTemplatesDetails){

        SQLiteDatabase  db = this.getWritableDatabase();

        //retrieving json object list as the form PermitTemplate;



        Cursor cr = null;

        for(PermitTemplateDetails permitTemplateDetail : permitTemplatesDetails){

            Log.d("test==" , permitTemplateDetail.question);

            cr = db.rawQuery("select " +
                    "_id"
                    + " from "
                    + " permit_template_details "
                    + " where "
                    + " server_id ="+permitTemplateDetail.id+"" +//here id used as server id do not confuse
                    ";"
                    , null);




            if(cr.getCount() == 0)
            {
                Log.d("test==", permitTemplateDetail.question);

                ContentValues contentValues = new ContentValues();
                contentValues.put("server_id", permitTemplateDetail.id); //here id used as server id do not confuse
                contentValues.put("question", permitTemplateDetail.question);
                contentValues.put("extra_text", permitTemplateDetail.extra_text);
                contentValues.put("sno", permitTemplateDetail.sno);
                contentValues.put("permit_template_id", permitTemplateDetail.permit_template_id);

                db.insert("permit_template_details", null, contentValues);

            }


        }

        cr.close();


        return true;
    }

    public void insertPermitPermission(List<PermitPermission> permitPermissionList) {
        SQLiteDatabase  db = this.getWritableDatabase();


        Cursor cr = null;




        ContentValues contentValues;


        for (PermitPermission permitPermission : permitPermissionList){

            cr = db.rawQuery("select user_id " +
                    " from "
                    +" permit_permission "
                    +" where " +
                    " server_id = "+permitPermission.id+"" +
                    ";"
                    , null);

            if(cr.getCount() == 0) {
                contentValues = new ContentValues();
                contentValues.put("server_id", permitPermission.id); // as server id
                contentValues.put("user_id", permitPermission.user_id);
                contentValues.put("permit_id", permitPermission.permit_id);
                contentValues.put("status", permitPermission.status);


                db.insert("permit_permission", null, contentValues);
            }
        }

        if(cr!= null)
            cr.close();

    }


}
