package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitPermission;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 9/29/2015.
 */
public class ProjectDatabaseHelper extends DatabaseHelper {

    public String PROJECT_TABLE_NAME= "Projects";
    public String PROJECT_TABLE_COL_PROJECT_ID = "project_id";
    public String PROJECT_TABLE_COL_PROJECT_NAME = "project_name";
    public List<Project> projects;
    private List<PermitPermission> newPermitPermissions;

    public ProjectDatabaseHelper(Context context) {
        super(context);
    }


    public void saveProjects(List<Project> projects) {
        for(Project project : projects){
            Log.d("projects == ", "" + project.name);
        }


    }






    public boolean insertProjects(List<Project> projects){

        SQLiteDatabase  db = this.getWritableDatabase();

        Cursor cr = null;

        for(Project project : projects){

            Log.d("test==" , project.name);

            cr = db.rawQuery("select " +
                    PROJECT_TABLE_COL_PROJECT_NAME+
                    " from "+PROJECT_TABLE_NAME
                    +" where " +
                    PROJECT_TABLE_COL_PROJECT_ID+"="+project.id+"" +
//                    " and "+
//                    PROJECT_TABLE_COL_PROJECT_NAME+ " = '" + project.name +
//                    "'" +
                    ";"
                    , null);




            if(cr.getCount() == 0)
            {
                Log.d("test==", project.name);

                ContentValues contentValues = new ContentValues();
                contentValues.put(PROJECT_TABLE_COL_PROJECT_ID, project.id);
                contentValues.put(PROJECT_TABLE_COL_PROJECT_NAME, project.name);

                db.insert(PROJECT_TABLE_NAME, null, contentValues);

            }


        }

        if(cr != null)
        cr.close();


        return true;
    }



    public List<Project> getProjects(){


        Project project;
         projects = new ArrayList<Project>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cr = db.rawQuery("select "+"*"+" from "+PROJECT_TABLE_NAME+"" , null);

        cr.moveToFirst();

        while(!cr.isAfterLast())
        {

            project = new Project();

            project.name = cr.getString(cr.getColumnIndexOrThrow(PROJECT_TABLE_COL_PROJECT_NAME));
            project.id = cr.getInt(cr.getColumnIndexOrThrow(PROJECT_TABLE_COL_PROJECT_ID));


            Log.d("in ret from data =",""+project.name);

            projects.add(project);
            cr.moveToNext();


        }

        cr.close();

        return projects;
    }


    public Project getProjectAt(int pos) {
        return projects.get(pos);
    }






    public List<PermitPermission> getNewPermitPermissions() {


        SQLiteDatabase  db = this.getWritableDatabase();
        Cursor cr = null;

        newPermitPermissions = new ArrayList<PermitPermission>();

       cr = db.rawQuery("select * " +
                    " from "
                    +" permit_permission "
                    +" where " +
                    "notification_age" + " = '" + Constants.NOTIFICATION_AGE_NEW +"'"
               ,null);


        cr.moveToFirst();

        PermitPermission tempPermitPermission;
        while(!cr.isAfterLast()){

            tempPermitPermission = new PermitPermission();

            tempPermitPermission.id = cr.getInt(cr.getColumnIndexOrThrow("server_id"));
            tempPermitPermission.status = cr.getString(cr.getColumnIndexOrThrow("status"));
            tempPermitPermission.user_id = cr.getInt(cr.getColumnIndexOrThrow("user_id"));
            tempPermitPermission.permit_id = cr.getInt(cr.getColumnIndexOrThrow("permit_id"));
            //cr.getInt(cr.getColumnIndexOrThrow("notification_age"));


            //updateAgeOfNotification(db, cr);


            newPermitPermissions.add(tempPermitPermission);

            cr.moveToNext();
        }

        cr.close();

        return newPermitPermissions;
    }

    private void updateAgeOfNotification(SQLiteDatabase db, Cursor cr) {
        ContentValues contentValues;
        int _id = cr.getInt(cr.getColumnIndexOrThrow("_id"));
        String queryFor = " _id = ? ";
        String[] arguments = new String[] {""+_id};


        contentValues = new ContentValues();
        contentValues.put("notification_age" , Constants.NOTIFICATION_AGE_OLD);


        db.update("permit_permission",contentValues
                ,queryFor ,arguments
        );
    }
}
