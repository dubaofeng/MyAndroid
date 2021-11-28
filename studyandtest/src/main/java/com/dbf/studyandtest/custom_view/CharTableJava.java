package com.dbf.studyandtest.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;


/**
 * Created by dbf on 2021/11/7
 * describe:
 *
 * @author raden
 */
public class CharTableJava extends View {
    private final String TAG = CharTableJava.class.getCanonicalName();
    private Paint paint;
    /**
     * 折线点路径
     */
    private Path polylinePointPath = new Path();

    public CharTableJava(Context context) {
        this(context, null);
    }

    public CharTableJava(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CharTableJava(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    Shader shader;

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(52);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        //第1、2参数是渐变起点坐标，2、3参数是渐变终点坐标，4参数是沿着渐变线分布的 sRGB 颜色,5可能为空。颜色数组中每种对应颜色的相对位置 [0..1]。如果为 null，则颜色沿渐变线均匀分布,6着色器平铺模式
        shader = new LinearGradient(0, 0, 0, 500, new int[]{Color.GREEN, Color.TRANSPARENT}, null, Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }

    private int width;
    private int height;

    private int count = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        count++;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "onMeasure: start");
//        Log.i(TAG, "onMeasure: widthMode   =" + widthMode);
//        Log.i(TAG, "onMeasure: heightMode  =" + heightMode);
        Log.i(TAG, "onMeasure: widthSize   =" + widthSize);
        Log.i(TAG, "onMeasure: heightSize  =" + heightSize);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                Log.i(TAG, "onMeasure: EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                width = widthSize;
                Log.i(TAG, "onMeasure: AT_MOST");
                break;
            case MeasureSpec.UNSPECIFIED:
                width = widthSize;
                Log.i(TAG, "onMeasure: UNSPECIFIED");
                break;
            default:
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                Log.i(TAG, "onMeasure: EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                height = heightSize;
                Log.i(TAG, "onMeasure: AT_MOST");
                break;
            case MeasureSpec.UNSPECIFIED:
                height = heightSize;
                Log.i(TAG, "onMeasure: UNSPECIFIED");
                break;
            default:
                break;
        }
        Log.i(TAG, "onMeasure: end_count=" + count);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        canvas.drawText("你好！", 50, 50, paint);
        polylinePointPath.moveTo(50, 500);
        polylinePointPath.lineTo(100, 100);
        polylinePointPath.lineTo(150, 300);
        polylinePointPath.lineTo(200, 100);
        polylinePointPath.lineTo(250, 300);
        polylinePointPath.lineTo(300, 100);
        polylinePointPath.lineTo(350, 300);
        polylinePointPath.lineTo(400, 100);
        polylinePointPath.lineTo(450, 500);
//        polylinePointPath.lineTo(500, 100);
        polylinePointPath.close();
        canvas.drawPath(polylinePointPath, paint);
    }
}
