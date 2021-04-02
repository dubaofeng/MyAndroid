package com.dbf.studyandtest.custom_view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.dbf.studyandtest.R;

/**
 * Created by dbf on 2021/1/30
 * describe:
 */
public class ShowFrameAnim extends View implements Runnable {
    private static final String TAG = "ShowFrameAnim";
    int width = 100;
    int height = 100;
    long intervalDuration = 10;
    private Bitmap mBitmap;// 显示的图片
    private Rect targetRect;
    int currentPage = -1;
    int[] resours;
    private Handler handler;

    public ShowFrameAnim setResours(int[] resours) {
        this.resours = resours;
        return this;
    }

    public ShowFrameAnim setIntervalDuration(long intervalDuration) {
        this.intervalDuration = intervalDuration;
        return this;
    }

    public ShowFrameAnim setShowOnEnd(boolean showOnEnd) {
        this.showOnEnd = showOnEnd;
        return this;
    }

    private boolean showOnEnd = false;


    public ShowFrameAnim(Context context) {
        this(context, null);
    }

    public ShowFrameAnim(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowFrameAnim(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handler = new Handler(Looper.getMainLooper());
    }

    public void startAnima() {
        if (resours == null || resours.length == 0) {
            return;
        }

        handler.removeCallbacks(this);
        currentPage = 0;
        invalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wSpecMode) {
            //精确模式
            case MeasureSpec.EXACTLY:
                width = wSpecSize;
                break;
            //不超过父布局的限制之下，自己要多少就多少
            case MeasureSpec.AT_MOST:
                //这里个的使父布局的限制大小;根据需要不超过这个大小就行
                width = wSpecSize;
                break;
            //没有限制，随没有限制，系统多测测量的一个状态，最后还是会走确定大小的模式
            case MeasureSpec.UNSPECIFIED:
                //父布局不限制自己，自己给自己一个值
                break;
        }
        switch (hSpecMode) {
            //精确模式
            case MeasureSpec.EXACTLY:
                height = hSpecSize;
                break;
            //不超过父布局的限制之下，自己要多少就多少
            case MeasureSpec.AT_MOST:
                //这里个的使父布局的限制大小;根据需要不超过这个大小就行
                height = hSpecSize;
                break;
            //没有限制，随没有限制，系统多测测量的一个状态，最后还是会走确定大小的模式
            case MeasureSpec.UNSPECIFIED:
                //父布局不限制自己，自己给自己一个值
                break;
        }
        targetRect = new Rect(0, 0, width, height);
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas mCanvas) {
        if (currentPage >= 0 && currentPage < resours.length) {
            try {
                Log.i(TAG, "onDraw: currentPage=" + currentPage);
                //去掉背景色
                mCanvas.drawColor(Color.TRANSPARENT);
                // 如果图片过大可以再次对图片进行二次采样缩放处理
                mBitmap = BitmapFactory.decodeResource(getResources(), resours[currentPage]);
//                mCanvas.drawBitmap(mBitmap, (getWidth() - mBitmap.getWidth()) / 2, 0, null);
                mCanvas.drawBitmap(mBitmap, null, targetRect, null);
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (mBitmap != null) {
                    // 收回图片
                    mBitmap.recycle();
                }
            }
            if (mOnFrameFinishedListener != null) {
                mOnFrameFinishedListener.onFrame(currentPage);
            }
            handler.postDelayed(this, intervalDuration);
        }

    }

