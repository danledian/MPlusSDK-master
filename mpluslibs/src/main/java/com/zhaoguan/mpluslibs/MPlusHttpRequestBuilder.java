package com.zhaoguan.mpluslibs;

import android.text.TextUtils;

import com.lt.volley.http.JsonRequest;
import com.lt.volley.http.Request;
import com.lt.volley.http.VolleyResponse;
import com.lt.volley.http.retrypolicy.HighNetworkSpeedRetryPolicy;
import com.lt.volley.http.retrypolicy.LowNetworkSpeedRetryPolicy;
import com.lt.volley.http.retrypolicy.NormalNetworkSpeedRetryPolicy;
import com.lt.volley.http.retrypolicy.RetryPolicy;
import com.zhaoguan.mpluslibs.entity.UserLab;
import com.zhaoguan.mpluslibs.exception.MPlusNotLoginError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.zhaoguan.mpluslibs.MPlusHttpConstants.START_SLEEP;
import static com.zhaoguan.mpluslibs.MPlusHttpConstants.STOP_SLEEP;

/**
 * Created by husong on 2017/7/20.
 */

public class MPlusHttpRequestBuilder implements IMPlusHttp{

    private static int mCurrentSpeed;

    private static final String JSON = "json";
    private String baseUrl = MPlusHttpConstants.BASE_URL;

    private boolean authorized(String patientId, VolleyResponse.Listener listener){
        if(TextUtils.isEmpty(UserLab.get().getFactoryCode()))
            throw new IllegalArgumentException("factoryCode is null");
        if(TextUtils.isEmpty(patientId)){
            listener.onError(new MPlusNotLoginError(new IllegalArgumentException("user is not login")));
            return false;
        }
        return true;
    }

