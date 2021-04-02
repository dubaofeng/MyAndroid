package com.dbf.studyandtest.practice_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyPracticeService extends Service {
    private final String TAG = MyPracticeService.class.getCanonicalName();
    LocalBinder localBinder;

    public MyPracticeService() {
        Log.i(TAG, "MyPracticeService: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        localBinder = new LocalBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return localBinder;
    }

    public class LocalBinder extends Binder {
        MyPracticeService getService() {
            return MyPracticeService.this;
        }
    }

    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        if ("NewActivity".equals(msg)) {
//            Log.i(TAG, "调用定停止服务");
//            stopSelf();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
//  return       super.onUnbind(intent);
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "onRebind: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}