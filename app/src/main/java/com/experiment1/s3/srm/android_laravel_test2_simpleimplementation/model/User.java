package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model;

/**
 * Created by Mhr on 10/8/2015.
 */
public class User {

    public String name;
    public String email;
    public int id;


    public User(){}
    public User( String name ,int id ,String email){
        this.name = name;
        this.email = email;
        this.id = id;


    }


    public User( String name  ,String email){

        this.name = name;
        this.email = email;


    }


}
