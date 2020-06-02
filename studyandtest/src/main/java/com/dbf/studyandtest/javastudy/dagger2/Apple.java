package com.dbf.studyandtest.javastudy.dagger2;

import com.dbf.common.myutils.MyLog;

import javax.inject.Inject;
import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;

public class Apple implements Foot {
    private final String TAG = "Foot";
    private String type = "普通";

    //    @Inject
    public Apple() {
        //@Inject只能注释其中一个构造方法
        MyLog.INSTANCE.i(TAG, "Apple");
    }

    public void appleType(String from) {
        type = from;
    }

    @Override
    public void eatFoot() {
        MyLog.INSTANCE.i(TAG, "吃" + type + "苹果");
    }

    @Scope
    public @interface AppleSigle {
    }

}
