package com.dbf.studyandtest.myrecyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleScreen extends View {
    public CircleScreen(Context context) {
        this(context, null);
    }

    public CircleScreen(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleScreen(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private Paint paint;

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getHeight() / 2, getHeight() / 2, getHeight() / 2, paint);

    }
}
