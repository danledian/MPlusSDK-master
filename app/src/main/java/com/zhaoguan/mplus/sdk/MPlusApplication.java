package com.zhaoguan.mplus.sdk;

import android.app.Application;
import android.content.Context;

import com.zhaoguan.mpluslibs.MPlusClient;

/**
 * Created by husong on 2017/6/28.
 */

public class MPlusApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        MPlusClient.initialize(this, "dbef6584097b4e6290538d8260a391df", "");
    }

    public static Context getContext() {
        return mContext;
    }
}
