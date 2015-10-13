package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view;

/**
 * Created by Mhr on 10/3/2015.
 */
public class CheckList {

    public int YesOptions;
    public int id;
    public String question;


    public CheckList(){
        YesOptions = 0;
    }


    public CheckList(int id , String q){
        YesOptions = 0;
        this.id = id;
        this.question = q;

    }


    public void setYesOptions(int option){

//        1 = yes
//        2 = no
//        3 = na

        this.YesOptions = option;

    }

}
