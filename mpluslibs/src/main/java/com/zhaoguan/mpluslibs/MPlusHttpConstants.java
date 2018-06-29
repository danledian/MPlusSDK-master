package com.zhaoguan.mpluslibs;

/**
 * Created by husong.
 */

public class MPlusHttpConstants {

    public static String FACTORY_CODE;
    public static String clientKey;

    // LeanCloud
    public static final String BASE_URL = "https://api.leancloud.cn/1.1/";

    public static final String APP_ID = "1UlsKsiUTHpNkAyAKSWVW1oo-gzGzoHsz";
    public static final String APP_KEY = "MeyXCB3GkeYmQkQFOacuTSMU";

    public static final String PREPARE_ENV = "0";//预备环境(增加了uuid检测)
    public static final String CUSTOM_ENV = "1";//生产环境

    public static final boolean PRODUCT = false;

    public static String getLc(){
        if(PRODUCT){
            return CUSTOM_ENV;
        }
        return PREPARE_ENV;
    }

    public static final String REGISTER_FOR_SDK= "functions/registerForSDK";
    public static final String LOGIN_FOR_SDK= "functions/loginForSDK";
    public static final String CREATE_DEVICE_WITH_UUID_FOR_SDK= "functions/CreateDeviceWithUUIDForSDK";
    public static final String GET_DEVICE_WITH_UUID_FOR_SDK= "functions/GetDeviceWithUUIDForSDK";
    public static final String GET_DEVICE_WITH_FACTORY_USERID_FOR_SDK= "functions/GetDeviceWithFactoryUserIDForSDK";
    public static final String BOUND_DEVICE_FOR_CLIENT_SDK= "functions/boundDeviceForClientSDK";
    public static final String GET_REPORTS_FOR_SDK_WITH_END_AND_BEGIN= "functions/getReportsForSDKWithEndAndBegin";
    public static final String GET_REPORTS_FOR_SDK_WITH_END_AND_CNT= "functions/getReportsForSDKWithEndAndCnt";
    public static final String GET_REPORTS_CNT_FOR_SDK_WITH_END_AND_BEGIN= "functions/getReportsCntForSDKWithEndAndBegin";
    public static final String DEVICE = "classes/Device";

    public static final String START_SLEEP = "/startSleep";
    public static final String STOP_SLEEP = "/stopSleep";

}