    @Override
    public Request register(VolleyResponse.Listener listener) {
        String factoryCode = UserLab.get().getFactoryCode();
        if(TextUtils.isEmpty(factoryCode))
            throw new IllegalArgumentException("factoryCode is null");
        String json = String.format("{\"factoryCode\":\"%s\"}", factoryCode);
        Request request = createJsonRequest(MPlusHttpConstants.REGISTER_FOR_SDK, json, Request.Method.POST);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request login(String patientId, VolleyResponse.Listener listener) {
        String factoryCode = UserLab.get().getFactoryCode();
        if(!authorized(patientId, listener)){
            return null;
        }
        String json = String.format("{\"factoryCode\":\"%s\", \"patientId\":\"%s\"}", factoryCode, patientId);
        Request request = createJsonRequest(MPlusHttpConstants.LOGIN_FOR_SDK, json, Request.Method.POST);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request createDeviceWithUUID(String patientId, String UUID, VolleyResponse.Listener listener) {
        String factoryCode = UserLab.get().getFactoryCode();
        if(!authorized(patientId, listener))
            return null;
        String json = String.format("{\"factoryCode\":\"%s\", \"patientId\":\"%s\", \"UUID\":\"%s\"}", factoryCode, patientId, UUID);
        Request request = createJsonRequest(MPlusHttpConstants.CREATE_DEVICE_WITH_UUID_FOR_SDK, json, Request.Method.POST);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request getDeviceWithUUID(String UUID, VolleyResponse.Listener listener) {
        String factoryCode = UserLab.get().getFactoryCode();
        if (TextUtils.isEmpty(factoryCode) || TextUtils.isEmpty(UUID))
            throw new IllegalArgumentException("factoryCode or UUID is null");
        String json = String.format("{\"factoryCode\":\"%s\", \"UUID\":\"%s\"}", factoryCode, UUID);
        Request request = createJsonRequest(MPlusHttpConstants.GET_DEVICE_WITH_UUID_FOR_SDK, json, Request.Method.POST);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request getDeviceWithFactoryUserID(String patientId, VolleyResponse.Listener listener) {
        String factoryCode = UserLab.get().getFactoryCode();
        if(!authorized(patientId, listener))
            return null;
        String json = String.format("{\"factoryCode\":\"%s\", \"patientId\":\"%s\"}", factoryCode, patientId);
        Request request = createJsonRequest(MPlusHttpConstants.GET_DEVICE_WITH_FACTORY_USERID_FOR_SDK, json, Request.Method.POST);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request boundDeviceForClient(String patientId, String UUID, String deviceSN, String deviceID, VolleyResponse.Listener listener) {
        String factoryCode = UserLab.get().getFactoryCode();
        if(!authorized(patientId, listener))
            return null;
        if(TextUtils.isEmpty(UUID) || TextUtils.isEmpty(deviceSN) || TextUtils.isEmpty(deviceID))
            throw new IllegalArgumentException("argument is null");
        String json = String.format("{\"factoryCode\":\"%s\", \"patientId\":\"%s\", \"UUID\":\"%s\", \"deviceSN\":\"%s\", \"deviceID\":\"%s\"}",
                factoryCode, patientId, UUID, deviceSN, deviceID);
        Request request = createJsonRequest(MPlusHttpConstants.BOUND_DEVICE_FOR_CLIENT_SDK, json, Request.Method.POST);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request getReportsForSDKWithEndAndBegin(String patientId, String begin, String end, VolleyResponse.Listener listener) {
        String factoryCode = UserLab.get().getFactoryCode();
        if(!authorized(patientId, listener))
            return null;
        if(TextUtils.isEmpty(begin) || TextUtils.isEmpty(end))
            throw new IllegalArgumentException("argument is null");
        JSONObject json = new JSONObject();
        try {
            json.put("patientId", patientId);
            json.put("begin", getTime(begin));
            json.put("end", getTime(end));
            json.put("factoryCode", factoryCode);
        }catch (JSONException e){
            e.printStackTrace();
        }
        Request request = createJsonRequest(MPlusHttpConstants.GET_REPORTS_FOR_SDK_WITH_END_AND_BEGIN, json.toString(), Request.Method.POST);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request getReportsForSDKWithEndAndCnt(String patientId, String end, int count, VolleyResponse.Listener listener) {
        String factoryCode = UserLab.get().getFactoryCode();
        if(!authorized(patientId, listener))
            return null;
        if (TextUtils.isEmpty(end))
            throw new IllegalArgumentException("end is null");
        JSONObject json = new JSONObject();
        try {
            json.put("factoryCode", factoryCode);
            json.put("patientId", patientId);
            json.put("cnt", count);
            json.put("end", getTime(end));
        }catch (JSONException e){
            e.printStackTrace();
        }
        Request request = createJsonRequest(MPlusHttpConstants.GET_REPORTS_FOR_SDK_WITH_END_AND_CNT, json.toString(), Request.Method.POST);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request getReportsCntForSDKWithEndAndBegin(String patientId, String begin, String end, VolleyResponse.Listener listener) {
        String factoryCode = UserLab.get().getFactoryCode();
        if(!authorized(patientId, listener))
            return null;
        JSONObject json = new JSONObject();
        try {
            json.put("factoryCode", factoryCode);
            json.put("patientId", patientId);
            json.put("begin", getTime(begin));
            json.put("end", getTime(end));
        }catch (JSONException e){
            e.printStackTrace();
        }
        Request request = createJsonRequest(MPlusHttpConstants.GET_REPORTS_CNT_FOR_SDK_WITH_END_AND_BEGIN, json.toString(), Request.Method.POST);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request setAutoTestTime(String patientId, String period, VolleyResponse.Listener listener) {
        if(!authorized(patientId, listener))
            return null;
        JSONObject json = new JSONObject();
        try {
            json.put("period", period);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(TextUtils.isEmpty(patientId))return null;
        StringBuffer url = new StringBuffer();
        url.append(MPlusHttpConstants.DEVICE).append("/").append(patientId);
        Request request = createJsonRequest(url.toString(), json.toString(), Request.Method.PUT);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request unbindDevice(String objectId, VolleyResponse.Listener listener) {
        if(TextUtils.isEmpty(objectId))
            throw new RuntimeException("objectId is null");
        JSONObject json = new JSONObject();
        try {
            json.put("active", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringBuffer url = new StringBuffer();
        url.append(MPlusHttpConstants.DEVICE).append("/").append(objectId);
        Request request = createJsonRequest(url.toString(), json.toString(), Request.Method.PUT);
        request.setResponse(listener);
        return request;
    }

    @Override
    public Request startSleep(String patientId, String ip, VolleyResponse.Listener listener) {
        if(TextUtils.isEmpty(patientId) || TextUtils.isEmpty(ip))
            throw new RuntimeException("patientId or ip is null");
        Map<String, String> params = new HashMap<>();
        params.put("patientId", patientId);
        Request request = new Request.Builder()
                .setMethod(Request.Method.GET)
                .setParams(params)
                .setUrl(String.format("http://%s:8080%s", ip, START_SLEEP))
                .build();
        request.setResponse(listener);
        request.setRetryPolicy(new NormalNetworkSpeedRetryPolicy());
        return request;
    }

    @Override
    public Request stopSleep(String patientId, String ip, VolleyResponse.Listener listener) {
        if(TextUtils.isEmpty(patientId) || TextUtils.isEmpty(ip))
            throw new RuntimeException("patientId or ip is null");
        Map<String, String> params = new HashMap<>();
        params.put("patientId", patientId);
        Request request = new Request.Builder()
        .setMethod(Request.Method.GET)
        .setParams(params)
        .setUrl(String.format("http://%s:8080%s", ip, STOP_SLEEP))
        .build();
        request.setResponse(listener);
        request.setRetryPolicy(new NormalNetworkSpeedRetryPolicy());
        return request;
    }

    @Override
    public MPlusHttpRequestBuilder getHttpRequestBuilder() {
        return null;
    }

    private JSONObject getTime(String time){
        JSONObject json = new JSONObject();
        try {
            json.put("__type", "Date");
            json.put("iso", time);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return json;
    }

    private Request createJsonRequest(String url, String json, int method) {
        if (TextUtils.isEmpty(url))
            throw new IllegalArgumentException("url not allow null");
        Map<String, String> params = new HashMap<>();
        params.put(JSON, json);
        Request request = new JsonRequest.Builder()
                .setMethod(method)
                .addHeader("X-AVOSCloud-Application-Id", MPlusHttpConstants.APP_ID)
                .addHeader("X-AVOSCloud-Application-Key", MPlusHttpConstants.APP_KEY)
                .addHeader("X-LC-Prod", MPlusHttpConstants.getLc())
                .setParams(params)
//        .addHeader("", MASTER_KEY)
                .setUrl(baseUrl + url)
                .build();
        request.setRetryPolicy(getRetryPolicy());
        return request;
    }

    private static RetryPolicy getRetryPolicy() {
        switch (mCurrentSpeed) {
            case NetWorkUtils.HIGH_NETWORK_SPEED:
                return new HighNetworkSpeedRetryPolicy();
            case NetWorkUtils.LOW_NETWORK_SPEED:
                return new LowNetworkSpeedRetryPolicy();
            case NetWorkUtils.NORMAL_NETWORK_SPEED:
            case NetWorkUtils.NETWORK_UNKNOW:
            default:
                return new NormalNetworkSpeedRetryPolicy();
        }
    }
}
