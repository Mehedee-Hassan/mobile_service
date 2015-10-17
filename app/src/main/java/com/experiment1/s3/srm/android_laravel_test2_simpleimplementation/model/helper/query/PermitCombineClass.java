package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.helper.query;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.PermitDetails;

/**
 * Created by User-8.1 on 10/16/2015.
 */
public class PermitCombineClass {

    Permit permit;
    PermitDetails permitDetails;

    public PermitCombineClass(Permit p ,PermitDetails pd){
        this.permit = p;
        this.permitDetails = pd;
    }

}
