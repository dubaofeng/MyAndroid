package com.dbf.studyandtest.myaidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.dbf.common.myutils.MyLog;
import com.dbf.studyandtest.R;

public class MyTestAidlMainActivity extends AppCompatActivity {
    private final String TAG = "生命周期C";
    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_my_test_aidl_main);
        startService(new Intent(this, MyAidlService.class));
    }

    public void clickButton(View view) {
        switch (view.getId()) {
            case R.id.connectAidlSv:
                bindService(new Intent(this, MyAidlService.class), connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.add:
                Person person = new Person("小", true, 18);
                try {
                    MyLog.INSTANCE.i(TAG, "person之前=" + person);
                    iMyAidlInterface.addPerson(person);
                    MyLog.INSTANCE.i(TAG, "person之后=" + person);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
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
//        unbindService(connection);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: service=" + service);
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };
}
