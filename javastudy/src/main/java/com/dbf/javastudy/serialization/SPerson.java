package com.dbf.javastudy.serialization;

import java.io.Serializable;

/**
 * Created by dbf on 2021/10/17
 * describe:
 *
 * @author raden
 */
public class SPerson  implements Serializable {

    private static final long serialVersionUID = 2088008379212083287L;
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

