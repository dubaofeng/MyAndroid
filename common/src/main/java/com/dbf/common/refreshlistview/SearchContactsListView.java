package com.dbf.common.refreshlistview;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dbf.common.R;
import com.dbf.common.myutils.MyLog;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SearchContactsListView extends ListView implements OnScrollListener {
    private final String TAG = "SearchContactsListView";
    private int downY = -1; // 按下时y轴的偏移量
    private View headerView; // 头布局
    private int headerViewHeight; // 头布局的高度
    private int firstVisibleItemPosition; // 滚动时界面显示在顶部的item的position
    private DisplayMode currentState = DisplayMode.Pull_Down; // 头布局当前的状态,
    // 缺省值为下拉状态
    private Animation upAnim; // 向上旋转的动画
    private Animation downAnim; // 向下旋转的动画
    // private ImageView ivArrow; // 头布局的箭头
    private TextView tvState; // 头布局刷新状态
    // private ProgressBar mProgressBar; // 头布局的进度条
    private OnRefreshListener mOnRefreshListener;
    private boolean isScroll2Bottom = false; // 是否滚动到底部
    private View footerView; // 脚布局
    private LinearLayout llLoadView;
    private int footerViewHeight; // 脚布局的高度
    private boolean isLoadMoving = false; // 是否正在加载更多中
    private int total = 0;
    public static boolean isPull = false;

    private ImageView imgLoading; // 头布局的箭头
    private AnimationDrawable animationDrawable = null;
    private RefreshFirstStepView mFirstView;

    public SearchContactsListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initHeader();
        initFooter();
        this.setOnScrollListener(this);
    }

    /**
     * 初始化脚布局
     */
    private void initFooter() {
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.bwc_listview_refresh_footer, null);
        llLoadView = (LinearLayout) footerView.findViewById(R.id.llLoading);
        Utils.INSTANCE.measureView(footerView); // 测量一下脚布局的高度
        footerViewHeight = footerView.getMeasuredHeight();
        showLoading(false);
        footerView.setPadding(0, 0, 0, 0); // 隐藏脚布局
        this.addFooterView(footerView);
    }


    /**
     * 初始化头布局
     */
    private void initHeader() {
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.bwc_listview_refresh_header, null);
        mFirstView = (RefreshFirstStepView) headerView.findViewById(R.id.firstView);
        imgLoading = (ImageView) headerView.findViewById(R.id.imgLoading);
        imgLoading.setBackgroundResource(R.drawable.pull_to_refresh_second_anim);
        animationDrawable = (AnimationDrawable) imgLoading.getBackground();
        animationDrawable.setOneShot(false);
        imgLoading.setImageDrawable(animationDrawable);
        tvState = (TextView) headerView.findViewById(R.id.tv_listview_header_state);
        Utils.INSTANCE.measureView(headerView);
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        this.addHeaderView(headerView);
    }

    private void showLoading(boolean isShowLoad) {
        llLoadView.setVisibility(isShowLoad ? View.VISIBLE : View.GONE);
    }

    private void hideLoadMode() {
        llLoadView.setVisibility(View.GONE);
    }

    /**
     * 获得最后刷新的时间
     *
     * @return
     */
    private String getLastUpdateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        upAnim = new RotateAnimation(
                0,
                -180,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        upAnim.setDuration(500);
        upAnim.setFillAfter(true);

        downAnim = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnim.setDuration(500);
        downAnim.setFillAfter(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                isPull = true;
                if (currentState == DisplayMode.Refreshing) {
                    // 当前的状态是正在刷新中, 不执行下拉操作
                    break;
                }
                if (downY == -1) downY = (int) ev.getY();
                int moveY = (int) ev.getY(); // 移动中的y轴的偏移量
                int diffY = moveY - downY;
                int paddingTop = -headerViewHeight + (diffY / 2);
//                MyLog.INSTANCE.i(TAG, "paddingTop=" + paddingTop);
                //计算当前滑动的高度
                float currentHeight = (-headerViewHeight + diffY / 2);
                MyLog.INSTANCE.i(TAG, "currentHeight=" + currentHeight + "  headerViewHeight="+headerViewHeight);
                //用当前滑动的高度和头部headerView的总高度进行比 计算出当前滑动的百分比 0到1
                float currentProgress = 1 + currentHeight / headerViewHeight;
                MyLog.INSTANCE.i(TAG, "currentProgress=" + currentProgress);
                //如果当前百分比大于1了，将其设置为1，目的是让第一个状态的椭圆不再继续变大
                if (currentProgress >= 1) {
                    currentProgress = 1;
                }
                if (firstVisibleItemPosition == 0 && paddingTop > -headerViewHeight) {
                    //paddingTop > 0 完全显示 currentState == DisplayMode.Pull_Down 当是在下拉状态时
                    if (paddingTop > 0 && currentState == DisplayMode.Pull_Down) { // 完全显示,
                        // 进入到刷新状态
                        Log.i("RefreshListView", "松开刷新");
                        currentState = DisplayMode.Release_Refresh; // 把当前的状态改为松开刷新
                        refreshHeaderViewState();
                    } else if (paddingTop < 0 && currentState == DisplayMode.Release_Refresh) { // 没有完全显示,
                        // 进入到下拉状态
                        Log.i("RefreshListView", "下拉刷新");
                        currentState = DisplayMode.Pull_Down;
                        refreshHeaderViewState();
                    }
                    mFirstView.setCurrentProgress(currentProgress);
//重画
                    mFirstView.postInvalidate();
                    headerView.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isPull = false;
                downY = -1;
                if (currentState == DisplayMode.Pull_Down) { // 松开时, 当前显示的状态为下拉状态,
                    // 执行隐藏headerView的操作
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                } else if (currentState == DisplayMode.Release_Refresh) { // 松开时,
                    // 当前显示的状态为松开刷新状态,
                    // 执行刷新的操作
                    headerView.setPadding(0, 0, 0, 0);
                    currentState = DisplayMode.Refreshing;
                    refreshHeaderViewState();
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onRefresh();
                    }
                    return true;
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 当刷新任务执行完毕时, 回调此方法, 去刷新界面
     */
    public void onRefreshFinish(boolean hasData, int total) {
        this.total = total;
        hideHeader();
        if (isLoadMoving) { // 隐藏脚布局
            isLoadMoving = false;
            isScroll2Bottom = false;
            showLoading(false);
            footerView.setPadding(0, -footerViewHeight, 0, 0);
        } else { // 隐藏头布局
// headerView.setPadding(0, -headerViewHeight, 0, 0);

// ivArrow.setVisibility(View.VISIBLE);
// tvLastUpdateTime.setText(BeaconApplication.mContext.getString(
// R.string.text_last_update_time, getLastUpdateTime()));
            currentState = DisplayMode.Pull_Down;
        }
        if (!hasData) {
            hideLoadMode();
        }
    }

    int scrollY = 0;

    private void hideHeader() {
        int top = headerView.getPaddingTop();
        if (top == -headerViewHeight)
            return;
        scrollY = 0;
        mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (scrollY < headerViewHeight)
                scrollY += 10;
            if (scrollY >= headerViewHeight)
                scrollY = headerViewHeight;
            headerView.setPadding(0, -scrollY, 0, 0);
            if (scrollY >= headerViewHeight) {
                mHandler.removeMessages(0);
                mFirstView.setVisibility(View.VISIBLE);
                animationDrawable.stop();
                animationDrawable.selectDrawable(0);
                imgLoading.setVisibility(View.GONE);
            } else
                mHandler.sendEmptyMessageDelayed(0, 10);
        }
    };

    /**
     * 刷新头布局的状态
     */
    private void refreshHeaderViewState() {
        if (currentState == DisplayMode.Pull_Down) { // 当前进入下拉状态
            // ivArrow.startAnimation(downAnim);
            mFirstView.setVisibility(View.VISIBLE);
            imgLoading.setVisibility(View.GONE);
            animationDrawable.stop();
            tvState.setText("下拉刷新");
        } else if (currentState == DisplayMode.Release_Refresh) { // 当前进入松开刷新状态
            // ivArrow.startAnimation(upAnim);
            tvState.setText("松开刷新");
        } else if (currentState == DisplayMode.Refreshing) { // 当前进入正在刷新中
            // ivArrow.clearAnimation();
            // ivArrow.setVisibility(View.GONE);
            tvState.setText("正在刷新中...");
            mFirstView.setVisibility(View.GONE);
            //第三状态view显示出来
            imgLoading.setVisibility(View.VISIBLE);
            animationDrawable.start();
        }
    }

    /**
     * 当ListView滚动状态改变时回调 SCROLL_STATE_IDLE // 当ListView滚动停止时
     * SCROLL_STATE_TOUCH_SCROLL // 当ListView触摸滚动时 SCROLL_STATE_FLING //
     * 快速的滚动(手指快速的触摸移动)
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING) {
            if (isScroll2Bottom && !isLoadMoving) { // 滚动到底部
                showLoading(true);// 加载更多
                footerView.setPadding(0, 0, 0, 0);
                this.setSelection(this.getCount()); // 滚动到ListView的底部
                isLoadMoving = true;
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onLoadMoring();
                }
            }
        }
    }

    /**
     * 当ListView滚动时触发 firstVisibleItem 屏幕上显示的第一个Item的position visibleItemCount
     * 当前屏幕显示的总个数 totalItemCount ListView的总条数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstVisibleItemPosition = firstVisibleItem;
        if ((firstVisibleItem + visibleItemCount) >= totalItemCount && totalItemCount > 0) {
            isScroll2Bottom = true;//到底部
        } else {
            isScroll2Bottom = false;//没有到底部
        }
    }

    /**
     * @author andong 下拉头部的几种显示状态
     */
    public enum DisplayMode {
        Pull_Down, // 下拉刷新的状态
        Release_Refresh, // 松开刷新的状态
        Refreshing // 正在刷新中的状态
    }

    /**
     * 设置刷新的监听事件
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }
}