package com.zhaoguan.mpluslibs;

import android.content.Context;

import com.zhaoguan.mpluslibs.entity.UserLab;

/**
 * Created by husong on 2017/6/27.
 */

public class MPlusClient {

    private static Context mContext;

    public static void initialize(Context context, String factoryCode, String clientKey){
        MPlusHttpConstants.FACTORY_CODE = factoryCode;
        MPlusHttpConstants.clientKey = clientKey;
        mContext = context.getApplicationContext();
        UserLab.get().setFactoryCode(factoryCode);
        System.loadLibrary("CRCLibrary");
        System.loadLibrary("sinvoice");
    }

    public static Context getContext() {
        return mContext;
    }
}
