package com.zhaoguan.mpluslibs.entity;

import com.zhaoguan.mpluslibs.InAppUtils;


public class UserLab {

    private static UserLab mUserLab;

    public static UserLab get(){
        if(null == mUserLab){
            synchronized (UserLab.class){
                if(null == mUserLab)
                    mUserLab = new UserLab();
            }
        }
        return mUserLab;
    }

    public void setLogin(String patientId){
        InAppUtils.get().setLogin(patientId);
    }


    public String getPatientId(){
        return InAppUtils.get().getPatientId();
    }

    public String getFactoryCode(){
        return InAppUtils.get().getFactoryCode();
    }

    public void setFactoryCode(String patientId){
        InAppUtils.get().setFactoryCode(patientId);
    }

}
