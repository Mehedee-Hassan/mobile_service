package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.helper.query;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetail;

/**
 * Created by User-8.1 on 10/16/2015.
 */
public class PermitCombineClass {

    Permit permit;
    PermitDetail permitDetail;

    public PermitCombineClass(Permit p ,PermitDetail pd){
        this.permit = p;
        this.permitDetail = pd;
    }

}
