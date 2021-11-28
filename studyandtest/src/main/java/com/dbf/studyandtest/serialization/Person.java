package com.dbf.studyandtest.serialization;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by dbf on 2021/10/17
 * describe:
 *
 * @author raden
 */
public class Person implements Serializable  {

    private String name;
    private int age;

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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

