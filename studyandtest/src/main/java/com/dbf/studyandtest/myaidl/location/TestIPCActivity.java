package com.dbf.studyandtest.myaidl.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import com.dbf.common.ipc.IPC;
import com.dbf.common.ipc.IPCService;
import com.dbf.common.myutils.ToastUtils;
import com.dbf.studyandtest.R;

public class TestIPCActivity extends AppCompatActivity {
    ILocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_i_p_c);
        startService(new Intent(this, GpsService.class));
        IPC.connect(this, IPCService.IPCService0.class);
    }

    public void getLocation(View view) {
        locationManager = IPC.getInstanceWithName(IPCService.IPCService0.class, ILocationManager.class, "getDefault");

//        LocationBean locationBean = locationManager.getLocationBean();
        LocationBean locationBean = locationManager.setLocationBean(new LocationBean("上海", 128.00f, 35.00f));
        ToastUtils.showText(this, locationBean.toString());
    }

    public void getTest(View view) {
        LocationBean locationBean = locationManager.getLocationBean("sss");
        ToastUtils.showText(this, locationBean.toString());
    }
}
