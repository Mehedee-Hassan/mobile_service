package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.Constants;

/**
 * Created by Mhr on 9/22/2015.
 */
public class SaveDataHelper {

//    public static int CLIENT_ID = 2;
//    public static String CLIENT_SECRET = "tt";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String[] accessToken;


    public SaveDataHelper(){

    }


    public SaveDataHelper(Context context){


         preferences = context.getSharedPreferences(Constants.PREF_FILE_NAME
                ,Context.MODE_PRIVATE );

         editor = preferences.edit();

    }


    public boolean saveSettings(String clientId
            ,String clientSecret
            ,String baseUrl
            ,String loginUrl
            ,String projectRetrivalUrl ) {




        editor.putString(Constants.PREF_KEY_CLIENT_ID , clientId);
        editor.putString(Constants.PREF_KEY_CLIENT_SECRET , clientSecret);
        editor.putString(Constants.PREF_KEY_BASE_URL , baseUrl);
        editor.putString(Constants.PREF_KEY_LOGIN_URL , loginUrl);
        editor.putString(Constants.PREF_KEY_PROJECT_RETRIVAL_URL , projectRetrivalUrl);


        boolean tt =editor.commit();

        return tt;
    }


    public void updateSettings(String clientId
            ,String clientSecret
            ,String baseUrl
            ,String loginUrl
            ,String projectRetrivalUrl ) {




        editor.putString(Constants.PREF_KEY_CLIENT_ID , clientId);
        editor.putString(Constants.PREF_KEY_CLIENT_SECRET , clientSecret);
        editor.putString(Constants.PREF_KEY_BASE_URL , baseUrl);
        editor.putString(Constants.PREF_KEY_LOGIN_URL, loginUrl);
        editor.putString(Constants.PREF_KEY_PROJECT_RETRIVAL_URL, projectRetrivalUrl);


        editor.commit();

        Log.d("==now ==" ,preferences.getString(Constants.PREF_KEY_LOGIN_URL ,""));

        //return tt;
    }


    public String getPrefClientId(){

        String value = preferences.getString(Constants.PREF_KEY_CLIENT_ID, Constants.CLIENT_ID);
        return value;

    }

    public String getPrefClientSecret(){

        String value = preferences.getString(Constants.PREF_KEY_CLIENT_SECRET,Constants.CLIENT_SECRET);
        return value;

    }



    public String getPrefLoginUrl(){

        String value = preferences.getString(Constants.PREF_KEY_LOGIN_URL,Constants.loginUrl);
        return value;

    }



    public String getPrefProjectRetrivalUrl(){

        String value = preferences.getString(Constants.PREF_KEY_PROJECT_RETRIVAL_URL,Constants.PROJECT_URL);
        return value;

    }

    public String getPrefBaseUrl() {
        String value = preferences.getString(Constants.PREF_KEY_BASE_URL,Constants.BASE_URL);

        return value;
    }




    public void commitSave() {
        editor.commit();
    }

    public void clearSave(){
        editor.clear();
    }



    public void removeAllSettings(){
        editor.remove(Constants.PREF_KEY_CLIENT_ID);
        editor.remove(Constants.PREF_KEY_CLIENT_SECRET);
        editor.remove(Constants.PREF_KEY_BASE_URL);
        editor.remove(Constants.PREF_KEY_LOGIN_URL);
        editor.remove(Constants.PREF_KEY_PROJECT_RETRIVAL_URL);
        editor.commit();
    }


    public String[] toList() {


        String[] pref = new String[]
                {
                        getPrefClientId() ,
                        getPrefClientSecret(),
                        getPrefBaseUrl(),
                        getPrefLoginUrl(),
                        getPrefProjectRetrivalUrl()
                };

        return pref;
    }

    public void savePrefUsername(String username) {
        editor.putString(Constants.PREF_KEY_USERNAME ,username);
        editor.commit();
    }

    public void savePrefPassword(String password) {
        editor.putString(Constants.PREF_KEY_PASSWORD ,password);
        editor.commit();
    }


    public String getPrefPassword() {
        return preferences.getString(Constants.PREF_KEY_PASSWORD ,"");
    }
    public String getPrefUsername() {

        return preferences.getString(Constants.PREF_KEY_USERNAME, "");
    }

    public void deleteCredential() {
//        editor.remove(Constants.PREF_KEY_USERNAME);
        editor.remove(Constants.PREF_KEY_PASSWORD);
        editor.commit();
    }

    public String[] getAccessTokenDetails() {


        String at = preferences.getString(Constants.PREF_KEY_ACCESS_TOKEN ,"");
        String tt = preferences.getString(Constants.PREF_KEY_ACCESS_TOKEN_TYPE,"");

        String[] returnVal = new String[]{at ,tt};


        return returnVal;
    }

    public void saveToken(String access_token, String token_type) {
        editor.putString(Constants.PREF_KEY_ACCESS_TOKEN    , access_token);
        editor.putString(Constants.PREF_KEY_ACCESS_TOKEN_TYPE    , token_type);

        editor.commit();
    }


    public void removeToken(String access_token, String token_type) {
        editor.remove(Constants.PREF_KEY_ACCESS_TOKEN);
        editor.remove(Constants.PREF_KEY_ACCESS_TOKEN_TYPE);

        editor.commit();
    }
}
