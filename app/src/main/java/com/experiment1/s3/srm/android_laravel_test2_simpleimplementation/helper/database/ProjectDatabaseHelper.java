package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
                    PROJECT_TABLE_COL_PROJECT_ID+"="+project.id
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
}
