package com.dbf.studyandtest.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.dbf.common.myutils.MyLog;

import java.util.ArrayList;
import java.util.List;

public class MyAidlService extends Service {
    private ArrayList<Person> persons = new ArrayList<>();
private final String TAG = MyAidlService.class.getCanonicalName();
    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.INSTANCE.i("MyTestAidlMainActivity", "MyAidlService.onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.INSTANCE.i("MyTestAidlMainActivity", "MyAidlService.onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new IMyAidlInterface.Stub() {
            @Override
            public void addPerson(Person person) throws RemoteException {
                MyLog.INSTANCE.i(TAG, "服务端的person=" + person);
                person.setAge(1);
                persons.add(person);
            }

            @Override
            public List<Person> getPersonList() throws RemoteException {
                return persons;
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.INSTANCE.i("MyTestAidlMainActivity", "MyAidlService.onDestroy");
    }
}
