package com.dbf.studyandtest.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by dbf on 2020/12/21
 * describe:
 */
public class MyPracticeCustomView extends View {
    public MyPracticeCustomView(Context context) {
        super(context);
    }

    public MyPracticeCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPracticeCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            //精确模式
            case MeasureSpec.EXACTLY:
                w = wSpecSize;
                break;
            //不超过父布局的限制之下，自己要多少就多少
            case MeasureSpec.AT_MOST:
                //这里个的使父布局的限制大小;根据需要不超过这个大小就行
                w = wSpecSize;
                break;
            //没有限制，随没有限制，系统多测测量的一个状态，最后还是会走确定大小的模式
            case MeasureSpec.UNSPECIFIED:
                //父布局不限制自己，自己给自己一个值
                w = 100;
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        canvas.save();
        canvas.restore();
        canvas.restoreToCount(0);
    }
}
