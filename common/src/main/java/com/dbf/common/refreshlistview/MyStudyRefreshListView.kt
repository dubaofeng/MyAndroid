package com.dbf.common.refreshlistview

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.AbsListView
import android.widget.ListView
import com.dbf.common.R
import com.dbf.common.myutils.MyLog
import com.dbf.common.refreshlistview.Utils.measureView

/***
 *@author dbf
 *@time 2020/3/22 22:48
 * @JvmOverloads 这个注解想当于java重载了自定义View的那几个必须的构造方法
 */
class MyStudyRefreshListView @JvmOverloads constructor(context: Context,
                                                       attributeSet: AttributeSet? = null,
                                                       defStyleAttr: Int = 0) :
    ListView(context, attributeSet, defStyleAttr), AbsListView.OnScrollListener {
    companion object {
        val TAG = "MyStudyRefreshListView"
    }

    init {
        initHead()
        initFoot()
        setOnScrollListener(this)
    }

    lateinit var headView: View
    lateinit var footView: View
    var headViewHeight = 0
    var isLoading = false
    var firstVisibleItem = 0

    inner class Myhandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> headView.setPadding(0, -headViewHeight, 0, 0)
            }
        }
    }

    var myhandler = Myhandler()

    fun initHead() {
        headView =
            LayoutInflater.from(context).inflate(R.layout.mystudyrefreshlistview_headlayout, null)
        measureView(headView) //测量头部
        headViewHeight = headView.getMeasuredHeight() //取得头部布局高度
        headView.setPadding(0, -headViewHeight, 0, 0) //设置头部布局上内边距-headViewHeight隐藏头部
        this.addHeaderView(headView) //listView添加头部

    }

    fun initFoot() {
        footView = LayoutInflater.from(context).inflate(R.layout.bwc_listview_refresh_footer, null)
        this.addFooterView(footView)
    }

    override fun onScroll(view: AbsListView?,
                          firstVisibleItem: Int,
                          visibleItemCount: Int,
                          totalItemCount: Int) {
        //firstVisibleItem  第一个显示条目的下标 如果添加头和脚则头和脚也算一个条目
        //visibleItemCount当前屏幕可见的条目数 只见一点点的也算一个
        //totalItemCount 总条目数
        MyLog.d("onScroll${TAG}",
            "firstVisibleItem=${firstVisibleItem} visibleItemCount=${visibleItemCount} totalItemCount=${totalItemCount}")
        this.firstVisibleItem = firstVisibleItem
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
        //scrollState==0 列表已经停止滚动  OnScrollListener.SCROLL_STATE_IDLE;
        //scrollState==1 手指拖动      OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;
        //scrollState==2 列表自己滚动（惯性滚动）OnScrollListener.SCROLL_STATE_FLING;
        MyLog.d("onScrollStateChanged${TAG}", "scrollState=${scrollState}")
    }

    var dy: Float = -1f
    var my: Float = -1f
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                dy = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (isLoading) {
                    //如果正在加载更多
                    return true
                }
                if (dy == -1f) {
                    dy = ev.y
                }
                my = ev.y - dy
                MyLog.d(TAG, "ACTION_MOVE_my=$my")
                var myscrolly = (-headViewHeight + my / 2)
                if (firstVisibleItem == 0 && myscrolly > -headViewHeight) {
                    //头部已经开始显示
                    headView.setPadding(0, myscrolly.toInt(), 0, 0)
                }
            }
            MotionEvent.ACTION_UP -> {
                //恢复初始状态
                //判断是否符合下拉松手刷新
                dy = -1f
                MyLog.d(TAG, "ACTION_UP")
                myhandler.sendEmptyMessageDelayed(0, 1000)
            }
        }
        return super.onTouchEvent(ev)
    }


}