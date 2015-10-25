package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Mhr on 10/3/2015.
 */
public class PermitDetails {

    @Expose
    public int id;
    @Expose
    public String question;
    @Expose
    public String extra_text;
    @Expose
    public String status;
    @Expose
    public long permit_id;
    @Expose
    public int allowed_text;
    @Expose
    public int sno;

    public int server_id;

    public PermitDetails(){

        server_id = 0;
    }

}
