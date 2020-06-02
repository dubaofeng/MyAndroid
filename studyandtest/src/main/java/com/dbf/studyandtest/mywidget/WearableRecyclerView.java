package com.dbf.studyandtest.mywidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbf.studyandtest.R;


public class WearableRecyclerView extends RecyclerView {
    private static final String TAG = WearableRecyclerView.class.getSimpleName();
    private static final int NO_VALUE = -2147483648;
    private final ScrollManager mScrollManager;
    private WearableRecyclerView.OffsettingHelper mOffsettingHelper;
    private boolean mCircularScrollingEnabled;
    private boolean mCenterEdgeItems;
    private boolean mCenterEdgeItemsWhenThereAreChildren;
    private int mOriginalPaddingTop;
    private int mOriginalPaddingBottom;
    private final ViewTreeObserver.OnPreDrawListener mPaddingPreDrawListener;

    public WearableRecyclerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public WearableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mScrollManager = new ScrollManager();
        this.mOriginalPaddingTop = -2147483648;
        this.mOriginalPaddingBottom = -2147483648;
        this.mPaddingPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (mCenterEdgeItemsWhenThereAreChildren && getChildCount() > 0) {
                    setupCenteredPadding();
                    mCenterEdgeItemsWhenThereAreChildren = false;
                }

                return true;
            }
        };
        this.setHasFixedSize(true);
        this.setClipToPadding(false);
        if (attrs != null) {
            int defStyleRes = 0;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WearableRecyclerView, defStyle, defStyleRes);
            this.setCircularScrollingGestureEnabled(a.getBoolean(R.styleable.WearableRecyclerView_circular_scrolling_gesture_enabled, this.mCircularScrollingEnabled));
            this.setBezelWidth(a.getFloat(R.styleable.WearableRecyclerView_bezel_width, this.mScrollManager.getBezelWidth()));
            this.setScrollDegreesPerScreen(a.getFloat(R.styleable.WearableRecyclerView_scroll_degrees_per_screen, this.mScrollManager.getScrollDegreesPerScreen()));
            a.recycle();
        }

        this.setLayoutManager(new WearableRecyclerView.OffsettingLinearLayoutManager(this.getContext()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.mCircularScrollingEnabled && this.mScrollManager.onTouchEvent(event) ? true : super.onTouchEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent ev) {
        LayoutManager layoutManager = this.getLayoutManager();
        if (layoutManager == null) {
            return false;
        } else if (this.isLayoutFrozen()) {
            return false;
        } else {
            return super.onGenericMotionEvent(ev);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mScrollManager.setRecyclerView(this);
        this.getViewTreeObserver().addOnPreDrawListener(this.mPaddingPreDrawListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mScrollManager.clearRecyclerView();
        this.getViewTreeObserver().removeOnPreDrawListener(this.mPaddingPreDrawListener);
    }

    public void setScrollDegreesPerScreen(float degreesPerScreen) {
        this.mScrollManager.setScrollDegreesPerScreen(degreesPerScreen);
    }

    public float getScrollDegreesPerScreen() {
        return this.mScrollManager.getScrollDegreesPerScreen();
    }

    public void setBezelWidth(float fraction) {
        this.mScrollManager.setBezelWidth(fraction);
    }

    public float getBezelWidth() {
        return this.mScrollManager.getBezelWidth();
    }

    public void setCircularScrollingGestureEnabled(boolean circularScrollingGestureEnabled) {
        this.mCircularScrollingEnabled = circularScrollingGestureEnabled;
    }

    public boolean isCircularScrollingGestureEnabled() {
        return this.mCircularScrollingEnabled;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setOffsettingHelper(@Nullable WearableRecyclerView.OffsettingHelper offsettingHelper) {
        this.mOffsettingHelper = offsettingHelper;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void clearOffsettingHelper() {
        this.setOffsettingHelper(null);
    }

    /**
     * @deprecated
     */
    @Deprecated
    @Nullable
    public WearableRecyclerView.OffsettingHelper getOffsettingHelper() {
        return this.mOffsettingHelper;
    }

    public void setCenterEdgeItems(boolean centerEdgeItems) {
        this.mCenterEdgeItems = centerEdgeItems;
        if (this.mCenterEdgeItems) {
            if (this.getChildCount() > 0) {
                this.setupCenteredPadding();
            } else {
                this.mCenterEdgeItemsWhenThereAreChildren = true;
            }
        } else {
            this.setupOriginalPadding();
            this.mCenterEdgeItemsWhenThereAreChildren = false;
        }

    }

    public boolean getCenterEdgeItems() {
        return this.mCenterEdgeItems;
    }

    private void setupCenteredPadding() {
        if (this.mCenterEdgeItems && this.getChildCount() >= 1) {
            View child = this.getChildAt(0);
            int height = child.getHeight();
            int desiredPadding = (int) ((float) this.getHeight() * 0.5F - (float) height * 0.5F);
            if (this.getPaddingTop() != desiredPadding) {
                this.mOriginalPaddingTop = this.getPaddingTop();
                this.mOriginalPaddingBottom = this.getPaddingBottom();
                this.setPadding(this.getPaddingLeft(), desiredPadding, this.getPaddingRight(), desiredPadding);
                View focusedChild = this.getFocusedChild();
                int focusedPosition = focusedChild != null ? this.getLayoutManager().getPosition(focusedChild) : 0;
                this.getLayoutManager().scrollToPosition(focusedPosition);
            }

        } else {
            Log.w(TAG, "No children available");
        }
    }

    private void setupOriginalPadding() {
        if (this.mOriginalPaddingTop != -2147483648) {
            this.setPadding(this.getPaddingLeft(), this.mOriginalPaddingTop, this.getPaddingRight(), this.mOriginalPaddingBottom);
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    private final class OffsettingLinearLayoutManager extends LinearLayoutManager {
        public OffsettingLinearLayoutManager(Context context) {
            super(context, 1, false);
        }

        @Override
        public int scrollVerticallyBy(int dy, Recycler recycler, State state) {
            int scrolled = super.scrollVerticallyBy(dy, recycler, state);
            this.updateLayout();
            return scrolled;
        }

        @Override
        public void onLayoutChildren(Recycler recycler, State state) {
            super.onLayoutChildren(recycler, state);
            if (this.getChildCount() != 0) {
                this.updateLayout();
            }
        }

        private void updateLayout() {
            if (mOffsettingHelper != null) {
                for (int count = 0; count < this.getChildCount(); ++count) {
                    View child = this.getChildAt(count);
                    mOffsettingHelper.updateChild(child, WearableRecyclerView.this);
                }
            }

        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public abstract static class OffsettingHelper {
        public OffsettingHelper() {
        }

        public abstract void updateChild(View child, WearableRecyclerView parent);
    }

    public abstract static class ChildLayoutManager extends LinearLayoutManager {
        public ChildLayoutManager(Context context) {
            super(context, 1, false);
        }

        @Override
        public int scrollVerticallyBy(int dy, Recycler recycler, State state) {
            int scrolled = super.scrollVerticallyBy(dy, recycler, state);
            this.updateLayout();
            return scrolled;
        }

        @Override
        public void onLayoutChildren(Recycler recycler, State state) {
            super.onLayoutChildren(recycler, state);
            if (this.getChildCount() != 0) {
                this.updateLayout();
            }
        }

        private void updateLayout() {
            for (int count = 0; count < this.getChildCount(); ++count) {
                View child = this.getChildAt(count);
                this.updateChild(child, (WearableRecyclerView) child.getParent());
            }

        }

        public abstract void updateChild(View child, WearableRecyclerView parent);
    }

}