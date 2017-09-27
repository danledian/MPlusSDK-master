package com.zhaoguan.mpluslibs;

import com.lt.volley.http.Request;
import com.lt.volley.http.VolleyResponse;
import com.zhaoguan.mpluslibs.entity.MPlusObject;

/**
 * Created by husong on 2017/6/27.
 */

public interface IMPlusDevice {

    void voiceConfig(String wifiName, String wifiPwd, MPlusConfigCallback response);

    void voiceConfig(String wifiName, String wifiPwd, String deviceSn, MPlusConfigCallback response);

    void stopVoiceConfig();

    IMPlusObject getReportsWithEnd(String patientId, String end, int count, VolleyResponse.Listener listener);

    IMPlusObject getReportsWithEndAndBegin(String patientId, String begin, String end, VolleyResponse.Listener listener);

    IMPlusObject boundDeviceForClient(String patientId, String UUID, String deviceSN, String deviceID, VolleyResponse.Listener listener);

    IMPlusObject getDevice(String UUID, VolleyResponse.Listener listener);

    IMPlusObject addDeviceConfig(String patientId, String UUID, VolleyResponse.Listener listener);

    IMPlusObject queryInfoFromServer(String patientId, VolleyResponse.Listener listener);

//    IMPlusObject queryUpgradeInfo(String romVersion, String appVersion, VolleyResponse.Listener response);

    IMPlusObject setAutoTestTime(String patientId, String period, VolleyResponse.Listener response);

//    IMPlusObject unbindDevice(String deviceId, VolleyResponse.Listener response);


//    IMPlusObject startSleep(String ip, VolleyResponse.Listener response);
//
//    IMPlusObject stopSleep(String ip, VolleyResponse.Listener response);
//
//    IMPlusObject queryInfoFromDevice(String ip, VolleyResponse.Listener response);

    IMPlusHttp getHttpClient();

    IMPlusObject getMPlusObject(Request request);

    IMPlusDeviceConfig getMPlusDeviceConfig();



}
