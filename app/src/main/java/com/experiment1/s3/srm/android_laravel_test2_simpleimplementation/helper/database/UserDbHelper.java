package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhr on 10/8/2015.
 */
public class UserDbHelper extends DatabaseHelper {

  User user ;
  List<User> users;


    public UserDbHelper(Context context) {
        super(context);

    }



    public List<User> selectAllUser(){


        SQLiteDatabase db = this.getWritableDatabase();


        users = new ArrayList<User>();

        Cursor cr = db.rawQuery("SELECT permit_id ,name ,email " +
                "FROM USER_TABLE" ,null);

        cr.moveToFirst();

        while(!cr.isAfterLast())
        {

            user = new User();

            user.name = cr.getString(cr.getColumnIndexOrThrow("name"));
            user.email = cr.getString(cr.getColumnIndexOrThrow("email"));
            user.id = cr.getInt(cr.getColumnIndexOrThrow("permit_id"));


            Log.d("in ret from data =", "" + user.name);

            users.add(user);
            cr.moveToNext();


        }

        cr.close();



        return users;
    }

}
