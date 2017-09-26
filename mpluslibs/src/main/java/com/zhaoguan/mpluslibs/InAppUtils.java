package com.zhaoguan.mpluslibs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by song on 2016/5/17.
 */
public class InAppUtils {

    private static final String LOGIN = "login";

    private static final String PATIENT_ID = "idPatient";
    private static final String FACTORY_CODE = "factoryCode";

    private static InAppUtils mInAppUtils;

    public static InAppUtils get(){
        if(mInAppUtils == null){
            synchronized (InAppUtils.class){
                if(mInAppUtils == null){
                    mInAppUtils = new InAppUtils();
                }
            }
        }
        return mInAppUtils;
    }


    public String getPatientId(){
        SharedPreferences sp = MPlusClient.getContext().getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        return sp.getString(PATIENT_ID, null);
    }

    public void setLogin(String patientId){
        SharedPreferences sp = MPlusClient.getContext().getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PATIENT_ID, patientId);
        editor.apply();
    }

    public String getFactoryCode(){
        SharedPreferences sp = MPlusClient.getContext().getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        return sp.getString(FACTORY_CODE, null);
    }

    public void setFactoryCode(String factoryCode){
        SharedPreferences sp = MPlusClient.getContext().getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(FACTORY_CODE, factoryCode);
        editor.apply();
    }
}
