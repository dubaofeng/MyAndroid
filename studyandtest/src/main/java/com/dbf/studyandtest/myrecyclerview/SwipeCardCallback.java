package com.dbf.studyandtest.myrecyclerview;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.dbf.common.myutils.MyLog;


public class SwipeCardCallback extends ItemTouchHelper.SimpleCallback {
    private final String TAG = "SwipeCardCallback";
    private RecyclerView mRv;

    public SwipeCardCallback() {
        //方向
        super(0, 15);//1111
        this.mRv = mRv;
    }

    //drag
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        MyLog.INSTANCE.d(TAG, "onMove");
        return false;
    }

    // item 滑出去后回调
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyLog.INSTANCE.d(TAG, "onSwiped");
    }

    // onDraw
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX,
                            float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        MyLog.INSTANCE.d(TAG, "dX=" + dX + " dY=" + dY + " actionState=" + actionState + " isCurrentlyActive=" + isCurrentlyActive);

    }

    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return 1000;
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.2f;
    }
}
