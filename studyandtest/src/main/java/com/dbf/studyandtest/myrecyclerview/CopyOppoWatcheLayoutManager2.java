package com.dbf.studyandtest.myrecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CopyOppoWatcheLayoutManager2 extends RecyclerView.LayoutManager {
    private final String TAG = "MyLayout";
    private int itemHSize = -1;
    private int itemWSize = -1;
    private int hCount = 3;
    private int vCount = 3;
    private int screenlayoutCount = 0;
    private int mvOffsetCount = 0;

    private int mFirsItemPosition = 0;
    private int mLastItemPosition = 0;
    private int mCurrentOffset = 0;
    private int totalOffset = 0;
    private int mSetOffset = 0;
    private float minScale = 0.3f;
    private int beforOneLineStartOffset;

    public CopyOppoWatcheLayoutManager2(Context context) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i(TAG, "onLayoutChildren");
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        detachAndScrapAttachedViews(recycler);
        mLastItemPosition = getItemCount() - 1;
        itemWSize = getHorizontalSpace() / hCount;
        mSetOffset = getVerticalSpace() / (4) / 3;
        if (vCount == 0) {
            vCount = getVerticalSpace() / itemWSize;
            itemHSize = itemWSize;
        } else {
            if (itemHSize == -1) {
                itemHSize = (getVerticalSpace() - 2 * mSetOffset) / vCount;
//
            }
        }

        if (screenlayoutCount == 0) {
            screenlayoutCount = hCount * vCount;
        }
        if (mvOffsetCount == 0) {
            mvOffsetCount = (int) Math.ceil((getItemCount() + 0f) / hCount);
            int screenOffsetCount = (screenlayoutCount) / hCount;
            if (mvOffsetCount > screenOffsetCount) {
                mvOffsetCount = mvOffsetCount - screenOffsetCount;
            } else {
                mvOffsetCount = 0;
            }
            totalOffset = mvOffsetCount * itemHSize + itemHSize;
        }

        fill(recycler, state, 0);
        recycleChildren(recycler);
    }

    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state, int dy) {
        detachAndScrapAttachedViews(recycler);

        int leftOffset = getPaddingLeft();
        float itemOffset = (mCurrentOffset + 0f) % itemHSize;
        float frac = itemOffset / itemHSize;
        if (mCurrentOffset >= totalOffset) {
            frac = 1f;
        }
        int scrollY = (int) (itemHSize * frac);
        int viewTopOffset = getPaddingTop() + mSetOffset;
        viewTopOffset -= scrollY;//偏移量

        beforOneLineStartOffset = (int) -(itemHSize / 2 - itemHSize * minScale / 2);
        int lastOffset = (vCount - 1) * itemHSize + mSetOffset;
        int lastEndLineStartOffset = (vCount) * itemHSize + 2 * mSetOffset - (itemHSize + beforOneLineStartOffset);
        int lastScreenLayoutOffset = (vCount - 1) * itemHSize;
        int lastEndLineStartOffset2 = lastEndLineStartOffset;
        int oneLineStartOffset = 0;
        int smallccircleoffset = (int) (itemWSize * (1 - minScale + 0.0f));
        int hc = getHorizontalSpace() / 2 + getPaddingLeft();
        int smallccircledist = Math.abs(mSetOffset - beforOneLineStartOffset);
        int fs = (int) Math.floor(Math.abs(mCurrentOffset) / itemHSize) * hCount;
        if (fs <= getItemCount() - 1) {
            mFirsItemPosition = fs;
        }
        int LastItemPosition = mFirsItemPosition + screenlayoutCount - 1 + hCount;
        if (LastItemPosition > getItemCount() - 1) {
            LastItemPosition = getItemCount() - 1;
        }
        mLastItemPosition = LastItemPosition;
        int buttomItemCount = (mLastItemPosition + 1) % hCount;
        if (buttomItemCount == 0) {
            buttomItemCount = hCount;
        }

        if (mFirsItemPosition >= hCount) {
            //顶部小item
            for (int k = mFirsItemPosition - hCount; k < mFirsItemPosition; k++) {
                boolean noofs = Math.abs(leftOffset + itemWSize / 2 - hc) < itemWSize / 3;
                int ofs = smallccircleoffset;
                if (noofs) {
                    ofs = 0;
                } else if (leftOffset + itemWSize > hc) {
                    ofs = -ofs;
                }
                View beforchild = recycler.getViewForPosition(k);
                addView(beforchild);
                measureChildWithMargins(beforchild, itemWSize * 2, itemHSize * 2);
                beforchild.setScaleX(minScale);
                beforchild.setScaleY(minScale);
                layoutDecoratedWithMargins(beforchild, leftOffset + ofs, beforOneLineStartOffset, leftOffset + itemWSize + ofs, beforOneLineStartOffset + itemHSize);
                leftOffset += itemWSize;
            }
        }


        leftOffset = getPaddingLeft();
        if (mLastItemPosition + hCount < getItemCount()) {
            for (int i = mLastItemPosition + 1; i <= mLastItemPosition + hCount; i++) {
                //画底部小圆，留在底部不跟着上画的小item
                boolean noofs = Math.abs(leftOffset + itemWSize / 2 - hc) < itemWSize / 3;
                int ofs = smallccircleoffset;
                if (noofs) {
                    ofs = 0;
                } else if (leftOffset + itemWSize > hc) {
                    ofs = -ofs;
                }
                View child = recycler.getViewForPosition(i);
                addView(child);
                measureChildWithMargins(child, itemWSize * 2, itemHSize * 2);
                child.setScaleX(minScale);
                child.setScaleY(minScale);
                layoutDecoratedWithMargins(child, leftOffset + ofs, lastEndLineStartOffset2, leftOffset + itemWSize + ofs, lastEndLineStartOffset2 + itemHSize);
                leftOffset += itemWSize;
            }
        } else {
            for (int i = mLastItemPosition + 1; i < getItemCount(); i++) {
                //画底部小圆
                boolean noofs = Math.abs(leftOffset + itemWSize / 2 - hc) < itemWSize / 3;
                int ofs = smallccircleoffset;
                if (noofs) {
                    ofs = 0;
                } else if (leftOffset + itemWSize > hc) {
                    ofs = -ofs;
                }
                View child = recycler.getViewForPosition(i);
                addView(child);
                measureChildWithMargins(child, itemWSize * 2, itemHSize * 2);
                child.setScaleX(minScale);
                child.setScaleY(minScale);
                layoutDecoratedWithMargins(child, leftOffset + ofs, lastEndLineStartOffset2, leftOffset + itemWSize + ofs, lastEndLineStartOffset2 + itemHSize);
                leftOffset += itemWSize;
            }
        }

        if (mCurrentOffset < 0 && mLastItemPosition >= (vCount - 1) * hCount) {
//有最后一行就得干,下滑，从底部开始布局，解决Android4.4没有设置view  Z轴方法
            for (int i = mLastItemPosition; i >= 0; i--) {
                View child = recycler.getViewForPosition(i);
                addView(child);
                measureChildWithMargins(child, itemWSize * 2, itemHSize * 2);
                int iwCount = i % hCount + 1;//有几个宽度
                int l = iwCount * itemWSize + getPaddingLeft() - itemWSize;
                int r = iwCount * itemWSize + getPaddingLeft();
                int ihCount = i / hCount + 0;//在第几行

                if (i >= vCount * hCount) {
                    child.setScaleX(minScale);
                    child.setScaleY(minScale);
                    boolean noofs = Math.abs(l + itemWSize / 2 - hc) < itemWSize / 3;
                    int ofs = smallccircleoffset;
                    if (noofs) {
                        ofs = 0;
                    } else if (l + itemWSize > hc) {
                        ofs = -ofs;
                    }
                    layoutDecoratedWithMargins(child, l + ofs, lastEndLineStartOffset2, r + ofs, lastEndLineStartOffset2 + itemHSize);

                } else if (i >= (vCount - 1) * hCount) {
                    int intercept = lastScreenLayoutOffset + viewTopOffset;
                    if (intercept > lastEndLineStartOffset2) {
                        intercept = lastEndLineStartOffset2;
                    }
                    int ofset = Math.abs(intercept - lastEndLineStartOffset2);
//                Log.i(TAG, "ofset=" + ofset);
                    float ofsetf = (ofset + 0f) / smallccircledist;
//                Log.i(TAG, "ofsetf=" + ofsetf + "   smallccircledist=" + smallccircledist);
                    int ofs = (int) (smallccircleoffset - smallccircleoffset * ofsetf);
//                Log.i(TAG, "ofs=" + ofs);


                    boolean noofs = Math.abs(l + itemWSize / 2 - hc) < itemWSize / 3;
                    if (noofs) {
                        ofs = 0;
                    } else if (l + itemWSize > hc) {
                        ofs = -ofs;
                    }
                    layoutDecoratedWithMargins(child, l + ofs, intercept, r + ofs, intercept + itemHSize);
                    float realScale = 1f - (1f - minScale) * frac;
                    realScale = 1f - (1f - minScale) * (intercept - lastScreenLayoutOffset - mSetOffset) / smallccircledist;
                    if (realScale > 1f) {
                        realScale = 1f;
                    }
                    if (realScale < minScale) {
                        realScale = minScale;
                    }
                    child.setScaleX(realScale);
                    child.setScaleY(realScale);

                } else {
                    child.setScaleX(1f);
                    child.setScaleY(1f);
                    layoutDecoratedWithMargins(child, l, ihCount * itemHSize + viewTopOffset, r, ihCount * itemHSize + viewTopOffset + itemHSize);
                }

            }
            return dy;
        }

        leftOffset = getPaddingLeft();
        for (int i = mFirsItemPosition; i <= mLastItemPosition; i++) {
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, itemWSize * 2, itemHSize * 2);
            if (i - mFirsItemPosition < hCount) {
                //第一行
                float realScale = 1f - (1f - minScale) * frac;
                if (viewTopOffset < mSetOffset) {
                    //计算出滑动到小item的百分比
                    realScale = 1f - (1f - minScale) * (mSetOffset - viewTopOffset) / smallccircledist;
                }
                if (realScale > 1f) {
                    realScale = 1f;
                }
                if (realScale < minScale) {
                    realScale = minScale;
                }
                child.setScaleX(realScale);
                child.setScaleY(realScale);
                oneLineStartOffset = viewTopOffset;

                if (viewTopOffset <= beforOneLineStartOffset) {
                    //上滑到此停住
                    oneLineStartOffset = beforOneLineStartOffset;
                }


                int ofset = oneLineStartOffset - beforOneLineStartOffset;
//                Log.i(TAG, "ofset=" + ofset);
                float ofsetf = (ofset + 0f) / smallccircledist;
//                Log.i(TAG, "ofsetf=" + ofsetf + "   smallccircledist=" + smallccircledist);
                int ofs = (int) (smallccircleoffset - smallccircleoffset * ofsetf);
//                Log.i(TAG, "ofs=" + ofs);


                boolean noofs = Math.abs(leftOffset + itemWSize / 2 - hc) < itemWSize / 3;
                if (noofs) {
                    ofs = 0;
                } else if (leftOffset + itemWSize > hc) {
                    ofs = -ofs;
                }
                layoutDecoratedWithMargins(child, leftOffset + ofs, oneLineStartOffset, leftOffset + itemWSize + ofs, oneLineStartOffset + itemHSize);
                leftOffset += itemWSize;
            } else if (i > mLastItemPosition - buttomItemCount && i >= screenlayoutCount) {
                //画底部小圆，下滑时秒变正常大小item的最后一行，要注意
                float realScale = minScale + (1f - minScale) * frac;
                if (viewTopOffset < lastOffset) {
                    //计算出滑动到小item的百分比
                    realScale = minScale + (1f - minScale) * (lastOffset - viewTopOffset + beforOneLineStartOffset) / smallccircledist;
                }
                if (realScale > 1f) {
                    realScale = 1f;
                }
                if (realScale < minScale) {
                    realScale = minScale;
                }
                child.setScaleX(realScale);
                child.setScaleY(realScale);

                lastEndLineStartOffset = lastEndLineStartOffset2;//固定住底部小item
                if (viewTopOffset <= lastEndLineStartOffset2 - itemHSize) {//上滑时跟随上滑
                    lastEndLineStartOffset = viewTopOffset + itemHSize;
                }
                if (lastEndLineStartOffset > lastEndLineStartOffset2) {//下滑时固定
                    lastEndLineStartOffset = lastEndLineStartOffset2;
                }
                int ofset = (lastEndLineStartOffset2 - lastEndLineStartOffset);
                float ofsetf = (ofset + 0f) / smallccircledist;
                if (leftOffset + itemWSize <= getHorizontalSpace() + getPaddingLeft()) { //当前行还排列的下
                    int ofs = (int) (smallccircleoffset - smallccircleoffset * ofsetf);
                    boolean noofs = Math.abs(leftOffset + itemWSize / 2 - hc) < itemWSize / 3;
                    if (noofs) {
                        ofs = 0;
                    } else if (leftOffset + itemWSize > hc) {
                        ofs = -ofs;
                    }

                    if (mCurrentOffset > totalOffset - itemHSize - 1) {//最后一行正常大小显示
                        lastEndLineStartOffset = viewTopOffset + itemHSize;
                        child.setScaleX(1f);
                        child.setScaleY(1f);
                        ofs = 0;
                    }


                    layoutDecoratedWithMargins(child, leftOffset + ofs, lastEndLineStartOffset, leftOffset + itemWSize + ofs, lastEndLineStartOffset + itemHSize);
                    leftOffset += itemWSize;
                } else {//换行布局
                    leftOffset = getPaddingLeft();
                    lastEndLineStartOffset = lastEndLineStartOffset2;
                    if (viewTopOffset <= lastEndLineStartOffset2 - itemHSize) {
                        lastEndLineStartOffset = viewTopOffset + itemHSize;
                    }
                    if (lastEndLineStartOffset > lastEndLineStartOffset2) {
                        lastEndLineStartOffset = lastEndLineStartOffset2;
                    }
                    int ofs = (int) (smallccircleoffset - smallccircleoffset * ofsetf);
                    boolean noofs = Math.abs(leftOffset + itemWSize / 2 - hc) < itemWSize / 3;
                    if (noofs) {
                        ofs = 0;
                    } else if (leftOffset + itemWSize > hc) {
                        ofs = -ofs;
                    }
                    if (mCurrentOffset > totalOffset - itemHSize - 1) {
                        lastEndLineStartOffset = viewTopOffset + itemHSize;
                        child.setScaleX(1f);
                        child.setScaleY(1f);
                        ofs = 0;
                    }

                    layoutDecoratedWithMargins(child, leftOffset + ofs, lastEndLineStartOffset, leftOffset + itemWSize + ofs, lastEndLineStartOffset + itemHSize);
                    leftOffset += itemWSize;
                }

            } else { //正常大小item的第二行到倒数第二行
                lastScreenLayoutOffset = viewTopOffset;
                child.setScaleX(1f);//设置正常缩放大小，如过不设置，会出现小的item在不正确的位置
                child.setScaleY(1f);
                if (mCurrentOffset < 0) {//控制最开始的第一行下滑最多一个item的高度
                    if (viewTopOffset > lastOffset) {
                        lastScreenLayoutOffset = viewTopOffset;
                        if (viewTopOffset >= lastEndLineStartOffset2) {
                            lastScreenLayoutOffset = lastEndLineStartOffset2;
                        }
                        float realScale = 1f - (1f - minScale) * frac;
                        realScale = 1f - (1f - minScale) * (viewTopOffset - lastOffset) / smallccircledist;
                        if (realScale > 1f) {
                            realScale = 1f;
                        }
                        if (realScale < minScale) {
                            realScale = minScale;
                        }
                        child.setScaleX(realScale);
                        child.setScaleY(realScale);
                    }

                }

                if (leftOffset + itemWSize <= getHorizontalSpace() + getPaddingLeft()) { //当前行还排列的下
                    layoutDecoratedWithMargins(child, leftOffset, lastScreenLayoutOffset, leftOffset + itemWSize, lastScreenLayoutOffset + itemHSize);
                    leftOffset += itemWSize;
                } else {
                    leftOffset = getPaddingLeft();
                    viewTopOffset += itemHSize;
                    lastScreenLayoutOffset = viewTopOffset;
                    if (mCurrentOffset < 0) {
                        if (viewTopOffset > lastOffset) {
                            lastScreenLayoutOffset = viewTopOffset;
                            if (viewTopOffset >= lastEndLineStartOffset2) {
                                lastScreenLayoutOffset = lastEndLineStartOffset2;
                            }
                            float realScale = 1f - (1f - minScale) * frac;
                            realScale = 1f - (1f - minScale) * (viewTopOffset - lastOffset) / smallccircledist;
                            if (realScale > 1f) {
                                realScale = 1f;
                            }
                            if (realScale < minScale) {
                                realScale = minScale;
                            }
                            child.setScaleX(realScale);
                            child.setScaleY(realScale);
                        }

                    }
                    layoutDecoratedWithMargins(child, leftOffset, lastScreenLayoutOffset, leftOffset + itemWSize, lastScreenLayoutOffset + itemHSize);
                    leftOffset += itemWSize;
                }
            }


        }
        return dy;
    }


    /**
     * 回收屏幕外需回收的Item
     */
    private void recycleChildren(RecyclerView.Recycler recycler) {
        List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
        for (int i = 0; i < scrapList.size(); i++) {
            RecyclerView.ViewHolder holder = scrapList.get(i);
            removeView(holder.itemView);
            recycler.recycleView(holder.itemView);
        }
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dy == 0 || getChildCount() == 0) {
            return 0;
        }
        int realOffset = dy;//实际滑动的距离
        if (realOffset < 0) {//上边界

        } else if (realOffset > 0) {//下边界

        }

        mCurrentOffset += realOffset;//累加实际滑动距离
        if (mCurrentOffset <= -itemHSize) {
            realOffset = 0;
            mCurrentOffset = (int) -itemHSize + 1;
        }
        if (mCurrentOffset >= totalOffset) {
            mCurrentOffset = totalOffset - 1;
            realOffset = 0;
        } else {
        }
        fill(recycler, state, realOffset);
