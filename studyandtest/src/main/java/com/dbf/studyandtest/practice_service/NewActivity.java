package com.dbf.studyandtest.practice_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.dbf.studyandtest.R;
import com.dbf.studyandtest.databinding.ActivityNewBinding;

public class NewActivity extends AppCompatActivity {
    ActivityNewBinding binding;
    ServiceConnection serviceConnection;
    MyPracticeService myPracticeService;
    private boolean isBindService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.binService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewActivity.this, "点击了绑定服务", Toast.LENGTH_SHORT).show();
//                bindService();
                stopService(new Intent(NewActivity.this, MyPracticeService.class));
            }
        });
        binding.unBinService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewActivity.this, "点击了解绑服务", Toast.LENGTH_SHORT).show();
                unbindService(serviceConnection);
            }
        });
    }

    private void bindService() {
        Intent intent = new Intent(this, MyPracticeService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyPracticeService.LocalBinder binder = (MyPracticeService.LocalBinder) service;
                myPracticeService = binder.getService();
                myPracticeService.showMsg("NewActivity");
                isBindService = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBindService = false;
            }
        };
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}