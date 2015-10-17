package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplateDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 10/5/2015.
 */
public class PermitTemplateDetailsDBHelper extends DatabaseHelper {
    public PermitTemplateDetailsDBHelper(Context context) {
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


}
