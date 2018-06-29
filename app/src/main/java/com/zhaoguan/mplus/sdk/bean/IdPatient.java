package com.zhaoguan.mplus.sdk.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class IdPatient implements Parcelable {

    private String __type;
    private String className;
    private String objectId;

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.__type);
        dest.writeString(this.className);
        dest.writeString(this.objectId);
    }

    public IdPatient() {
    }

    protected IdPatient(Parcel in) {
        this.__type = in.readString();
        this.className = in.readString();
        this.objectId = in.readString();
    }

    public static final Parcelable.Creator<IdPatient> CREATOR = new Parcelable.Creator<IdPatient>() {
        @Override
        public IdPatient createFromParcel(Parcel source) {
            return new IdPatient(source);
        }

        @Override
        public IdPatient[] newArray(int size) {
            return new IdPatient[size];
        }
    };
}
