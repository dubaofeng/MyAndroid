package com.dbf.studyandtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.dbf.studyandtest.databinding.ActivityTestHandlerBinding;

public class TestHandlerActivity extends AppCompatActivity {
    private final String TAG = TestHandlerActivity.class.getCanonicalName();
    private Handler threadHandler;
    private Handler mainHandler = new Handler(getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };
    ActivityTestHandlerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainHandler.sendEmptyMessageDelayed(0, 2000);
        binding = ActivityTestHandlerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.sendWorkThreadMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (threadHandler != null) {
                    Message message = Message.obtain();
                    message.obj = "点击了向子线程发送消息的按钮";
                    message.what = 1;
                    threadHandler.sendMessage(message);
                }
            }
        });
        binding.releaseWorkThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (threadHandler != null) {
                    threadHandler.sendEmptyMessageAtTime(-1, 0);
                }
            }
        });
        Log.i(TAG, "主线程=" + Thread.currentThread().getName());
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                threadHandler = new Handler(Looper.myLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case -1:
                                Log.i(TAG, "释放子线程");
                                Looper.myLooper().quitSafely();
                                break;
                            case 1:
                                Log.i(TAG, "收到主线程发的消息msg.obj=" + msg.obj);
                                break;
                        }
                    }
                };
                Log.i(TAG, "子线程=" + Thread.currentThread().getName());
                Log.i(TAG, "调用loop()阻塞");
                Looper.loop();
                Log.i(TAG, "线程执行完毕");
            }
        };
        thread.start();
    }


}