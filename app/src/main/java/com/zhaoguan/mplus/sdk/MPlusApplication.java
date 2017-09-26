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

        MPlusClient.initialize(this, "15c0e3f80112530f860ded97937de2d2", "");
    }

    public static Context getContext() {
        return mContext;
    }
}
