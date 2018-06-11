package com.zhaoguan.mpluslibs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.libra.sinvoice.MyVoice;
import com.lt.volley.http.HttpBaseEntity;
import com.lt.volley.http.VolleyResponse;
import com.lt.volley.http.error.VolleyError;
import com.zhaoguan.mpluslibs.entity.UserLab;
import com.zhaoguan.mpluslibs.entity.response.DeviceResponse;
import com.zhaoguan.mpluslibs.exception.MPlusArgumentException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by husong on 2017/6/28.
 */

public class MPlusDeviceConfigImpl implements IMPlusDeviceConfig, MyVoice.listener, MPlusConfigCallback {

    private static final String TAG = "MPlusDeviceConfigImpl";

    private static final int BIND_DEVICE_FAILURE = 1;
    private static final int MSG_GET_DEVICE_INFO = 202;
    private static final char START = '0';
    private static final char END = '1';

    private MyVoice _recognition;
    private IMPlusObject mIMPlusObject;
    private MPlusConfigCallback mCallback;
    private String uuid;
    private String mWifiName;
    private String mWifiPwd;
    private String mDeviceSn;
    private long mStartTime;

    private IMPlusObject mBoundDeviceForClientRequest;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_GET_DEVICE_INFO:
                    requestServerDeviceInfo();
                    break;
                default:
                    break;
            }
        }
    };

    public MPlusDeviceConfigImpl(Context context) {
        _recognition = new MyVoice();
        _recognition.init(context, MPlusDeviceConfigImpl.this);
    }

    @Override
    public void voiceConfigWifi(String wifiName, String wifiPwd, String deviceSn, MPlusConfigCallback callback) {
        reset();
        startVoiceConfigWifi(wifiName, wifiPwd, deviceSn, callback);
    }

    @Override
    public void stopVoiceConfig() {
        reset();
    }

    private void startVoiceConfigWifi(String wifiName, String wifiPwd, String deviceSn, MPlusConfigCallback callback) {
        if(!isUserLogin()){
            callback.onFailure(201, "User is not login");
            return;
        }
        if(TextUtils.isEmpty(wifiName)){
            throw new MPlusArgumentException("wifiName not allow null");
        }

        synchronized (this){
            mWifiName = wifiName;
            mWifiPwd = wifiPwd;
            mDeviceSn = deviceSn;
            uuid = UUID.randomUUID().toString().replace("-", "");
            Log.d(TAG, String.format("wifiName:%s, wifiPwd:%s, deviceSn:%s, uuid:%s", wifiName, wifiPwd, deviceSn, uuid));
        }

        mCallback = callback;

        if(mIMPlusObject != null)
            mIMPlusObject.cancel();
        mIMPlusObject = MPlusDevice.get().addDeviceConfig(UserLab.get().getPatientId(), uuid, new VolleyResponse.Listener() {
            @Override
            public void onSuccess(String content) {
                Log.d(TAG, String.format("addDeviceConfig:%s", content));
                ArrayList<MyVoice.VoiceMessageForClient> voiceMessageArrayList = createVoiceMessageForClient();
                if(voiceMessageArrayList != null){

                    _recognition.sendMsg(voiceMessageArrayList);
                    startCheckConfigState();
                    onStartVoice();
                }else {
                    Log.d(TAG, "voiceMessageArrayList is null");
                   onFailure(-1, "createVoiceMessageForClient failure");
                }
            }

            @Override
            public void onError(VolleyError volleyError) {
                MPlusErrorDelivery.handleResponseError(volleyError, mCallback);
            }
        });
    }


    private void startCheckConfigState() {
        mStartTime = System.currentTimeMillis();
        if (mHandler != null){
            mHandler.sendEmptyMessageDelayed(MSG_GET_DEVICE_INFO, 3 * 1000);
        }
    }


    @Override
    public ArrayList<MyVoice.VoiceMessageForClient> createVoiceMessageForClient() {
        try {
            mWifiName = new String(mWifiName.getBytes(), "UTF-8");
            mWifiPwd = new String(mWifiPwd.getBytes(), "UTF-8");
            if(!TextUtils.isEmpty(mDeviceSn)){
                mDeviceSn = new String(mDeviceSn.getBytes(), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        Log.d(TAG, String.format("_uuid_send:%s, mDeviceSn:%s, mSid:%s, mPwd:%s", uuid, mDeviceSn, mWifiName, mWifiPwd));
        ArrayList<MyVoice.VoiceMessageForClient> msgList = new ArrayList<MyVoice.VoiceMessageForClient>();
        msgList.add(new MyVoice.VoiceMessageForClient("id", uuid, START));
        msgList.add(new MyVoice.VoiceMessageForClient("wf", mWifiName, START));
        if(!TextUtils.isEmpty(mDeviceSn)){
            msgList.add(new MyVoice.VoiceMessageForClient("sn", mDeviceSn, START));
        }
        msgList.add(new MyVoice.VoiceMessageForClient("ps", mWifiPwd, END));
        return msgList;
    }

    private boolean isUserLogin(){
        if(!TextUtils.isEmpty(UserLab.get().getPatientId()))
            return true;
        return false;
    }

    @Override
    public void onMsg(ArrayList msg) {

    }

    private IMPlusObject mDeviceRequest;

    private void requestServerDeviceInfo() {
        Log.d(TAG, "requestServerDeviceInfo" + uuid);
        if(TextUtils.isEmpty(uuid))
            return;
        if (mDeviceRequest != null)
            mDeviceRequest.cancel();
        mDeviceRequest = MPlusDevice.get().getDevice(uuid, new VolleyResponse.Listener() {
            @Override
            public void onSuccess(String content) {
                Log.d(TAG, "getDevice" + content);
                DeviceResponse response = JSONObject.parseObject(content, DeviceResponse.class);
                if(isConfigSuccess(response)){
                    _recognition.stopSendMsg();
                    mHandler.removeMessages(MSG_GET_DEVICE_INFO);
                    requestBoundDeviceForClient(response);
                }else {
                    if (isTimeOut()) {
                        onTimeOutError();
                    }else {
                        continueGetDevice();
                    }
                }

            }

            @Override
            public void onError(VolleyError response) {
                if (isTimeOut()) {
                    onTimeOutError();
                }else {
                    continueGetDevice();
                }
            }
        });
    }

    private void requestBoundDeviceForClient(HttpBaseEntity response) {

        DeviceResponse.ResultEntity deviceEntity = ((DeviceResponse) response).getResult();

        String deviceId = deviceEntity.getObjectId();
        String deviceSn = deviceEntity.getDeviceSN();
        String UUID = deviceEntity.getUUID();

        Log.d(TAG, String.format("deviceID:%s, deviceSN:%s, UUID:%s", deviceId, deviceSn, UUID));

        if(TextUtils.isEmpty(UUID) || TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(deviceSn)){
            Log.d(TAG, "DeviceResponse objectId is null");
            return;
        }

        if(mBoundDeviceForClientRequest != null)
            mBoundDeviceForClientRequest.cancel();
        mBoundDeviceForClientRequest = MPlusDevice.get().boundDeviceForClient(UserLab.get().getPatientId(),
                UUID, deviceSn, deviceId, new VolleyResponse.Listener() {
            @Override
            public void onSuccess(String content) {
                Log.d(TAG, "boundDeviceForClient device success in server" + content);
                onConfigSuccess(content);
            }

            @Override
            public void onError(VolleyError response) {
                Log.d(TAG, "boundDeviceForClient device success in failure");
                onFailure(BIND_DEVICE_FAILURE, "bound Device failure");
            }
        });
    }

    private void continueGetDevice(){
        mHandler.sendEmptyMessageDelayed(MSG_GET_DEVICE_INFO, 3 * 1000);
    }

    private boolean isTimeOut() {
        long time = System.currentTimeMillis() - mStartTime;
        Log.d(TAG, "config device use time:" + time);
        return time > 5 * 60 * 1000;
    }

    @Override
    public void onStartVoice() {
        if(mCallback != null)
            mCallback.onStartVoice();
    }

    @Override
    public void onFailure(int code, String message) {
        if(mCallback != null)
            mCallback.onFailure(code, message);
    }

    @Override
    public void onConfigSuccess(String response) {
        if(mCallback != null)
            mCallback.onConfigSuccess(response);
    }

    private void onTimeOutError(){
        mHandler.removeMessages(MSG_GET_DEVICE_INFO);
        onFailure(-1, "onTimeOutError");
    }

    private boolean isConfigSuccess(DeviceResponse response){
        if(response == null)
            return false;
        DeviceResponse.ResultEntity deviceEntity = response.getResult();
        if (!TextUtils.isEmpty(deviceEntity.getWorkStatus()) && "1".equalsIgnoreCase(deviceEntity.getWorkStatus())) {
            String wifiName = deviceEntity.getWifiName();
            if (!TextUtils.isEmpty(wifiName) && (wifiName.equalsIgnoreCase(mWifiName) ||
                    ("\"" + wifiName + "\"").equalsIgnoreCase(mWifiName))) {
                Log.d(TAG, "get server wifi conn success");
                return true;
            }
        }
        return false;
    }

    private void reset() {
        if(mBoundDeviceForClientRequest != null)
            mBoundDeviceForClientRequest.cancel();
        if(mDeviceRequest != null)
            mDeviceRequest.cancel();
        mHandler.removeMessages(MSG_GET_DEVICE_INFO);
        if(_recognition != null){
            _recognition.stopSendMsg();
        }
        mCallback = null;
        uuid = null;
        mWifiName = null;
        mWifiPwd = null;
        mDeviceSn = null;
    }

}
