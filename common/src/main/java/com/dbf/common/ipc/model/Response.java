package com.dbf.common.ipc.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Response implements Parcelable {
    private String source;//远程方法执行返回结果
    private boolean isSuccess;//是否成功执行远程方法

    public Response(String source, boolean isSuccess) {
        this.source = source;
        this.isSuccess = isSuccess;
    }

    protected Response(Parcel in) {
        source = in.readString();
        isSuccess = in.readByte() != 0;
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(source);
        dest.writeByte((byte) (isSuccess ? 1 : 0));
    }
}
