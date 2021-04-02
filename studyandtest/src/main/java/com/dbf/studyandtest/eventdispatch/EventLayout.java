package com.dbf.studyandtest.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Created by dbf on 2020/11/17
 * describe:
 */
class EventLayout extends LinearLayout {
    private final String TAG = EventLayout.class.getCanonicalName();

    public EventLayout(Context context) {
        super(context);
    }

    public EventLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG + "事件机制", "dispatchTouchEvent: ACTION_DOWN");
//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG + "事件机制", "dispatchTouchEvent: ACTION_MOVE");
                return  true;
//                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG + "事件机制", "dispatchTouchEvent: ACTION_UP");
                break;
//                return true;
        } boolean s = super.dispatchTouchEvent(ev);
        return s;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG + "事件机制", "onInterceptTouchEvent: ACTION_DOWN");
                return true;
//                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG + "事件机制", "onInterceptTouchEvent: ACTION_MOVE");
                break;
//                return true;
            case MotionEvent.ACTION_UP:
                Log.i(TAG + "事件机制", "onInterceptTouchEvent: ACTION_UP");
//                return true;
                break;
        }boolean s = super.onTouchEvent(ev);
        return s;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG + "事件机制", "onTouchEvent: ACTION_DOWN");
//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG + "事件机制", "onTouchEvent: ACTION_MOVE");
//                return true;
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG + "事件机制", "onTouchEvent: ACTION_UP");
//                return true;
                break;
        }  boolean s = super.onTouchEvent(event);
        return s;
    }
}
