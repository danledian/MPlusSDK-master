package com.zhaoguan.mpluslibs;

import com.lt.volley.http.Request;
import com.lt.volley.http.VolleyResponse;

/**
 * Created by husong on 2017/7/20.
 */

public interface IMPlusHttp {

    Request register(VolleyResponse.Listener listener);

    Request login(String patientId, VolleyResponse.Listener listener);

    Request createDeviceWithUUID(String patientId, String UUID, VolleyResponse.Listener listener);

    Request getDeviceWithUUID(String UUID, VolleyResponse.Listener listener);

    Request getDeviceWithFactoryUserID(String patientId, VolleyResponse.Listener listener);

    Request boundDeviceForClient(String patientId, String UUID, String deviceSN, String deviceID, VolleyResponse.Listener listener);

    Request getReportsForSDKWithEndAndBegin(String patientId, String begin, String end, VolleyResponse.Listener listener);

    Request getReportsForSDKWithEndAndCnt(String patientId, String end, int count, VolleyResponse.Listener listener);

    Request getReportsCntForSDKWithEndAndBegin(String patientId, String begin, String end, VolleyResponse.Listener listener);

    Request setAutoTestTime(String objectId, String period, VolleyResponse.Listener listener);

    Request unbindDevice(String objectId, VolleyResponse.Listener listener);

    MPlusHttpRequestBuilder getHttpRequestBuilder();

}
