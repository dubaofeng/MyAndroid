package com.dbf.studyandtest.myaidl;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Person implements Parcelable {

    private String name;
    private boolean sex;
    private int age;

    public Person() {
    }

    public Person(String name, boolean sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }


    protected Person(Parcel in) {
        name = in.readString();
        sex = in.readByte() != 0;
        age = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (sex ? 1 : 0));
        dest.writeInt(age);
    }

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        sex = dest.readInt() != 0;
        age = dest.readInt();
    }
}
