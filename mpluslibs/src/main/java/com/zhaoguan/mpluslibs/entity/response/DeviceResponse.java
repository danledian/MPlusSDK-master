package com.zhaoguan.mpluslibs.entity.response;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.lt.volley.http.HttpBaseEntity;

import org.json.JSONException;

/**
 * Created by song on 2016/7/16.
 */
public class DeviceResponse extends HttpBaseEntity{

    private static final String TAG = "DeviceResponse";

    private ResultEntity result;

    @Override
    public void logMessage(String message) {
        Log.d(TAG, String.format("DeviceResponse:%s", message));
    }

    @Override
    public void fromWebString(String msg) throws JSONException {
        result = JSONObject.parseObject(msg, DeviceResponse.class).result;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }


    public static class ResultEntity {

        private int breathRate;
        private String versionNO;
        private String name;
        private String romVersion;
        private String monitorStatus;
        private boolean rawDataUpload;
        private int ledOnTime;
        private boolean sleepMusicSwitch;
        private boolean active;
        private String wifiName;
        private String period;
        private String UUID;
        private String deviceSN;
        private String localIP;
        private String workStatus;
        private String objectId;
        private String createdAt;
        private String updatedAt;

        public int getBreathRate() {
            return breathRate;
        }

        public void setBreathRate(int breathRate) {
            this.breathRate = breathRate;
        }

        public String getVersionNO() {
            return versionNO;
        }

        public void setVersionNO(String versionNO) {
            this.versionNO = versionNO;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRomVersion() {
            return romVersion;
        }

        public void setRomVersion(String romVersion) {
            this.romVersion = romVersion;
        }

        public String getMonitorStatus() {
            return monitorStatus;
        }

        public void setMonitorStatus(String monitorStatus) {
            this.monitorStatus = monitorStatus;
        }

        public boolean isRawDataUpload() {
            return rawDataUpload;
        }

        public void setRawDataUpload(boolean rawDataUpload) {
            this.rawDataUpload = rawDataUpload;
        }

        public int getLedOnTime() {
            return ledOnTime;
        }

        public void setLedOnTime(int ledOnTime) {
            this.ledOnTime = ledOnTime;
        }

        public boolean isSleepMusicSwitch() {
            return sleepMusicSwitch;
        }

        public void setSleepMusicSwitch(boolean sleepMusicSwitch) {
            this.sleepMusicSwitch = sleepMusicSwitch;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getWifiName() {
            return wifiName;
        }

        public void setWifiName(String wifiName) {
            this.wifiName = wifiName;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getUUID() {
            return UUID;
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }

        public String getDeviceSN() {
            return deviceSN;
        }

        public void setDeviceSN(String deviceSN) {
            this.deviceSN = deviceSN;
        }

        public String getLocalIP() {
            return localIP;
        }

        public void setLocalIP(String localIP) {
            this.localIP = localIP;
        }

        public String getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(String workStatus) {
            this.workStatus = workStatus;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
