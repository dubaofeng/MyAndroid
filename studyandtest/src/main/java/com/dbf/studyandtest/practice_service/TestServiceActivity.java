package com.dbf.studyandtest.practice_service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbf.studyandtest.R;
import com.dbf.studyandtest.databinding.ActivityTestServiceBinding;

public class TestServiceActivity extends AppCompatActivity implements View.OnClickListener {
    MyPracticeService myPracticeService;
    private boolean isBindService = false;
    ActivityTestServiceBinding binding;
    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestServiceBinding.inflate(getLayoutInflater());
        binding.binService.setOnClickListener(this);
        binding.unBinService.setOnClickListener(this);
        binding.startService.setOnClickListener(this);
        binding.startNewActivity.setOnClickListener(this);
        setContentView(binding.getRoot());
    }


    private void startService() {
        Intent intent = new Intent(this, MyPracticeService.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent(this, MyPracticeService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyPracticeService.LocalBinder binder = (MyPracticeService.LocalBinder) service;
                myPracticeService = binder.getService();
                myPracticeService.showMsg("TestServiceActivity");
                isBindService = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBindService = false;
            }
        };
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startService:
                startService();
                break;
            case R.id.binService:
                bindService();
                break;
            case R.id.unBinService:
                unbindService(serviceConnection);
                break;
            case R.id.startNewActivity:
                startActivity(new Intent(this, NewActivity.class));
                break;
        }
    }
}