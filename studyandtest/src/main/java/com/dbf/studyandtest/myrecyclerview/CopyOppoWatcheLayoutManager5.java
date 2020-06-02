package com.dbf.studyandtest.myrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CopyOppoWatcheLayoutManager5 extends GridLayoutManager {
    public CopyOppoWatcheLayoutManager5(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CopyOppoWatcheLayoutManager5(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CopyOppoWatcheLayoutManager5(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        upDate();
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int y = super.scrollVerticallyBy(dy, recycler, state);
        upDate();

        return y;
    }

    StringBuilder stringBuilder = new StringBuilder();

    private void upDate() {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView != null) {
                int l = childView.getLeft();
                int t = childView.getTop();
                int r = childView.getRight();
                int b = childView.getBottom();
                int w = childView.getWidth();
                int h = childView.getHeight();
                float x = childView.getX();
                float px = childView.getPivotX();
                float rx = childView.getRotationX();
                float tx = childView.getTranslationX();
                float y = childView.getY();
                float py = childView.getPivotY();
                float ry = childView.getRotationY();
                float ty = childView.getTranslationY();
                int prw = getWidth();
                int prh = getHeight();
                int sph = getHeight() - getPaddingTop() - getPaddingBottom();


                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(i);
                stringBuilder.append(" l=").append(formatString(String.valueOf(l), 4, " "));
                stringBuilder.append(" t=").append(formatString(String.valueOf(t), 4, " "));
                stringBuilder.append(" r=").append(formatString(String.valueOf(r), 4, " "));
                stringBuilder.append(" b=").append(formatString(String.valueOf(b), 4, " "));
                stringBuilder.append(" w=").append(formatString(String.valueOf(w), 4, " "));
                stringBuilder.append(" h=").append(formatString(String.valueOf(h), 4, " "));
                stringBuilder.append(" x=").append(formatString(String.valueOf(x), 4, " "));
                stringBuilder.append(" px=").append(formatString(String.valueOf(px), 4, " "));
                stringBuilder.append(" rx=").append(formatString(String.valueOf(rx), 4, " "));
                stringBuilder.append(" tx=").append(formatString(String.valueOf(tx), 4, " "));
                stringBuilder.append(" y=").append(formatString(String.valueOf(y), 4, " "));
                stringBuilder.append(" py=").append(formatString(String.valueOf(py), 4, " "));
                stringBuilder.append(" ry=").append(formatString(String.valueOf(ry), 4, " "));
                stringBuilder.append(" ty=").append(formatString(String.valueOf(ty), 4, " "));
                stringBuilder.append(" fw=").append(formatString(String.valueOf(prw), 4, " "));
                stringBuilder.append(" fh=").append(formatString(String.valueOf(prh), 4, " "));
                stringBuilder.append(" sph=").append(formatString(String.valueOf(sph), 4, " "));

                Log.i("DBFdebug", stringBuilder.toString());
                float itemCenterY = y + py;
                float itemCenterX = x + px;
                int layoutYcenter = prh / 2;
                float f = (layoutYcenter - Math.abs(layoutYcenter - itemCenterY)) / layoutYcenter;
                f = 0.5f + 0.5f * f;
                childView.setScaleX(f);
                childView.setScaleY(f);

                float centerXdist = (float) Math.sqrt(Math.pow(layoutYcenter, 2) - Math.pow(Math.abs(layoutYcenter - itemCenterY), 2));
                int llx = (int) (layoutYcenter - centerXdist);//圆左x坐标
                int rrx = (int) (layoutYcenter + centerXdist);//圆右x坐标
//                int toScreenDist = 0;
//
//                if (centerXdist > w / 2) {
//                    if (itemCenterX - llx < w / 2) {
//                        toScreenDist = (int) (llx - (itemCenterX - px));
//                        childView.offsetLeftAndRight(toScreenDist);
//                    }
//                    if (rrx - itemCenterX < w / 2) {
//                        toScreenDist = (int) (rrx - (itemCenterX + px));
//                        childView.offsetLeftAndRight(toScreenDist);
//                    }
//
//                }

                requestLayout();
            }
        }

    }

    public static String formatString(String str, int length, String slot) {
        StringBuffer sb = new StringBuffer();
        sb.append(str);

        int count = 6 - str.length();

        while (count > 0) {
            sb.append(slot);
            count--;
        }

        return sb.toString();
    }

}
