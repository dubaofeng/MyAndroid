package com.dbf.studyandtest.javastudy.lambda;

import android.util.Log;

import com.dbf.common.myutils.MyLog;
import com.dbf.studyandtest.javastudy.reflect.Student;

public class MyLambda {
    private final String TAG = "MyLambda";
    //    Student student =()->{};
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("runnable");
        }
    };
    Runnable lambdaRunnable = () -> {
        System.out.println("lambdaRunnable");
    };
    Runnable lambdaRunnable2 = () -> System.out.println("lambdaRunnable");

    public void mian() {
//        new Thread(runnable).start();
//        new Thread(lambdaRunnable).start();
//        lambda语法格式一: 无参数无返回值，()不能省略，{}可以省略
        MyLambda1 myLambda1 = () -> MyLog.INSTANCE.i(TAG, "MyLambda1");
        myLambda1.runTest();
//        lambda语法格式二: 有一个参数无返回值，()能省略，也可以写(),{}可以省略
        MyLambda2 myLambda2 = s -> MyLog.INSTANCE.i(TAG, s);
        myLambda2 = (s) -> MyLog.INSTANCE.i(TAG, s);
        myLambda2.runTest("Mylambda2");
//        有多个参数无返回值
        MyLambda3 myLambda3 = (s, d) -> MyLog.INSTANCE.i(TAG, s + d);
        myLambda3.runTest("I ma s", " I ma d");
        //无参数 有返回值
        MyLambda4 myLambda4 = () -> {
            return "Mylambda4";
        };

        MyLog.INSTANCE.i(TAG, myLambda4.runTest());
    }
}
