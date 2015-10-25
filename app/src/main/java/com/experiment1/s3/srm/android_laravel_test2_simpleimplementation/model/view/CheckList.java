package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view;

/**
 * Created by Mhr on 10/3/2015.
 */
public class CheckList {

    public int yesOptions;
    public int _id;
    public long permit_id;
    public String question;
    public int checkListTemplateDetailsId;
    public int extra_added_sno;

    public CheckList(){
        yesOptions = 0;
    }


    public CheckList(int id ,long permit_id, String q ,String status ,int extra_added_sno){
        yesOptions = 0;

        this._id = id;
        this.permit_id = permit_id;
        this.question = q;
        this.extra_added_sno = extra_added_sno;

        if(status.contentEquals("NULL") || status.contentEquals("null")){
            yesOptions = 0;
        }

        if(status.contentEquals("OK") || status.contentEquals("ok")){
            yesOptions = 1;

        }

        if(status.contentEquals("NOK") || status.contentEquals("nok") ){
            yesOptions = 2;

        }

        if(status.contentEquals("NA") || status.contentEquals("na")){
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
