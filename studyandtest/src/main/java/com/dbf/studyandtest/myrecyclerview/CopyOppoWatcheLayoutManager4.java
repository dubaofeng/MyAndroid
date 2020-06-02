package com.dbf.studyandtest.myrecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.logging.Logger;

public class CopyOppoWatcheLayoutManager4 extends RecyclerView.LayoutManager {
    private final String TAG = "MyLayout";
    private int itemHSize = -1;
    private int itemWSize = -1;
    private int hCount = 3;
    private int vCount = 5;
    //屏幕可见的正常大小item个数
    private int screenItemCount = 12;
    //除去复用显示所有item需要的行数，用于计算垂直方向的总偏移
    private int mvOffsetCount = 0;

    private int mFirsItemPosition = 0;
    private int mLastItemPosition = 0;
    private int mCurrentOffset = 0;
    private int totalOffset = 0; //垂直方向总偏移量
    private int mSetOffset = 0;//竖直方向上添加的偏移
    private float minScale = 0.7f;
    private int smallCircleTopToScreenOffset; //小圆竖直方向距离屏幕边缘的位置
    private int smallCircleHorizontalOffset;//小圆水平方向上的偏移
    private int withSmallCircleDist;//大圆与小圆的距离

    private int bottomSmallCircleTopOffset;//底部小圆的top
    private int hc;//水平方向中间位置
    private int vc;//垂直方向中间位置
    private boolean isfirstFill = true;

    public CopyOppoWatcheLayoutManager4(Context context) {
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
        mSetOffset = 0;
        itemWSize = getHorizontalSpace() / hCount;
        if (itemHSize == -1) {
            itemHSize = (int) ((getVerticalSpace() - 2 * mSetOffset) / 5);
//
        }
        smallCircleTopToScreenOffset = (int) -(itemHSize / 2 - itemHSize * minScale / 2);
        bottomSmallCircleTopOffset = getVerticalSpace() - (itemHSize + smallCircleTopToScreenOffset);
        withSmallCircleDist = Math.abs(mSetOffset - smallCircleTopToScreenOffset);
        smallCircleHorizontalOffset = (int) (itemWSize * (1 - minScale));
        smallCircleHorizontalOffset = 0;
        hc = getHorizontalSpace() / 2 + getPaddingLeft();
        vc = getVerticalSpace() / 2 + getPaddingTop();
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
        if (isfirstFill) {
            isfirstFill = false;
            mCurrentOffset = -itemHSize;
            startValueAnimation(3);
        }
        upDate();
    }

    //添加子view
    private void addChild(View child, int l, int t, int r, int b, float scf) {
        addView(child);
        measureChildWithMargins(child, itemWSize * 2, itemHSize * 2);
//        if (scf > 1f) {
//            scf = 1f;
//        }
//        if (scf < minScale) {
//            scf = minScale;
//        }
//        child.setScaleX(scf);
//        child.setScaleY(scf);
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
        float ofsetf = (ofset + 0f) / getVerticalSpace() / 2 + getPaddingTop();
        int ofs = (int) (smallCircleHorizontalOffset - smallCircleHorizontalOffset * ofsetf);
        boolean noofs = Math.abs(leftOffset + itemWSize / 2 - hc) < itemWSize / 3;
        if (noofs) {
            ofs = 0;
        } else if (leftOffset + itemWSize > hc) {
            ofs = -ofs;
        }
        return ofs;
    }

    //获得滚动中的水平方向偏移
    private int getRollingOffset(float f, int leftOffset) {
        int ofs = (int) (smallCircleHorizontalOffset - smallCircleHorizontalOffset * f);
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

        int fsItemPosition = (int) Math.floor(Math.abs(mCurrentOffset) / itemHSize) * hCount;
        if (fsItemPosition <= getItemCount() - 1) {
            mFirsItemPosition = fsItemPosition;
        }
        mLastItemPosition = getItemCount() - 1;

        leftOffset = getPaddingLeft();
        for (int i = mFirsItemPosition; i <= mLastItemPosition; i++) {

            View child = recycler.getViewForPosition(i);
            if (!(leftOffset + itemWSize <= getHorizontalSpace() + getPaddingLeft())) {
                leftOffset = getPaddingLeft();
                viewTopOffset += itemHSize;
                if (viewTopOffset > getVerticalSpace() + getPaddingTop()) {
                    mLastItemPosition = i;
                    return dy;
                }
            }
            float f = 0f;
            if (viewTopOffset + itemHSize / 2 <= vc) {
                f = (float) (viewTopOffset + itemHSize / 2 - getPaddingTop()) / vc;
            } else {
                f = (float) (getVerticalSpace() + getPaddingTop() - viewTopOffset - itemHSize / 2) / vc;
            }
// child.setAlpha(f + 0.5f);
            int ofs = getRollingOffset(f, leftOffset);
            if (leftOffset == getPaddingLeft()) {
                child.setTag(0);
            } else if (leftOffset == itemWSize + getPaddingLeft()) {
                child.setTag(1);
            } else {
                child.setTag(2);
            }
//            addChild(child, leftOffset + ofs, viewTopOffset, leftOffset + itemWSize + ofs, viewTopOffset + itemHSize, f);
            addChild(child, getPaddingLeft() + itemWSize, viewTopOffset, getPaddingLeft() + itemWSize + itemWSize, viewTopOffset + itemHSize, f);
            leftOffset += itemWSize;
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
        if (mCurrentOffset <= -(1 * itemHSize)) {
            realOffset = 0;
            mCurrentOffset = (int) -(1 * itemHSize) + 1;
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
// Log.i("startValueAnimation", "/列表停止滚动后=");
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
// Log.i("startValueAnimation", "position=" + position);
        distance = getScrollToPositionOffset(position);
        if (mCurrentOffset < 0) {
            if (mCurrentOffset < -itemHSize / 2) {
                distance = itemHSize - mCurrentOffset;
            } else {
                distance = mCurrentOffset;
            }
        } else if (mCurrentOffset > totalOffset - itemHSize) {

            distance = (mCurrentOffset - (totalOffset - itemHSize));
            if (distance < itemHSize / 2) {
                distance = -distance;
            } else {
            }

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
                    if (mCurrentOffset < -itemHSize + 1) {
                        mCurrentOffset = -itemHSize + 1;
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
                int itemCenterY = (int) (y + py);
                int itemCenterX = (int) (x + px);
                int layoutYcenter = prh / 2;
                float f = (layoutYcenter - Math.abs(layoutYcenter - itemCenterY)) / (layoutYcenter + 0f);
//                if (itemCenterY < getHeight() / 2) {
//                    childView.offsetTopAndBottom((int) (10 * (1 - f)));
//                } else if (itemCenterY > getHeight() / 2) {
//                    childView.offsetTopAndBottom((int) -(10 * (1 - f)));
//                }

//                f = 0.7f + 0.3f * f;
                childView.setScaleX(f);
                childView.setScaleY(f);

                int centerXdist = 0;
                centerXdist = (int) Math.sqrt(Math.pow(layoutYcenter, 2) - Math.pow(Math.abs(layoutYcenter - itemCenterY), 2));


                int llx = (layoutYcenter - centerXdist);//圆左x坐标
                int rrx = (layoutYcenter + centerXdist);//圆右x坐标
                if (centerXdist > w / 2) {
                    int tag = (int) childView.getTag();
                    if (tag == 0) {
                        childView.offsetLeftAndRight((int) (llx - itemCenterX + px));

                    }
                    if (tag == 2) {
                        childView.offsetLeftAndRight((int) (rrx - itemCenterX - px));

                    }
                }


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