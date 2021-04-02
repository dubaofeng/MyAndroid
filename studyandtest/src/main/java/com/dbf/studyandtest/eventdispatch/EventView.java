package com.dbf.studyandtest.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by dbf on 2020/11/17
 * describe:
 */
class EventView extends View {
    private final String TAG = EventView.class.getCanonicalName();

    public EventView(Context context) {
        super(context);
    }

    public EventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.w(TAG + "事件机制", "dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.w(TAG + "事件机制", "dispatchTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.w(TAG + "事件机制", "dispatchTouchEvent: ACTION_UP");
                break;
        } boolean s = super.dispatchTouchEvent(ev);
        return s;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.w(TAG + "事件机制", "onTouchEvent: ACTION_DOWN");
                break;
//                return true;
            case MotionEvent.ACTION_MOVE:
                Log.w(TAG + "事件机制", "onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.w(TAG + "事件机制", "onTouchEvent: ACTION_UP");
                break;
        }  boolean s = super.onTouchEvent(event);
        return s;
    }
}
