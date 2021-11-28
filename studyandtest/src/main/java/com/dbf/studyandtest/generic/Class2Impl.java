package com.dbf.studyandtest.generic;

/**
 * Created by dbf on 2021/7/3
 * describe:
 *
 * @author raden
 */
public class Class2Impl implements InterFaceName<String> {
    private String string;

    @Override
    public String getT() {
        return string;
    }

    @Override
    public void setT(String s) {
        this.string = s;
    }
}
