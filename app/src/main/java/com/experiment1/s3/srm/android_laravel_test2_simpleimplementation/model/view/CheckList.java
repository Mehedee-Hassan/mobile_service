package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view;

/**
 * Created by Mhr on 10/3/2015.
 */
public class CheckList {

    public int yesOptions;
    public int id;
    public String question;
    public int checkListTemplateDetailsId;


    public CheckList(){
        yesOptions = 0;
    }


    public CheckList(int id , String q){
        yesOptions = 0;
        this.id = id;
        this.question = q;

    }


    public void setYesOptions(int option){

//        1 = yes
//        2 = no
//        3 = na

        this.yesOptions = option;

    }

}