    @Override
    public void run() {
        currentPage++;
        if (currentPage < 0) {
            //取消动画，
        }
        if (showOnEnd && currentPage >= resours.length) {
            //动画结束，并且停留在最后一帧，不执行invalidate();防止绘制
            if (mOnFrameFinishedListener != null) {
                mOnFrameFinishedListener.onStop();//回调动画结束方法
            }
            return;
        } else if (currentPage >= resours.length) {
            //动画结束，不停留在最后一帧，执行invalidate()
            if (mOnFrameFinishedListener != null) {
                mOnFrameFinishedListener.onStop();//回调动画结束方法
            }
        }
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 结束动画
     */
    public void cancel() {
        handler.removeCallbacks(this);
        currentPage = -2;
    }

    /**
     * 重新播放动画
     */
    public void reStart() {
        if (resours == null || resours.length == 0) {
            return;
        }
        currentPage = 0;
        handler.removeCallbacks(this);
        invalidate();
    }

    private OnFrameFinishedListener mOnFrameFinishedListener;// 动画监听事件

    /**
     * 设置动画监听器
     */
    public ShowFrameAnim setOnFrameFinisedListener(OnFrameFinishedListener onFrameFinishedListener) {
        this.mOnFrameFinishedListener = onFrameFinishedListener;
        return this;
    }

    /**
     * 动画监听器
     *
     * @author qike
     */
    public interface OnFrameFinishedListener {

        /**
         * 动画开始
         */
        void onStart();

        /**
         * 动画结束
         */
        void onStop();

        void onFrame(int index);
    }

    //头像流光
    public static int[] picRun1 =
            {R.mipmap.xing_00, R.mipmap.xing_01, R.mipmap.xing_02, R.mipmap.xing_03, R.mipmap.xing_04,
                    R.mipmap.xing_05, R.mipmap.xing_06, R.mipmap.xing_07, R.mipmap.xing_08, R.mipmap.xing_09,
                    R.mipmap.xing_10, R.mipmap.xing_11, R.mipmap.xing_12, R.mipmap.xing_13, R.mipmap.xing_14,
                    R.mipmap.xing_15, R.mipmap.xing_16, R.mipmap.xing_17, R.mipmap.xing_18, R.mipmap.xing_19,
                    R.mipmap.xing_20, R.mipmap.xing_21, R.mipmap.xing_22, R.mipmap.xing_23, R.mipmap.xing_23,
                    R.mipmap.xing_25, R.mipmap.xing_26, R.mipmap.xing_27, R.mipmap.xing_28, R.mipmap.xing_29,
                    R.mipmap.xing_30, R.mipmap.xing_31, R.mipmap.xing_32, R.mipmap.xing_33, R.mipmap.xing_34,
                    R.mipmap.xing_35, R.mipmap.xing_36, R.mipmap.xing_37, R.mipmap.xing_38, R.mipmap.xing_39,
                    R.mipmap.xing_40, R.mipmap.xing_41, R.mipmap.xing_42, R.mipmap.xing_43, R.mipmap.xing_44,
                    R.mipmap.xing_45, R.mipmap.xing_46, R.mipmap.xing_47, R.mipmap.xing_48, R.mipmap.xing_49,
                    R.mipmap.xing_50, R.mipmap.xing_51, R.mipmap.xing_52, R.mipmap.xing_53, R.mipmap.xing_54,
                    R.mipmap.xing_55, R.mipmap.xing_56, R.mipmap.xing_57, R.mipmap.xing_58, R.mipmap.xing_59,
                    R.mipmap.xing_60, R.mipmap.xing_61, R.mipmap.xing_62};
    //卡片翻转
    public static int[] picRun2 =
            {R.mipmap.kapian_00050, R.mipmap.kapian_00051, R.mipmap.kapian_00052, R.mipmap.kapian_00053, R.mipmap.kapian_00054, R.mipmap.kapian_00055,
                    R.mipmap.kapian_00056, R.mipmap.kapian_00057, R.mipmap.kapian_00058, R.mipmap.kapian_00059, R.mipmap.kapian_00060,
                    R.mipmap.kapian_00061, R.mipmap.kapian_00062, R.mipmap.kapian_00063, R.mipmap.kapian_00064, R.mipmap.kapian_00065,
                    R.mipmap.kapian_00066, R.mipmap.kapian_00067, R.mipmap.kapian_00068, R.mipmap.kapian_00069, R.mipmap.kapian_00070,
                    R.mipmap.kapian_00071, R.mipmap.kapian_00072, R.mipmap.kapian_00073, R.mipmap.kapian_00074, R.mipmap.kapian_00075,
                    R.mipmap.kapian_00076, R.mipmap.kapian_00077, R.mipmap.kapian_00078, R.mipmap.kapian_00079};
    public static int[] picRun3 = {R.mipmap.guang_00, R.mipmap.guang_01, R.mipmap.guang_02, R.mipmap.guang_03, R.mipmap.guang_04,
            R.mipmap.guang_05, R.mipmap.guang_06, R.mipmap.guang_07, R.mipmap.guang_08, R.mipmap.guang_09,
            R.mipmap.guang_10, R.mipmap.guang_11, R.mipmap.guang_12, R.mipmap.guang_13, R.mipmap.guang_14,
            R.mipmap.guang_15, R.mipmap.guang_16, R.mipmap.guang_17, R.mipmap.guang_18, R.mipmap.guang_19,
            R.mipmap.guang_20, R.mipmap.guang_21, R.mipmap.guang_22, R.mipmap.guang_23, R.mipmap.guang_24,
            R.mipmap.guang_25, R.mipmap.guang_26, R.mipmap.guang_27, R.mipmap.guang_28, R.mipmap.guang_29,
            R.mipmap.guang_30, R.mipmap.guang_31, R.mipmap.guang_32, R.mipmap.guang_33, R.mipmap.guang_34,
            R.mipmap.guang_35, R.mipmap.guang_36, R.mipmap.guang_37, R.mipmap.guang_38, R.mipmap.guang_39,
            R.mipmap.guang_40, R.mipmap.guang_41};
}
