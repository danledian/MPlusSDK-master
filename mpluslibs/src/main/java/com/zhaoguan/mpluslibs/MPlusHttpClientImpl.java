package com.zhaoguan.mpluslibs;

import com.lt.volley.http.Request;
import com.lt.volley.http.RequestQueue;
import com.lt.volley.http.Volley;
import com.lt.volley.http.VolleyResponse;
import com.zhaoguan.mpluslibs.exception.MPlusInitializeError;

/**
 * Created by husong.
 */

public class MPlusHttpClientImpl implements IMPlusHttp {

    private static MPlusHttpClientImpl mMPlusHttpClientImpl;

    public static MPlusHttpClientImpl get(){
        if(mMPlusHttpClientImpl == null){
            synchronized (MPlusHttpClientImpl.class){
                if(mMPlusHttpClientImpl == null){
                    mMPlusHttpClientImpl = new MPlusHttpClientImpl();
                }
            }
        }
        return mMPlusHttpClientImpl;
    }

    private RequestQueue mRequestQueue;
    private MPlusHttpRequestBuilder mMPlusHttpRequestBuilder;

    public MPlusHttpClientImpl() {
        try {
            init();
        } catch (MPlusInitializeError e) {
            e.printStackTrace();
        }
    }

    private void init() throws MPlusInitializeError {
        if(MPlusClient.getContext() == null){
            throw new MPlusInitializeError("MPlus init failure");
        }
        mRequestQueue = Volley.newRequestQueue(MPlusClient.getContext());
        mRequestQueue.start();
    }

    @Override
    public MPlusHttpRequestBuilder getHttpRequestBuilder() {
        if(mMPlusHttpRequestBuilder == null){
            mMPlusHttpRequestBuilder = new MPlusHttpRequestBuilder();
        }
        return mMPlusHttpRequestBuilder;
    }

    private void add(Request request){
        if(request != null){
            mRequestQueue.add(request);
        }
    }

    @Override
    public Request register(VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().register(listener);
        add(request);
        return request;
    }

    @Override
    public Request login(String patientId, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().login(patientId, listener);
        add(request);
        return request;
    }

    @Override
    public Request createDeviceWithUUID(String patientId, String UUID, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().createDeviceWithUUID(patientId, UUID, listener);
        add(request);
        return request;
    }

    @Override
    public Request getDeviceWithUUID(String UUID, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().getDeviceWithUUID(UUID, listener);
        add(request);
        return request;
    }

    @Override
    public Request getDeviceWithFactoryUserID(String patientId, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().getDeviceWithFactoryUserID(patientId, listener);
        add(request);
        return request;
    }

    @Override
    public Request boundDeviceForClient(String patientId, String UUID, String deviceSN, String deviceID, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().boundDeviceForClient(patientId, UUID, deviceSN, deviceID, listener);
        add(request);
        return request;
    }

    @Override
    public Request getReportsForSDKWithEndAndBegin(String patientId, String begin, String end, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().getReportsForSDKWithEndAndBegin(patientId, begin, end, listener);
        add(request);
        return request;
    }

    @Override
    public Request getReportsForSDKWithEndAndCnt(String patientId, String end, int count, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().getReportsForSDKWithEndAndCnt(patientId, end, count, listener);
        add(request);
        return request;
    }

    @Override
    public Request getReportsCntForSDKWithEndAndBegin(String patientId, String begin, String end, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().getReportsCntForSDKWithEndAndBegin(patientId, begin, end, listener);
        add(request);
        return request;
    }

    @Override
    public Request setAutoTestTime(String patientId, String period, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().setAutoTestTime(patientId, period, listener);
        add(request);
        return request;
    }

    @Override
    public Request unbindDevice(String objectId, VolleyResponse.Listener listener) {
        Request request = getHttpRequestBuilder().unbindDevice(objectId, listener);
        add(request);
        return request;
    }

    @Override
    public Request directBindBluetoothDevice(String ip, String type, VolleyResponse.Listener response) {
        return null;
    }

    @Override
    public Request directUnBindBluetoothDevice(String ip, String type, VolleyResponse.Listener response) {
        return null;
    }

    @Override
    public Request directGetDeviceInfo(String ip, String type, VolleyResponse.Listener response) {
        return null;
    }
}
