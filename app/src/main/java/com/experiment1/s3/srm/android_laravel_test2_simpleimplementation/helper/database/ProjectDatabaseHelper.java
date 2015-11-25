package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitPermission;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Project;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.ret.NotificationDataRetModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 9/29/2015.
 */
public class ProjectDatabaseHelper extends DatabaseHelper {


    public String TAG = this.getClass().getSimpleName();

    public String PROJECT_TABLE_NAME= "Projects";
    public String PROJECT_TABLE_COL_PROJECT_ID = "project_id";
    public String PROJECT_TABLE_COL_PROJECT_NAME = "project_name";
    public List<Project> projects;
    private List<NotificationDataRetModel> notificationDataRetModels;
    private String permitStatusToTake;


    final int PERMIT_STATUS_SUBMITTED_APPROVED_REJ_APP = 1;
    final int  PERMIT_STATUS_APPROVED_REJ_APP_REJ_VAL = 2;
    final int  PERMIT_STATUS_VALIDATED = 3;


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

        if(projects != null )
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






    public List<NotificationDataRetModel> getNotificationDataRetModels(int currentUserRole) {



        Log.d(TAG+" arg " ,"here");


        SQLiteDatabase  db = this.getWritableDatabase();
        Cursor cr = null;

        notificationDataRetModels = new ArrayList<NotificationDataRetModel>();

        String permitStatusArg = getMappedPermitStatus(getPermitStatusToTake(currentUserRole));

        Log.d(TAG+" arg = ", permitStatusArg);


       String query = "select * " +
                    " from "
                    +" permit_permission "
                    +" where " +
                    "notification_age" + " = '" + Constants.NOTIFICATION_AGE_NEW +"' " +
                    " AND  " +
                    permitStatusArg;

        Log.d(TAG+" arg = ", query);



        cr = db.rawQuery(query ,null);


        cr.moveToFirst();

        NotificationDataRetModel notificationDataRetModel;


        while(!cr.isAfterLast()){



            int permitPermissionServerId = cr.getInt(cr.getColumnIndexOrThrow("server_id"));
            String permitPermissionStatus = cr.getString(cr.getColumnIndexOrThrow("status"));
            int userId = cr.getInt(cr.getColumnIndexOrThrow("user_id"));
            int permitId = cr.getInt(cr.getColumnIndexOrThrow("permit_id"));
            //cr.getInt(cr.getColumnIndexOrThrow("notification_age"));





            String projectName = getProjectNameBy(permitId,db);
            notificationDataRetModel = new
                    NotificationDataRetModel(permitPermissionStatus
                    ,projectName
                    ,permitPermissionServerId);


            notificationDataRetModels.add(notificationDataRetModel);

            updateAgeOfNotification(db, cr);


            cr.moveToNext();
        }

        cr.close();

        return notificationDataRetModels;
    }

    private String getMappedPermitStatus(int permitStatusToTakeNumber) {



        String ARG_STRING = "";
        if(permitStatusToTakeNumber == PERMIT_STATUS_SUBMITTED_APPROVED_REJ_APP){

            ARG_STRING = "( status " + " = " +"'"+ Constants.PERMIT_STATUS_SUBMITTED +"'"
                    + " OR "
                    + " status " + " = " + "'" + Constants.PERMIT_STATUS_APPROVED +"'"
                    + " OR "
                    + " status " + " = " + "'" + Constants.PERMIT_STATUS_APPROVED_REJECT +"' " +
                    ")";

        }
        else if(permitStatusToTakeNumber == PERMIT_STATUS_APPROVED_REJ_APP_REJ_VAL){

            ARG_STRING = "( status " + " = " +"'"+ Constants.PERMIT_STATUS_APPROVED +"'"
                    + " OR "
                    + " status " + " = " + "'" + Constants.PERMIT_STATUS_APPROVED_REJECT +"'"
                    + " OR "
                    + " status " + " = " + "'" + Constants.PERMIT_STATUS_VALIDATE_REJECT +"' " +
                    ")";

        }

        else if(permitStatusToTakeNumber == PERMIT_STATUS_VALIDATED){
            ARG_STRING = " status " + " = " +"'"+ Constants.PERMIT_STATUS_VALIDATE_SUBMITTED +"'";
        }



        return ARG_STRING;
    }

    private String getProjectNameBy(long permit_id,SQLiteDatabase db) {

        String projectName="";

        Cursor cr1 = null;
        Cursor cr2 = null;



        cr1 = db.rawQuery("select " +
                " project_id " +
                " from "
                +" permit "
                +" where " +
                " permit_id "  + " = " + permit_id +""
                ,null);


        cr1.moveToFirst();
        int project_id = -2;
        if(!cr1.isAfterLast())
        project_id =cr1.getInt(cr1.getColumnIndexOrThrow("project_id"));

        Log.d(TAG+" project id = ",""+project_id);


        cr2 = db.rawQuery("select  " +
                " project_name " +
                " from " +
                " Projects "
                +" where " +
                " project_id "  + " = " + project_id +""
                ,null);




        cr2.moveToFirst();

        if(!cr2.isAfterLast())
        projectName += cr2.getString(cr2.getColumnIndexOrThrow("project_name"));


        Log.d(TAG + " project name = ", "" + projectName);


        if(cr1 != null)
            cr1.close();

        if(cr2 != null)
            cr1.close();


        return projectName;
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

    public int getPermitStatusToTake(int currentUserRole) {

        if (currentUserRole == 1) {

            return PERMIT_STATUS_VALIDATED;

        }
        else if (currentUserRole == 2){
            return PERMIT_STATUS_SUBMITTED_APPROVED_REJ_APP;
        }
        else
//        if(currentUserRole == 3)
        {
            return PERMIT_STATUS_APPROVED_REJ_APP_REJ_VAL;
        }


    }
}
