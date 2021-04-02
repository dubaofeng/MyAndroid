package com.dbf.studyandtest.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * Created by dbf on 2020/12/21
 * describe:
 */
public class MyPracticeCustomLayout extends ViewGroup {
    private final String TAG = MyPracticeCustomLayout.class.getCanonicalName();

    public MyPracticeCustomLayout(Context context) {
        super(context);
    }

    public MyPracticeCustomLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPracticeCustomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //l、t、r、b、是布局的位置，r-l=宽，b-t=高
        Log.i(TAG, "l=" + l + "  t=" + t + "  r=" + r + "  b=" + b);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int w = 0;
        int h = 0;
        switch (wSpecMode) {
            case MeasureSpec.EXACTLY://精确模式
                w = wSpecSize;
                break;
            case MeasureSpec.AT_MOST://不超过父布局
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
    }
}
