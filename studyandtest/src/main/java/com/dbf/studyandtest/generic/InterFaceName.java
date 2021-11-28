package com.dbf.studyandtest.generic;

/**
 * Created by dbf on 2021/7/3
 * describe:
 *
 * @author raden
 */
public interface InterFaceName<T> {
    /**
     * 获得T
     */
    T getT();

    /**
     * 返回T
     */
    void setT(T t);
}
