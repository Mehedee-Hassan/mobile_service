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


    public CheckList(int id , String q ,String status){
        yesOptions = 0;
        this.id = id;
        this.question = q;


        if(status.contentEquals("null")){
            yesOptions = 0;
        }
        else if(status.contentEquals("ok")){
            yesOptions = 1;

        }

        else if(status.contentEquals("nok")){
            yesOptions = 2;

        }

        else if(status.contentEquals("na")){
            yesOptions = 3;

        }

    }


    public void setYesOptions(int option){

//        1 = yes
//        2 = no
//        3 = na

        this.yesOptions = option;

    }

}
