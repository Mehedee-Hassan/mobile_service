package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.api.CustomAPI;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.SaveDataHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PTW;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.data.PermitForDataRet;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mhr on 10/1/2015.
 */
public class PermitDBHelper extends DatabaseHelper {

    String TAG = this.getClass().getSimpleName();

    List<PermitTemplate> permitTemplateItems;
    private List<PTW> listOfDetailsPtw;
    List<String> ptwTypeNames;
    Context context;

    List<Permit> savedPermtiList;


    public PermitDBHelper(Context context) {

        super(context);
        this.context = context;


        permitTemplateItems = new ArrayList<PermitTemplate>();
    }




    public List<String> getPtwTypeList(){

//      get ptw to global list
        Log.d("==" + PermitDBHelper.class.toString(), "getlist start");
        getPtwTypeObjects();


        Log.d("==" + PermitDBHelper.class.toString(), "getlist end");

        ptwTypeNames = new ArrayList<>();

        ptwTypeNames.clear();

        for(PermitTemplate permitTemplate : permitTemplateItems){

            ptwTypeNames.add(permitTemplate.name);
        }

        Log.d("==" + PermitDBHelper.class.toString(), "getlist end");

        return ptwTypeNames;
    }


    public void getPtwTypeObjects (){
        Log.d("==PTWTypeDatabaseHe+" , "getObject start");

        SQLiteDatabase db = this.getWritableDatabase();




        Cursor cr = db.rawQuery("SELECT" +
                " * FROM " +
                " permit_templates" +
                ";" , null);

        Log.d("==PTWTypeTempla" +
                "DBHelpr" , "getObject query");


        cr.moveToFirst();

        Log.d("==PTWTypeTemplateDBHelp", "getObject cursor move to first");


//        if(cr.getCount() != 0)
        permitTemplateItems.clear();
        while(!cr.isAfterLast()){



            PermitTemplate permitTemplate = new PermitTemplate();
            permitTemplate.name = cr.getString(cr.getColumnIndexOrThrow("name"));
            permitTemplate.id = cr.getInt(cr.getColumnIndexOrThrow("_id"));
            permitTemplate.company_id = cr.getInt(cr.getColumnIndexOrThrow("company_id"));


            Log.d("==PTWTypeTemplateDBHel" , ""+ permitTemplate.name);


            cr.moveToNext();
            permitTemplateItems.add(permitTemplate);

        }
        Log.d("==" + PermitDBHelper.class.toString(), "getObject end");

        cr.close();


    }

    public void saveToPermitTabel(Permit permit) {



    }


    public void getDetailsPermitToWorkTypeAt(int i) {

        PermitTemplate permitTemplate = permitTemplateItems.get(i);

        return ;

    }

    public List<PTW> getListOfDetailsPtw(int position) {



        PermitTemplate permitTemplate = permitTemplateItems.get(position);

        Log.d("==getListOfDetailsPtw()" , ""+ permitTemplate.name);

        int ptwTypeId = permitTemplate.id;

        PTWDatabaseHelper ptwDatabaseHelper = new PTWDatabaseHelper(context);
        List<PTW> ptwlist = ptwDatabaseHelper.getListOfSamePTWType(ptwTypeId);


        return ptwlist;
    }



    public PermitTemplate getPTWTypeAt(int pos) {

        return permitTemplateItems.get(pos);

    }




    public List<Permit>  getListOfPermitSaved(Project project){


        savedPermtiList = new ArrayList<>();


        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("select * from " +
                "permit " +
//                "inner join " +
//                "permit_details " +
//                "on permit._id = permit_details.permit_id " +
                "WHERE " +
//                "permit_details.status = 'draft'" +
//                " and " +
//                "permit." +
                "project_id = " +project.id +
                ";"
                , null);





        int count = cr.getCount();





        Permit permit;
        PermitDetails permitDetails;
        cr.moveToFirst();
        while (!cr.isAfterLast()){

            permit = new Permit();
            permitDetails = new PermitDetails();

            permit.id = cr.getInt(cr.getColumnIndexOrThrow("_id"));
            permit.project_id = cr.getInt(cr.getColumnIndexOrThrow("project_id"));
            permit.project_name = cr.getString(cr.getColumnIndexOrThrow("project_name"));
            permit.permit_name = cr.getString(cr.getColumnIndexOrThrow("permit_name"));
            permit.auto_gen_permit_no = cr.getString(cr.getColumnIndexOrThrow("permit_no"));
            permit.permit_template_id = cr.getInt(cr.getColumnIndexOrThrow("permit_template_id"));
            permit.contractor = cr.getString(cr.getColumnIndexOrThrow("contractor"));
            permit.location = cr.getString(cr.getColumnIndexOrThrow("location"));
            permit.work_activity = cr.getString(cr.getColumnIndexOrThrow("work_activity"));
            permit.permit_date = cr.getString(cr.getColumnIndexOrThrow("permit_date"));
            permit.start_time = cr.getString(cr.getColumnIndexOrThrow("start_time"));
            permit.end_time = cr.getString(cr.getColumnIndexOrThrow("end_time"));
            permit.created_by = cr.getInt(cr.getColumnIndexOrThrow("created_by"));
            permit.server_permit_id = cr.getInt(cr.getColumnIndexOrThrow("permit_id"));




//            PermitCombineClass permitCombineClass = new PermitCombineClass();


            savedPermtiList.add(permit);

            cr.moveToNext();
        }

        cr.close();
        return savedPermtiList;
    }


