package com.dbf.studyandtest.myaidl.location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dbf.common.ipc.Register;

public class GpsService extends Service {
    public GpsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Register.getInstance().register(LocationManager.class);
        LocationManager.getDefault().setLocationBean(new LocationBean("深圳", 128.00f, 35.00f));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
