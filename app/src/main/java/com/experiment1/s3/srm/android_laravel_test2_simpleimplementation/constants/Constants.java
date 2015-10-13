package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants;

/**
 * Created by Mhr on 9/18/2015.
 */
public class Constants {



    public static String PREF_FILE_NAME = "com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.SHARED_PREF_FILE";

//    public static String BASE_URL = "http://192.168.1.100/php/laravel/test/android_laravel_v1/laravel/public"; //domain name
    public static String BASE_URL = "http://192.168.1.102/php/laravel/___Office/mobileservice/public"; //domain name




    //temp credential
    public static String username = "user1@gamil.com";
    public static String password = "pass@word1";
    public static String CLIENT_ID = "2";
    public static String CLIENT_SECRET = "tt";
    public static String redirectUrl = "http://192.168.1.100/php/laravel/test/android_laravel_v1/laravel/public/";
    public static String token_type = "";
    public static String grant_type = "password";


    public static String getTokenUrl  = "oauth/access_token";
    public static String loginUrl = "mobile/login";
    public static long CALL_LOGIN_HELPER_DELAY = 6000;

    public static String PROJECT_URL = "";
    public static String PREF_KEY_USERNAME = "USER_NAME";
    public static String PREF_KEY_PASSWORD= "PASSWORD";
    public static String PREF_KEY_ACCESS_TOKEN = "ACCESS_TOKEN";
    public static String PREF_KEY_ACCESS_TOKEN_TYPE = "ACCESS_TOKEN_TYPE";

    public static String DATABASE_NAME = "PW_DB.db";
    public static long SERVICE_WEAK_INTERVAL = (30*60*1000); //10 min


    public int clientId = 2;
    public String clientSecret = "tt";





    public static String access_token = "";

            String test = "/oauth/access_token?" +
            "grant_type=password&" +
            "CLIENT_ID=2&" +
            "CLIENT_SECRET=tt&" +
            "username=user1@gmail.com&" +
            "password=pass@word1&" +
            "redirect_uri=localhost:8000/home";






    public static String PREF_KEY_CLIENT_ID = "CLIENT_ID";
    public static String PREF_KEY_CLIENT_SECRET = "CLIENT_SECRET";
    public static String PREF_KEY_BASE_URL = "BASE_URL";
    public static String PREF_KEY_LOGIN_URL = "LOGIN_URL";
    public static String PREF_KEY_PROJECT_RETRIVAL_URL = "PROJECT_RETRIVAL_URL";



    //database section

    public static String PERMIT_TABLE_NAME = "permit";
    public static String PERMIT_DETAILS_TABLE_NAME = "permit_details";
    public static String PERMIT_TEMPLATE_TABLE_NAME = "permit_templates";
    public static String PERMIT_TEMPLATE_DETAILS_TABLE_NAME = "permit_template_details";


}
