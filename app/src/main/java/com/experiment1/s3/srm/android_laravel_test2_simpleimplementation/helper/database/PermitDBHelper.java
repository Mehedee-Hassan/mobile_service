package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PTW;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetail;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.helper.query.PermitCombineClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 10/1/2015.
 */
public class PermitDBHelper extends DatabaseHelper {

    List<PermitTemplate> permitTemplateItems;
    private List<PTW> listOfDetailsPtw;
    List<String> ptwTypeNames;
    SQLiteDatabase db ;//= this.getWritableDatabase();
    Context context;

    List<Permit> savedPermtiList;


    public PermitDBHelper(Context context) {

        super(context);
        this.context = context;


        this.db = this.getWritableDatabase();
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





        Cursor cr = db.rawQuery("SELECT" +
                " * FROM " +
                "permit_templates" +
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




    public List<Permit>  getListOfPermitSaved(){


        savedPermtiList = new ArrayList<>();


        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("select * from " +
                "permit " +
                "inner join " +
                "permit_details " +
                "on permit._id = permit_details.permit_id " +
                "where " +
                "permit_details.status = 'draft';  "
                , null);





        Permit permit;
        PermitDetail permitDetail;
        cr.moveToFirst();
        while (!cr.isAfterLast()){

            permit = new Permit();
            permitDetail = new PermitDetail();

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





//            PermitCombineClass permitCombineClass = new PermitCombineClass();


            savedPermtiList.add(permit);

            cr.moveToNext();
        }


        return savedPermtiList;
    }


    public String getPermitDraftAt(int position) {


        Permit permit = savedPermtiList.get(position);

        return permit.auto_gen_permit_no;

    }
}
