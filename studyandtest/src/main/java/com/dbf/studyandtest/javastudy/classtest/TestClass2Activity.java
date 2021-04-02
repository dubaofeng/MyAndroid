package com.dbf.studyandtest.javastudy.classtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dbf.common.myutils.MyLog;
import com.dbf.studyandtest.R;
import com.dbf.studyandtest.myaidl.MyTestAidlMainActivity;

public class TestClass2Activity extends TestClass1Activity {
    private final String TAG = "生命周期B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_test_class2);
        Class<?> clas = getClass();
        MyLog.INSTANCE.i("TestClass", clas.toString());
        getPClass();
        MyLog.INSTANCE.i("TestClass", clas.getSuperclass().toString());


        Button button = findViewById(R.id.test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestClass2Activity.this, MyTestAidlMainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
