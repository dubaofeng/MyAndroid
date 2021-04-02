package com.dbf.studyandtest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dbf.common.myutils.MyLog;
import com.dbf.studyandtest.custom_view.MyPracticeCustomLayout;
import com.dbf.studyandtest.javastudy.classtest.TestClass2Activity;

import java.util.Observable;
import java.util.Observer;

public class TestMetaDataActivity extends AppCompatActivity {
    private final String TAG = "生命周期A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };
        Log.i(TAG, "onCreate");
        My my = new My();
        MyObserver myObserver = new MyObserver();
        my.addObserver(myObserver);
        my.hasChanged();
        my.myNotify(100);
        setContentView(R.layout.activity_test_meta_data);
        MyPracticeCustomLayout myPracticeCustomLayout = findViewById(R.id.test);
        myPracticeCustomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestMetaDataActivity.this, TestClass2Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
//        try {
//            ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
//            int id = activityInfo.metaData.getInt("Test1");
//            MyLog.INSTANCE.i("TestMetaDataTest1", getString(id));
//            MyLog.INSTANCE.i("TestMetaDataTest2", activityInfo.metaData.getString("Test2"));
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

//        Test1 test1 = new Test2();
//        Test2 test2 = new Test1();
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
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    class MyObserver implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            MyLog.INSTANCE.i("TestMetaDataTest1", "sssss");
            if (o instanceof My) {
                MyLog.INSTANCE.i("TestMetaDataTest1", ((My) o).i + "被观察者的值" + "传递给通知方法的参数=" + arg);
            }
        }
    }

    class My extends Observable {
        public int i = 1;

        public void myNotify(int change) {
            setChanged();
            notifyObservers(change);
        }
    }

    class Broa extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }


    class Test1 {
    }

    class Test2 extends Test1 {
    }

}
