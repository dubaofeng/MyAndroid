package com.dbf.studyandtest.eventdispatch.exemple;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by dbf on 2020/11/19
 * describe:
 */
public class MyListView extends ListView {
    private boolean isBottom = false;
    private boolean isTop = false;

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount + firstVisibleItem == totalItemCount) {
                    View lastVisibleItemView = getChildAt(totalItemCount - firstVisibleItem - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == view.getHeight()) {
                        isBottom = true;
                    } else {
                        isBottom = false;
                    }
                } else {
                    isBottom = false;
                    View firstVisibleItemView = getChildAt(0);
                    if (firstVisibleItemView != null) {
                        if (firstVisibleItemView.getTop() == 0) {
                            isTop = true;
                        } else {
                            isTop = false;
                        }
                    }
                }
            }
        });
    }


    float x = 0;
    float y = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("TAG", "ListViewdispatchTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:


                x = ev.getX();
                y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (ev.getY() - y > 0) {
                    //listView向下滑，禁止上一级拦截
                    if (isTop) {
                        requestDisallowInterceptTouchEvent(false);
                    } else {
                        requestDisallowInterceptTouchEvent(true);
                    }

                } else {
                    //listView向上滑
                    if (isBottom) {
                        //如果到底了，上一级可以拦截
                        requestDisallowInterceptTouchEvent(false);
                    } else {
                        //否则，禁止上一级拦截
                        requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }


        return super.dispatchTouchEvent(ev);
    }

}
