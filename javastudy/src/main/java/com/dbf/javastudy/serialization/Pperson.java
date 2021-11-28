package com.dbf.javastudy.serialization;

import java.io.Serializable;

/**
 * Created by dbf on 2021/10/24
 * describe:
 */
public class Pperson implements Serializable {
    private static final long serialVersionUID = 2579011663991386349L;
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
