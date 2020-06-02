package com.dbf.common.ipc.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parameters implements Parcelable {
    private String type;//参数类型
    private String value;//参数值，json序列化后的字符串

    public Parameters(String type, String value) {
        this.type = type;
        this.value = value;
    }

    protected Parameters(Parcel in) {
        type = in.readString();
        value = in.readString();
    }

    public static final Creator<Parameters> CREATOR = new Creator<Parameters>() {
        @Override
        public Parameters createFromParcel(Parcel in) {
            return new Parameters(in);
        }

        @Override
        public Parameters[] newArray(int size) {
            return new Parameters[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(value);
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
