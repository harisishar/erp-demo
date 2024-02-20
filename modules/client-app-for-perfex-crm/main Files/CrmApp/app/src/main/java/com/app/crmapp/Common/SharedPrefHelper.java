package com.app.crmapp.Common;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.crmapp.Utils.Application;

/**
 * Created by LBMSOLUTIONS on 05-11-2019.
 */

public class SharedPrefHelper {
    public static final String USER_INFO_PREF = "com.app.crm_app_user_info";
    private SharedPreferences pref;
    private static SharedPrefHelper instance;

    /****** get single instance of this class ******/
    public static synchronized SharedPrefHelper getInstance(String prefName) {
        if (instance == null) {
            instance = new SharedPrefHelper(prefName);
        }
        return instance;
    }

    /******** initialize object ********/
    private SharedPrefHelper(String prefName) {
        instance = this;
        pref= Application.getInstance().getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }


    /***** add string data into sharedpreference ******/
    public void saveData(String is_login, String userid){
        SharedPreferences.Editor prefsEditor = pref.edit();
        prefsEditor.putString("IS_LOGIN","1");
        prefsEditor.putString("user_id",userid);
        prefsEditor.commit();
    }

    /******* return specified key value******/
    public String getIsLogin(){
        return pref.getString("IS_LOGIN",null);
    }

    /******* return specified key value******/
    public String getName(){
        return pref.getString("name",null);
    }

    /******* return specified key value******/
    public String getImage(){
        return pref.getString("image",null);
    }

    /******* return specified key value******/
    public String getCompanyName(){
        return pref.getString("company_name",null);
    }

    /******* return specified key value******/
    public String getUserCompName(){
        return pref.getString("comp_name",null);
    }

    /******* return specified key value******/
    public String getUserId(){
        return pref.getString("user_id",null);
    }

    /******* save data into sharedpreference ******/
    public void saveUserData(String name, String user_company_name, String image, String companyName) {
        SharedPreferences.Editor prefsEditor = pref.edit();
        prefsEditor.putString("name",name);
        prefsEditor.putString("comp_name",user_company_name);
        prefsEditor.putString("image",image);
        prefsEditor.putString("company_name",companyName);
        prefsEditor.commit();
    }

    /******* clear all preference data******/
    public void logoutUser() {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
