
package com.dbf.studyandtest.recordscreen;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dbf.studyandtest.databinding.ActivityRecordScreenBinding;

public class RecordScreenActivity extends AppCompatActivity {
    private final String TAG = RecordScreenActivity.class.getCanonicalName();
    private final int REQUEST_MEDIA_PROJECTION = 1;
    private ActivityRecordScreenBinding binding;
    private RecordScreenService screenService;
    private MyServiceConnection myServiceConnection;
    private MediaProjectionManager mediaProjectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.start.setOnClickListener(v -> {
            startRecordScreen();
        });
        binding.stop.setOnClickListener(v -> {
            if (screenService != null) {
                screenService.stopRecord();
            }
        });
    }


    private void startRecordScreen() {
        Intent startServiceIntent = new Intent(this, RecordScreenService.class);
        myServiceConnection = new MyServiceConnection();
        bindService(startServiceIntent, myServiceConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (screenService != null) {
                MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
                screenService.startRecord(mediaProjection);
            }
        }
    }

    class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (screenService == null) {
                screenService = ((RecordScreenService.ServiceObject) service).getService();
                Log.i(TAG, "onServiceConnected: ");
                mediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService(MEDIA_PROJECTION_SERVICE);
                Intent captureIntent = mediaProjectionManager.createScreenCaptureIntent();
                startActivityForResult(captureIntent, REQUEST_MEDIA_PROJECTION);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            screenService = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(myServiceConnection);
    }
}