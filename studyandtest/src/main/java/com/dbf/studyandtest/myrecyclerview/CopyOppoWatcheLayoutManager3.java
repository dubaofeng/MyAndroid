package com.dbf.studyandtest.myrecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CopyOppoWatcheLayoutManager3 extends RecyclerView.LayoutManager {
    private final String TAG = "MyLayout";
    private int itemHSize = -1;
    private int itemWSize = -1;
    private int hCount = 3;
    private int vCount = 3;
    //屏幕可见的正常大小item个数
    private int screenItemCount = 9;
    //除去复用显示所有item需要的行数，用于计算垂直方向的总偏移
    private int mvOffsetCount = 0;

    private int mFirsItemPosition = 0;
    private int mLastItemPosition = 0;
    private int mCurrentOffset = 0;
    private int totalOffset = 0; //垂直方向总偏移量
    private int mSetOffset = 0;//竖直方向上添加的偏移
    private float minScale = 0.3f;
    private int smallCircleTopToScreenOffset; //小圆竖直方向距离屏幕边缘的位置
    private int smallCircleHorizontalOffset;//小圆水平方向上的偏移
    private int withSmallCircleDist;//大圆与小圆的距离

    private int bottomSmallCircleTopOffset;//底部小圆的top
    private int hc;//水平方向中间位置

    public CopyOppoWatcheLayoutManager3(Context context) {
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
        mSetOffset = getVerticalSpace() / (vCount + 1) / 3;
        itemWSize = getHorizontalSpace() / hCount;
        if (itemHSize == -1) {
            itemHSize = (getVerticalSpace() - 2 * mSetOffset) / vCount;
//
        }
        smallCircleTopToScreenOffset = (int) -(itemHSize / 2 - itemHSize * minScale / 2);
        bottomSmallCircleTopOffset = getVerticalSpace() - (itemHSize + smallCircleTopToScreenOffset);
        withSmallCircleDist = Math.abs(mSetOffset - smallCircleTopToScreenOffset);
        smallCircleHorizontalOffset = (int) (itemWSize * (1 - minScale + 0.0f));
        hc = getHorizontalSpace() / 2 + getPaddingLeft();
        if (mvOffsetCount == 0) {
            mvOffsetCount = (int) Math.ceil((getItemCount() + 0f) / hCount);
            int screenOffsetCount = (screenItemCount) / hCount;
            if (mvOffsetCount > screenOffsetCount) {
                mvOffsetCount = mvOffsetCount - screenOffsetCount;
            } else {
                mvOffsetCount = 0;
            }
            totalOffset = mvOffsetCount * itemHSize + itemHSize;
        }

        fill(recycler, state, 0);
        recycleChildren(recycler);
        upDate();
    }

    //添加子view
    private void addChild(View child, int l, int t, int r, int b, float scf) {
        addView(child);
        measureChildWithMargins(child, itemWSize * 2, itemHSize * 2);
        if (scf > 1f) {
            scf = 1f;
        }
        if (scf < minScale) {
            scf = minScale;
        }
        child.setScaleX(scf);
        child.setScaleY(scf);
        layoutDecoratedWithMargins(child, l, t, r, b);
    }

    //获得小圆的水平方向偏移
    private int getSmallCircleOffset(int leftOffset) {
        boolean noofs = Math.abs(leftOffset + itemWSize / 2 - hc) < itemWSize / 3;
        int ofs = smallCircleHorizontalOffset;
        if (noofs) {
            ofs = 0;
        } else if (leftOffset + itemWSize > hc) {
            ofs = -ofs;
        }
        return ofs;
    }

    //获得滚动中的水平方向偏移
    private int getRollingOffset(int currVOffsetint, int targetOffset, int leftOffset) {
        int ofset = Math.abs(currVOffsetint - targetOffset);
        float ofsetf = (ofset + 0f) / withSmallCircleDist;
        int ofs = (int) (smallCircleHorizontalOffset - smallCircleHorizontalOffset * ofsetf);
        boolean noofs = Math.abs(leftOffset + itemWSize / 2 - hc) < itemWSize / 3;
        if (noofs) {
            ofs = 0;
        } else if (leftOffset + itemWSize > hc) {
            ofs = -ofs;
        }
        return ofs;
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

        int lastOffset = (vCount - 1) * itemHSize + mSetOffset;//第三行的top
        int fsItemPosition = (int) Math.floor(Math.abs(mCurrentOffset) / itemHSize) * hCount;
        if (fsItemPosition <= getItemCount() - 1) {
            mFirsItemPosition = fsItemPosition;
        }
        int LastItemPosition = mFirsItemPosition + screenItemCount - 1 + hCount;
        if (LastItemPosition > getItemCount() - 1) {
            LastItemPosition = getItemCount() - 1;
        }
        mLastItemPosition = LastItemPosition;
        int buttomItemCount = (mLastItemPosition + 1) % hCount;//第三行有多少个item
        if (buttomItemCount == 0) {
            buttomItemCount = hCount;
        }

        if (mFirsItemPosition >= hCount) {
//顶部小item
            for (int k = mFirsItemPosition - hCount; k < mFirsItemPosition; k++) {
                int ofs = getSmallCircleOffset(leftOffset);
                View child = recycler.getViewForPosition(k);
                addChild(child, leftOffset + ofs, smallCircleTopToScreenOffset, leftOffset + itemWSize + ofs, smallCircleTopToScreenOffset + itemHSize, minScale);
                leftOffset += itemWSize;
            }
        }


        leftOffset = getPaddingLeft();
        int startButItemPosition = 0;
        int endButItemPosition = 0;
        if (mLastItemPosition + hCount < getItemCount()) {
            startButItemPosition = mLastItemPosition + 1;
            endButItemPosition = mLastItemPosition + hCount;
        } else {
            startButItemPosition = mLastItemPosition + 1;
            endButItemPosition = getItemCount() - 1;
        }
        if (startButItemPosition != 0 && endButItemPosition != 0) {
            for (int i = mLastItemPosition + 1; i < getItemCount(); i++) {
//画底部小圆
                int ofs = getSmallCircleOffset(leftOffset);
                View child = recycler.getViewForPosition(i);
                addChild(child, leftOffset + ofs, bottomSmallCircleTopOffset, leftOffset + itemWSize + ofs, bottomSmallCircleTopOffset + itemHSize, minScale);
                leftOffset += itemWSize;
            }
        }


        if (mCurrentOffset < 0 && mLastItemPosition >= (vCount - 1) * hCount) {
//有最后一行就得干,下滑，从底部开始布局，解决Android4.4没有设置view Z轴方法
            for (int i = mLastItemPosition; i >= 0; i--) {
                View child = recycler.getViewForPosition(i);
                int iwCount = i % hCount + 1;//有几个宽度
                int l = iwCount * itemWSize + getPaddingLeft() - itemWSize;
                int r = iwCount * itemWSize + getPaddingLeft();
                int ihCount = i / hCount + 0;//在第几行

                if (i >= vCount * hCount) {
                    int ofs = getSmallCircleOffset(l);
                    addChild(child, l + ofs, bottomSmallCircleTopOffset, r + ofs, bottomSmallCircleTopOffset + itemHSize, minScale);
                } else if (i >= (vCount - 1) * hCount) {
                    int interceptOffset = lastOffset + viewTopOffset - mSetOffset;
                    if (interceptOffset > bottomSmallCircleTopOffset) {
                        interceptOffset = bottomSmallCircleTopOffset;
                    }
                    int ofs = getRollingOffset(interceptOffset, bottomSmallCircleTopOffset, l);
                    float realScale = 1f - (1f - minScale) * (interceptOffset - lastOffset) / withSmallCircleDist;
                    addChild(child, l + ofs, interceptOffset, r + ofs, interceptOffset + itemHSize, realScale);

                } else {
                    addChild(child, l, ihCount * itemHSize + viewTopOffset, r, (ihCount + 1) * itemHSize + viewTopOffset, 1f);
                }
            }
            return dy;
        }

        leftOffset = getHorizontalSpace() + getPaddingLeft();
        for (int i = mFirsItemPosition; i <= mLastItemPosition; i++) {
            View child = recycler.getViewForPosition(i);
            int iwCount = i % hCount + 1;//有几个宽度
            int l = iwCount * itemWSize + getPaddingLeft() - itemWSize;
            int r = iwCount * itemWSize + getPaddingLeft();
            if (i - mFirsItemPosition < hCount) {
//第一行
                int oneLineTopOffset = 0;
                float realScale = 1f - (1f - minScale) * frac;
                if (viewTopOffset < mSetOffset) {
                    realScale = 1f - (1f - minScale) * (mSetOffset - viewTopOffset) / withSmallCircleDist; //计算出滑动到小item的百分比
                }
                oneLineTopOffset = viewTopOffset;
                if (viewTopOffset <= smallCircleTopToScreenOffset) {
                    oneLineTopOffset = smallCircleTopToScreenOffset;//上滑到此停住
                }
                int ofs = getRollingOffset(oneLineTopOffset, smallCircleTopToScreenOffset, l);
                addChild(child, l + ofs, oneLineTopOffset, l + itemWSize + ofs, oneLineTopOffset + itemHSize, realScale);
            } else if (i > mLastItemPosition - buttomItemCount && i >= screenItemCount) {
//画底部小圆，下滑时秒变正常大小item的最后一行，要注意
                float realScale = minScale + (1f - minScale) * frac;

                if (viewTopOffset < lastOffset) {
//计算出滑动到小item的百分比
                    realScale = minScale + (1f - minScale) * (lastOffset - viewTopOffset + smallCircleTopToScreenOffset) / withSmallCircleDist;
                }
                int myTopoffset = bottomSmallCircleTopOffset;//固定住底部小item
                if (viewTopOffset <= bottomSmallCircleTopOffset - itemHSize) {//上滑时跟随上滑
                    myTopoffset = viewTopOffset + itemHSize;
                }
                if (myTopoffset > bottomSmallCircleTopOffset) {//下滑时固定
                    myTopoffset = bottomSmallCircleTopOffset;
                }
                int ofs = getRollingOffset(myTopoffset, bottomSmallCircleTopOffset, l);
                if (mCurrentOffset > totalOffset - itemHSize - 1) {//最后一行正常大小显示
                    myTopoffset = viewTopOffset + itemHSize;
                    realScale = 1f;
                    ofs = 0;
                }
                addChild(child, l + ofs, myTopoffset, r + ofs, myTopoffset + itemHSize, realScale);

            } else { //正常大小item的第二行到倒数第二行
                if (leftOffset + itemWSize <= getHorizontalSpace() + getPaddingLeft()) { //当前行还排列的下
                    Log.i("DBFdebug", "换行1");
                    addChild(child, leftOffset, viewTopOffset, leftOffset + itemWSize, viewTopOffset + itemHSize, 1f);
                    leftOffset += itemWSize;
                } else {
                    Log.i("DBFdebug", "换行2");
                    leftOffset = getPaddingLeft();
                    viewTopOffset += itemHSize;
                    addChild(child, leftOffset, viewTopOffset, leftOffset + itemWSize, viewTopOffset + itemHSize, 1f);
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
        upDate();
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




    StringBuilder stringBuilder = new StringBuilder();
    int horizontalOffset = 10;

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

                float itemCenterY = y + py;
                float itemCenterX = x + px;
                int layoutYcenter = prh / 2;
                float f = (layoutYcenter - Math.abs(layoutYcenter - itemCenterY)) / layoutYcenter;
//                childView.setScaleX(f);
//                childView.setScaleY(f);

                float centerXdist = (float) Math.sqrt(Math.pow(layoutYcenter, 2) - Math.pow(Math.abs(layoutYcenter - itemCenterY), 2));
                int llx = (int) (layoutYcenter - centerXdist);//圆左x坐标
                int rrx = (int) (layoutYcenter + centerXdist);//圆右x坐标
                int toScreenDist = 0;
                if (itemCenterX < layoutYcenter && itemCenterX < w * 2 && centerXdist > w/2) {
                    toScreenDist = (int) (llx - (itemCenterX - px));


                } else if (itemCenterX > layoutYcenter && itemCenterX > prw - w * 2 && centerXdist > w/2) {
                    toScreenDist = (int) (rrx - (itemCenterX + px));
                }
//                Object o = childView.getTag();
//                if (o != null) {
//                    childView.offsetLeftAndRight(-(Integer) o);
//                }
//                childView.setTranslationX(toScreenDist);
                childView.offsetLeftAndRight(toScreenDist);
//                childView.setTag(toScreenDist);

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
                stringBuilder.append(" f=").append(formatString(String.valueOf(f), 4, " "));

                Log.i("DBFdebug", stringBuilder.toString());
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