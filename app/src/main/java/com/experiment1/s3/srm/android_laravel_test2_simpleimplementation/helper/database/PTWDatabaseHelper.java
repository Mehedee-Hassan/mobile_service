package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PTW;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 10/2/2015.
 */
public class PTWDatabaseHelper extends DatabaseHelper{
    public PTWDatabaseHelper(Context context) {
        super(context);
    }




    public List<PTW> getListOfSamePTWType(int ptwTypeId) {

//
//        db.execSQL("CREATE TABLE IF NOT EXISTS"
//                +" PTW" +
//                "(permit_id integer primary key autoincrement"
//                +", workdescription text"
//                +", location text"
//                +", contractor text"
//                +", start_date_time datetime"
//                +", status varchar(10)"
//                +", approved_date_time datetime"
//                +", approved_by integer"
//                +", validate_date_time datetime"
//                + ", validate_by integer"
//                +", applied_date_time datetime"
//                +", applied_by integer"
//                +", end_date_time datetime"
//                +", project_id integer"
//                +", ptw_type_id integer"
//                +", worker_id integer"
//                +", answer_id integer"
//                +", FOREIGN KEY(project_id) REFERENCES Projects(permit_id)"
//                +", FOREIGN KEY(ptw_type_id) REFERENCES PTW_TYPE_TABLE(permit_id)"
//                +", FOREIGN KEY(worker_id) REFERENCES WORKER_TABLE(permit_id)"
//                +", FOREIGN KEY(answer_id) REFERENCES ANSWER_TABLE(permit_id)"
//                +");");

        PTW ptw;

        List<PTW> ptws = new ArrayList<PTW>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("select "+"*"+" from PTW " +
                "WHERE ptw_type_id="+ptwTypeId , null);

        Log.d("==getListSamePTWType()", "in method" );

        cr.moveToFirst();


        if(cr.getCount() != 0)
        while(!cr.isAfterLast())
        {

            ptw = new PTW();

            ptw.location = cr.getString(cr.getColumnIndexOrThrow("location"));
            ptw.id = cr.getInt(cr.getColumnIndexOrThrow("permit_id"));
            ptw.appliedDate = cr.getString(cr.getColumnIndexOrThrow("applied_date_time"));
            ptw.workDescription = cr.getString(cr.getColumnIndexOrThrow("workdescription"));
            ptw.startTime = cr.getString(cr.getColumnIndexOrThrow("start_date_time"));
            ptw.endTime = cr.getString(cr.getColumnIndexOrThrow("end_date_time"));
            ptw.contractor = cr.getString(cr.getColumnIndexOrThrow("contractor"));
            ptw.status = cr.getString(cr.getColumnIndexOrThrow("status"));
            ptw.ptwType = ptwTypeId;

            Log.d("==getListSamePTWType()", ""+ptw.location );


            ptws.add(ptw);
            cr.moveToNext();


        }

        cr.close();




        return ptws;

    }
}
