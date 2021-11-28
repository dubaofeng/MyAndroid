package com.dbf.studyandtest.generic;

/**
 * Created by dbf on 2021/7/3
 * describe:
 *
 * @author raden
 */
public class ClassImpl<T extends String> implements InterFaceName<T> {
    private T t;
    @Override
    public T getT() {
        return t;
    }

    @Override
    public void setT(T t) {
        this.t = t;
    }
}
