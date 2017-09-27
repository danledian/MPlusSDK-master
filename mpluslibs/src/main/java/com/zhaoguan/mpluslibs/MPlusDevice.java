package com.zhaoguan.mpluslibs;

import com.lt.volley.http.Request;
import com.lt.volley.http.VolleyResponse;
import com.zhaoguan.mpluslibs.entity.MPlusObject;

/**
 * Created by husong on 2017/6/27.
 */

public class MPlusDevice implements IMPlusDevice {

    private static MPlusDevice mMPlusDevice;

    private IMPlusDeviceConfig mIMPlusDeviceConfig;

    public static IMPlusDevice get(){
        if(mMPlusDevice == null){
            synchronized (MPlusDevice.class){
                if (mMPlusDevice == null){
                    mMPlusDevice = new MPlusDevice();
                }
            }
        }
        return mMPlusDevice;
    }

    private MPlusDevice() {
        mIMPlusDeviceConfig = new MPlusDeviceConfigImpl(MPlusClient.getContext());
    }

    @Override
    public IMPlusObject boundDeviceForClient(String patientId, String UUID, String deviceSN, String deviceID, VolleyResponse.Listener listener) {
        return getMPlusObject(getHttpClient().boundDeviceForClient(patientId, UUID, deviceSN, deviceID, listener));
    }

    @Override
    public IMPlusObject getDevice(String UUID, VolleyResponse.Listener listener) {
        return getMPlusObject(getHttpClient().getDeviceWithUUID(UUID, listener));
    }

    @Override
    public void stopVoiceConfig() {
        getMPlusDeviceConfig().stopVoiceConfig();
    }

    @Override
    public IMPlusObject getReportsWithEnd(String patientId, String end, int count, VolleyResponse.Listener listener) {
        return getMPlusObject(getHttpClient().getReportsForSDKWithEndAndCnt(patientId, end, count ,listener));
    }

    @Override
    public IMPlusObject getReportsWithEndAndBegin(String patientId, String begin, String end, VolleyResponse.Listener listener) {
        return getMPlusObject(getHttpClient().getReportsForSDKWithEndAndBegin(patientId, begin, end, listener));
    }

    @Override
    public void voiceConfig(String wifiName, String wifiPwd, MPlusConfigCallback response){
        voiceConfig(wifiName, wifiPwd, null, response);
    }

    @Override
    public void voiceConfig(String wifiName, String wifiPwd, String deviceSn, MPlusConfigCallback response) {
        getMPlusDeviceConfig().voiceConfigWifi(wifiName, wifiPwd, deviceSn, response);
    }

    @Override
    public IMPlusObject addDeviceConfig(String patientId, String UUID, VolleyResponse.Listener listener) {
        return getMPlusObject(getHttpClient().createDeviceWithUUID(patientId, UUID, listener));
    }

    @Override
    public IMPlusObject queryInfoFromServer(String patientId, VolleyResponse.Listener listener) {
        return getMPlusObject(getHttpClient().getDeviceWithFactoryUserID(patientId, listener));
    }

    @Override
    public IMPlusObject setAutoTestTime(String patientId, String period, VolleyResponse.Listener response) {
        MPlusDeviceRequest mPlusDeviceRequest = new MPlusDeviceRequest();
        mPlusDeviceRequest.setAutoTestTime(patientId, period, response);
        return getMPlusObject(mPlusDeviceRequest);
    }

//    @Override
//    public IMPlusObject queryUpgradeInfo(String romVersion, String appVersion, VolleyResponse.Listener response) {
//        return getMPlusObject(getHttpClient().getOta(MPlusConstants.USER_TYPE[1], romVersion, appVersion, response));
//    }

//    @Override
//    public IMPlusObject unbindDevice(String deviceId, VolleyResponse.Listener response) {
//        DeviceEntity entity = new DeviceEntity();
//        entity.setObjectId(deviceId);
//        return getMPlusObject(getHttpClient().unbindDevice(entity, response));
//    }

//    @Override
//    public IMPlusObject startSleep(String ip, VolleyResponse.Listener response) {
//        return getMPlusObject(getHttpClient().startSleep(ip, UserLab.get().getPatientId(), response));
//    }
//
//    @Override
//    public IMPlusObject stopSleep(String ip, VolleyResponse.Listener response) {
//        return getMPlusObject(getHttpClient().stopSleep(ip, response));
//    }
//
//    @Override
//    public IMPlusObject queryInfoFromDevice(String ip, VolleyResponse.Listener response) {
//        return getMPlusObject(getHttpClient().getDeviceInfo(ip, "all", response));
//    }

    @Override
    public IMPlusHttp getHttpClient() {
        synchronized (this){
            return MPlusHttpClientImpl.get();
        }
    }

    @Override
    public IMPlusObject getMPlusObject(Request request) {
        return new MPlusObject(request);
    }

    @Override
    public IMPlusDeviceConfig getMPlusDeviceConfig() {
        return mIMPlusDeviceConfig;
    }
}
