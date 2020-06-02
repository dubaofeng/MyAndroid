package com.dbf.studyandtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.dbf.common.myutils.MyLog;

public class TestMetaDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_meta_data);
        try {
            ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            int id = activityInfo.metaData.getInt("Test1");
            MyLog.INSTANCE.i("TestMetaDataTest1", getString(id));
            MyLog.INSTANCE.i("TestMetaDataTest2", activityInfo.metaData.getString("Test2"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
