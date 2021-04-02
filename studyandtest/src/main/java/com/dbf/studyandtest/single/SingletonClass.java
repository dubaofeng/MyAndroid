package com.dbf.studyandtest.single;

import android.util.Log;

import java.util.logging.Logger;

/**
 * Created by dbf on 2020/12/17
 * describe:
 */
public class SingletonClass {
    private static class SingletonClassInstance {
        private static final SingletonClass instance = new SingletonClass();
    }

    public static SingletonClass getInstance() {
        return SingletonClassInstance.instance;
    }

    private SingletonClass() {

    }
}
