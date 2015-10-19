package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplateDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;

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


    public List<PermitDetails>getPermitTemplateDetailsListWherePTId(long pt_id){
        List<PermitDetails> permitTemplateDetails2 = new ArrayList<PermitDetails>();

        Log.d("tab2 db helper ==", "permit_id 1");

        PermitDetails permitTemplateDetails1;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("select * " +
                "from " +
                "permit_details " +
                "WHERE permit_id= "+pt_id , null);


        Log.d("tab2 db helper ==", "permit_id 2");

        cr.moveToFirst();

        while(!cr.isAfterLast())
        {

            permitTemplateDetails1 = new PermitDetails();

            permitTemplateDetails1.question = cr.getString(cr.getColumnIndexOrThrow("question"));
            permitTemplateDetails1.extra_text = cr.getString(cr.getColumnIndexOrThrow("extra_text"));
            permitTemplateDetails1.status = cr.getString(cr.getColumnIndexOrThrow("status"));
            permitTemplateDetails1.id = cr.getInt(cr.getColumnIndexOrThrow("_id"));
//            permitTemplateDetails1.sno = cr.getInt(cr.getColumnIndexOrThrow("sno"));


            Log.d("in ret from data ==", "" + permitTemplateDetails1.status);


            permitTemplateDetails2.add(permitTemplateDetails1);

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
}
