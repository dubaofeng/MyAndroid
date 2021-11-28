package com.dbf.studyandtest.serialization;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dbf on 2021/10/24
 * describe:
 * @author raden
 */
public class PPerson implements Parcelable {
    private String name;
    private int age;

    public PPerson() {
    }

    protected PPerson(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<PPerson> CREATOR = new Creator<PPerson>() {
        @Override
        public PPerson createFromParcel(Parcel in) {
            return new PPerson(in);
        }

        @Override
        public PPerson[] newArray(int size) {
            return new PPerson[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
