package com.dbf.studyandtest.myaidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.dbf.common.myutils.MyLog;
import com.dbf.studyandtest.R;

public class MyTestAidlMainActivity extends AppCompatActivity {
    private IMyAidlInterface iMyAidlInterface;
    private final String TAG = "MyTestAidlMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    iMyAidlInterface.addPerson(person);
                    MyLog.INSTANCE.i(TAG, "返回=" + iMyAidlInterface.getPersonList().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };
}