    public Permit getPermitDraftAt(int position) {


        Permit permit = savedPermtiList.get(position);

        return permit;

    }

    public long saveNewHalfDonePermit(Permit permit) {

        //currently in object
//        permit.permit_name = permitTemplate.name;
//        permit.permit_template_id = permitTemplate.id;


        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();


        contentValues.put("permit_name",permit.permit_name);
        contentValues.put("permit_template_id",permit.permit_template_id);
        contentValues.put("permit_no" , permit.auto_gen_permit_no);
        contentValues.put("project_name" , permit.project_name);
        contentValues.put("project_id" , permit.project_id);

       long id = db.insert("permit" ,null ,contentValues);
        Log.d("==" ,"insert "+id);






        return id;
    }

    public void saveToPermitDetailsTable(List<PermitDetails> permitDetailsList) {
        ContentValues contentValues;
        SQLiteDatabase db = this.getWritableDatabase();
//


        Cursor cr = null;


        for (PermitDetails permitDetails : permitDetailsList) {


            cr = db.rawQuery("select" +
                    " _id " +
                    "from " +
                    "permit_details " +
                    " WHERE " +
                    " server_id = " +permitDetails.id +
                    ";", null);


            Log.d(TAG + " server_id +++++++ = " ,""+permitDetails.permit_id);

            if(cr.getCount() == 0) {

                contentValues = new ContentValues();


//            contentValues.put("updated_at",permit.updated_at);
                contentValues.put("server_id", permitDetails.id);
                contentValues.put("permit_id", permitDetails.permit_id);
                contentValues.put("sno", permitDetails.sno);
                contentValues.put("question", permitDetails.question);
                contentValues.put("allowed_text", permitDetails.allowed_text);
                contentValues.put("extra_text", permitDetails.extra_text);
                contentValues.put("status", permitDetails.status);


                db.insert("permit_details", null, contentValues);
            }
            else{

                // if we need to update
                //updatePermitFromServer(permits,contentValues,db);

            }

            if(cr != null)
                cr.close();

        }

    }


    public void saveToPermitTable(List<PermitForDataRet> permitForDataRetList) {

        ContentValues contentValues;
        SQLiteDatabase db = this.getWritableDatabase();
//


        Cursor cr = null;


        for (PermitForDataRet permitForDataRet : permitForDataRetList) {


            cr = db.rawQuery("select" +
                    " _id " +
                    "from " +
                    "permit " +
                    " WHERE " +
                    " permit_id = " +permitForDataRet.id +
                    ";", null);


            if(cr.getCount() == 0) {

                contentValues = new ContentValues();


//            contentValues.put("updated_at",permit.updated_at);
                contentValues.put("permit_id", permitForDataRet.id);
                contentValues.put("permit_no", permitForDataRet.permit_no);
                contentValues.put("permit_name", permitForDataRet.permit_name);
                contentValues.put("permit_template_id", permitForDataRet.permit_template_id);
                contentValues.put("project_id", permitForDataRet.project_id);
                contentValues.put("project_name", permitForDataRet.project_name);
                contentValues.put("status", permitForDataRet.status);
                contentValues.put("permit_date", permitForDataRet.permit_date);
//            contentValues.put("created_at",permit.created_at);
                contentValues.put("created_by", permitForDataRet.created_by);
                contentValues.put("location", permitForDataRet.location);
                contentValues.put("contractor", permitForDataRet.contractor);
                contentValues.put("end_time", permitForDataRet.end_time);
                contentValues.put("start_time", permitForDataRet.start_time);
                contentValues.put("work_activity", permitForDataRet.work_activity);


                db.insert("permit", null, contentValues);
            }
            else{

                // if we need to update
                //updatePermitFromServer(permits,contentValues,db);

            }

            if(cr != null)
                cr.close();

        }


    }


}