// offsetChildrenVertical(-realOffset);//滑动
        return realOffset;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        switch (state) {
            case RecyclerView.SCROLL_STATE_DRAGGING://手指触摸屏幕
                cancelAnmationtor();
                break;
            case RecyclerView.SCROLL_STATE_IDLE://列表停止滚动后
                smoothScrollToPosition(findShouldSelectPosition());
                break;
            default:
                break;
        }
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        smoothScrollToPosition(position);
    }

    @Override
    public void scrollToPosition(int position) {
        mCurrentOffset += getScrollToPositionOffset(position);
        if (mCurrentOffset >= totalOffset) {
            mCurrentOffset = totalOffset - 1;
        }
        requestLayout();
    }

    private int getScrollToPositionOffset(int position) {
        position = (int) Math.ceil((position + 0f) / hCount);
        return position * itemHSize - Math.abs(mCurrentOffset);
    }

    private int findShouldSelectPosition() {
        if (itemHSize == 0) {
            return -1;
        }
        int remainder = -1;
        remainder = Math.abs(mCurrentOffset) % itemHSize;
        if (remainder > itemHSize / 2.0f) {
//下一项
            if (mFirsItemPosition + hCount <= getItemCount() - 1) {
                return mFirsItemPosition + hCount;
            }
        }

        return mFirsItemPosition;
    }

    private void smoothScrollToPosition(int position) {
        if (position >= 0 && position < getItemCount()) {
            startValueAnimation(position);
        }
    }

    /**
     * 自动选中动画
     */
    private ValueAnimator selectAnimator;
    private long autoSelectMinDuration = 100;
    private long autoSelectMaxDuration = 300;
    private float distance;

    private void startValueAnimation(int position) {
        cancelAnmationtor();
        Log.i("startValueAnimation", "position=" + position);
        distance = getScrollToPositionOffset(position);
        if (mCurrentOffset < 0) {
            distance = mCurrentOffset;
        } else if (mCurrentOffset > totalOffset - itemHSize) {
            distance = -(mCurrentOffset - (totalOffset - itemHSize));
        }
        long minDuration = autoSelectMinDuration;
        long maxDuration = autoSelectMaxDuration;
        long duration;
        float distanceFraction = Math.abs(distance) / itemHSize;
        if (distance <= itemHSize) {
            duration = (long) (minDuration + (maxDuration - minDuration) * distanceFraction);
        } else {
            duration = (long) (maxDuration * distanceFraction);
        }
        selectAnimator = ValueAnimator.ofFloat(0.0f, distance).setDuration(duration);
        selectAnimator.setInterpolator(new LinearInterpolator());
        final float anstartOffset = mCurrentOffset;
        selectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (mCurrentOffset < 0) {
                    mCurrentOffset = (int) Math.floor(anstartOffset - (float) animation.getAnimatedValue());
                    if (mCurrentOffset >= totalOffset) {
                        mCurrentOffset = totalOffset - 1;
                    }
                } else {
                    mCurrentOffset = (int) Math.ceil(anstartOffset + (float) animation.getAnimatedValue());
                    if (mCurrentOffset >= totalOffset) {
                        mCurrentOffset = totalOffset - 1;
                    }
                }
                requestLayout();
            }
        });
        selectAnimator.start();
    }

    public void cancelAnmationtor() {
        if (selectAnimator != null && (selectAnimator.isStarted() || selectAnimator.isRunning())) {
            selectAnimator.cancel();
        }
    }

    /*
     * 获取某个childView在水平方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementHorizontal(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin
                + params.rightMargin;
    }

    /**
     * 获取某个childView在竖直方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin
                + params.bottomMargin;
    }

    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}