package com.zhaoguan.mplus.sdk.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {

    private int breathRate;
    private String versionNO;
    private int modeType;
    private int sptLieWarnStatus;
    private String name;
    private String romVersion;
    private boolean inBed;
    private String type;
    private String monitorStatus;
    private boolean rawDataUpload;
    private int ledOnTime;
    private boolean sleepMusicSwitch;
    private boolean active;
    private String wifiName;
    private IdPatient idPatient;
    private int frameArea;
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

    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public int getSptLieWarnStatus() {
        return sptLieWarnStatus;
    }

    public void setSptLieWarnStatus(int sptLieWarnStatus) {
        this.sptLieWarnStatus = sptLieWarnStatus;
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

    public boolean isInBed() {
        return inBed;
    }

    public void setInBed(boolean inBed) {
        this.inBed = inBed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public IdPatient getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(IdPatient idPatient) {
        this.idPatient = idPatient;
    }

    public int getFrameArea() {
        return frameArea;
    }

    public void setFrameArea(int frameArea) {
        this.frameArea = frameArea;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.breathRate);
        dest.writeString(this.versionNO);
        dest.writeInt(this.modeType);
        dest.writeInt(this.sptLieWarnStatus);
        dest.writeString(this.name);
        dest.writeString(this.romVersion);
        dest.writeByte(this.inBed ? (byte) 1 : (byte) 0);
        dest.writeString(this.type);
        dest.writeString(this.monitorStatus);
        dest.writeByte(this.rawDataUpload ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ledOnTime);
        dest.writeByte(this.sleepMusicSwitch ? (byte) 1 : (byte) 0);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeString(this.wifiName);
        dest.writeParcelable(this.idPatient, flags);
        dest.writeInt(this.frameArea);
        dest.writeString(this.period);
        dest.writeString(this.UUID);
        dest.writeString(this.deviceSN);
        dest.writeString(this.localIP);
        dest.writeString(this.workStatus);
        dest.writeString(this.objectId);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public Device() {
    }

    protected Device(Parcel in) {
        this.breathRate = in.readInt();
        this.versionNO = in.readString();
        this.modeType = in.readInt();
        this.sptLieWarnStatus = in.readInt();
        this.name = in.readString();
        this.romVersion = in.readString();
        this.inBed = in.readByte() != 0;
        this.type = in.readString();
        this.monitorStatus = in.readString();
        this.rawDataUpload = in.readByte() != 0;
        this.ledOnTime = in.readInt();
        this.sleepMusicSwitch = in.readByte() != 0;
        this.active = in.readByte() != 0;
        this.wifiName = in.readString();
        this.idPatient = in.readParcelable(IdPatient.class.getClassLoader());
        this.frameArea = in.readInt();
        this.period = in.readString();
        this.UUID = in.readString();
        this.deviceSN = in.readString();
        this.localIP = in.readString();
        this.workStatus = in.readString();
        this.objectId = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
}
