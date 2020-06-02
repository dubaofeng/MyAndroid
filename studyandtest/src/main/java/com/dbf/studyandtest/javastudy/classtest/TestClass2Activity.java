package com.dbf.studyandtest.javastudy.classtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dbf.common.myutils.MyLog;
import com.dbf.studyandtest.R;

public class TestClass2Activity extends TestClass1Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_class2);
        Class<?> clas = getClass();
        MyLog.INSTANCE.i("TestClass", clas.toString());
        getPClass();
        MyLog.INSTANCE.i("TestClass", clas.getSuperclass().toString());
    }
}
