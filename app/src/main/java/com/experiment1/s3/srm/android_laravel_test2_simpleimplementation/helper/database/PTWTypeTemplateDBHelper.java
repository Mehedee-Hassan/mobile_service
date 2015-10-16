package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PTW;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitTemplate;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 10/1/2015.
 */
public class PTWTypeTemplateDBHelper extends DatabaseHelper {

    List<PermitTemplate> permitTemplateItems;
    private List<PTW> listOfDetailsPtw;
    List<String> ptwTypeNames;
    SQLiteDatabase db ;//= this.getWritableDatabase();
    Context context;

    public PTWTypeTemplateDBHelper(Context context) {

        super(context);
        this.context = context;


        this.db = this.getWritableDatabase();
        permitTemplateItems = new ArrayList<PermitTemplate>();
    }




    public List<String> getPtwTypeList(){

//      get ptw to global list
        Log.d("==" + PTWTypeTemplateDBHelper.class.toString(), "getlist start");
        getPtwTypeObjects();


        Log.d("==" + PTWTypeTemplateDBHelper.class.toString(), "getlist end");

        ptwTypeNames = new ArrayList<>();

        ptwTypeNames.clear();

        for(PermitTemplate permitTemplate : permitTemplateItems){

            ptwTypeNames.add(permitTemplate.name);
        }

        Log.d("==" + PTWTypeTemplateDBHelper.class.toString(), "getlist end");

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
            permitTemplate.id = cr.getInt(cr.getColumnIndexOrThrow("id"));
            permitTemplate.company_id = cr.getInt(cr.getColumnIndexOrThrow("company_id"));


            Log.d("==PTWTypeTemplateDBHel" , ""+ permitTemplate.name);


            cr.moveToNext();
            permitTemplateItems.add(permitTemplate);

        }
        Log.d("==" + PTWTypeTemplateDBHelper.class.toString(), "getObject end");

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
}
