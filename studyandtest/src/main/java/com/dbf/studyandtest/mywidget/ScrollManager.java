package com.dbf.studyandtest.mywidget;

import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import androidx.recyclerview.widget.RecyclerView;

class ScrollManager {
    private static final int ONE_SEC_IN_MS = 1000;
    private static final float VELOCITY_MULTIPLIER = 1.5F;
    private static final float FLING_EDGE_RATIO = 1.5F;
    private float mMinRadiusFraction = 0.0F;
    private float mMinRadiusFractionSquared;
    private float mScrollDegreesPerScreen;
    private float mScrollRadiansPerScreen;
    private float mScreenRadiusPx;
    private float mScreenRadiusPxSquared;
    private float mScrollPixelsPerRadian;
    private boolean mDown;
    private boolean mScrolling;
    private float mLastAngleRadians;
    private RecyclerView mRecyclerView;
    VelocityTracker mVelocityTracker;

    ScrollManager() {
        this.mMinRadiusFractionSquared = this.mMinRadiusFraction * this.mMinRadiusFraction;
        this.mScrollDegreesPerScreen = 180.0F;
        this.mScrollRadiansPerScreen = (float) Math.toRadians((double) this.mScrollDegreesPerScreen);
    }

    void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        Point displaySize = new Point();
        Display display = this.mRecyclerView.getDisplay();
        display.getSize(displaySize);
        this.mScreenRadiusPx = (float) Math.max(displaySize.x, displaySize.y) / 2.0F;
        this.mScreenRadiusPxSquared = this.mScreenRadiusPx * this.mScreenRadiusPx;
        this.mScrollPixelsPerRadian = (float) displaySize.y / this.mScrollRadiansPerScreen;
        this.mVelocityTracker = VelocityTracker.obtain();
    }

    void clearRecyclerView() {
        this.mRecyclerView = null;
    }

    boolean onTouchEvent(MotionEvent event) {
        float deltaX = event.getRawX() - this.mScreenRadiusPx;
        float deltaY = event.getRawY() - this.mScreenRadiusPx;
        float radiusSquared = deltaX * deltaX + deltaY * deltaY;
        MotionEvent vtev = MotionEvent.obtain(event);
        this.mVelocityTracker.addMovement(vtev);
        vtev.recycle();
        switch (event.getActionMasked()) {
            case 0:
                if (radiusSquared / this.mScreenRadiusPxSquared > this.mMinRadiusFractionSquared) {
                    this.mDown = true;
                    return true;
                }
                break;
            case 1:
                this.mDown = false;
                this.mScrolling = false;
                this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mRecyclerView.getMaxFlingVelocity());
                int velocityY = (int) this.mVelocityTracker.getYVelocity();
                if (event.getX() < 1.5F * this.mScreenRadiusPx) {
                    velocityY = -velocityY;
                }

                this.mVelocityTracker.clear();
                if (Math.abs(velocityY) > this.mRecyclerView.getMinFlingVelocity()) {
                    return this.mRecyclerView.fling(0, (int) (1.5F * (float) velocityY));
                }
                break;
            case 2:
                float deltaXFromCenter;
                float deltaYFromCenter;
                if (this.mScrolling) {
                    deltaXFromCenter = (float) Math.atan2((double) deltaY, (double) deltaX);
                    deltaYFromCenter = deltaXFromCenter - this.mLastAngleRadians;
                    deltaYFromCenter = normalizeAngleRadians(deltaYFromCenter);
                    int scrollPixels = Math.round(deltaYFromCenter * this.mScrollPixelsPerRadian);
                    if (scrollPixels != 0) {
                        this.mRecyclerView.scrollBy(0, scrollPixels);
                        deltaYFromCenter = (float) scrollPixels / this.mScrollPixelsPerRadian;
                        this.mLastAngleRadians += deltaYFromCenter;
                        this.mLastAngleRadians = normalizeAngleRadians(this.mLastAngleRadians);
                    }

                    return true;
                }

                if (this.mDown) {
                    deltaXFromCenter = event.getRawX() - this.mScreenRadiusPx;
                    deltaYFromCenter = event.getRawY() - this.mScreenRadiusPx;
                    float distFromCenter = (float) Math.hypot((double) deltaXFromCenter, (double) deltaYFromCenter);
                    deltaXFromCenter /= distFromCenter;
                    deltaYFromCenter /= distFromCenter;
                    this.mScrolling = true;
                    this.mRecyclerView.invalidate();
                    this.mLastAngleRadians = (float) Math.atan2((double) deltaYFromCenter, (double) deltaXFromCenter);
                    return true;
                }

                if (radiusSquared / this.mScreenRadiusPxSquared > this.mMinRadiusFractionSquared) {
                    this.mDown = true;
                    return true;
                }
                break;
            case 3:
                if (this.mDown) {
                    this.mDown = false;
                    this.mScrolling = false;
                    this.mRecyclerView.invalidate();
                    return true;
                }
        }

        return false;
    }

    private static float normalizeAngleRadians(float angleRadians) {
        if ((double) angleRadians < -3.141592653589793D) {
            angleRadians = (float) ((double) angleRadians + 6.283185307179586D);
        }

        if ((double) angleRadians > 3.141592653589793D) {
            angleRadians = (float) ((double) angleRadians - 6.283185307179586D);
        }

        return angleRadians;
    }

    public void setScrollDegreesPerScreen(float degreesPerScreen) {
        this.mScrollDegreesPerScreen = degreesPerScreen;
        this.mScrollRadiansPerScreen = (float) Math.toRadians((double) this.mScrollDegreesPerScreen);
    }

    public void setBezelWidth(float fraction) {
        this.mMinRadiusFraction = 1.0F - fraction;
        this.mMinRadiusFractionSquared = this.mMinRadiusFraction * this.mMinRadiusFraction;
    }

    public float getScrollDegreesPerScreen() {
        return this.mScrollDegreesPerScreen;
    }

    public float getBezelWidth() {
        return 1.0F - this.mMinRadiusFraction;
    }
}
