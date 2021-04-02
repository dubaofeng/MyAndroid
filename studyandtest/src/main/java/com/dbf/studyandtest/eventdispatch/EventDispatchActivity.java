package com.dbf.studyandtest.eventdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dbf.studyandtest.R;
import com.dbf.studyandtest.databinding.ActivityEventDispatchBinding;
import com.dbf.studyandtest.eventdispatch.exemple.EventConflictExempleActivity;

public class EventDispatchActivity extends AppCompatActivity {
    private final String TAG = EventDispatchActivity.class.getCanonicalName();
    private ActivityEventDispatchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventDispatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.lvInVpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventDispatchActivity.this, EventConflictExempleActivity.class));
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG + "事件机制", "dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG + "事件机制", "dispatchTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG + "事件机制", "dispatchTouchEvent: ACTION_UP");
                break;
        }
        boolean s = super.dispatchTouchEvent(ev);
        return s;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG + "事件机制", "onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG + "事件机制", "onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG + "事件机制", "onTouchEvent: ACTION_UP");
                break;
        }
        boolean s = super.onTouchEvent(event);
        return s;
    }
}