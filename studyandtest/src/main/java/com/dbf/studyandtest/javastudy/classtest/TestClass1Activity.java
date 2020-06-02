package com.dbf.studyandtest.javastudy.classtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dbf.common.myutils.MyLog;
import com.dbf.studyandtest.R;

public class TestClass1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_class1);
    }

    public void getPClass() {
        Class<?> clas = getClass();
        MyLog.INSTANCE.i("TestClassP", clas.toString());
    }
}
