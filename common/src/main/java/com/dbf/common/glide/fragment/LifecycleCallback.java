package com.dbf.common.glide.fragment;

/**
 * Created by dbf on 2020/6/7
 * describe:
 */
public interface LifecycleCallback {
    //生命周期 开始初始化了
    void glideInitAction();

    //生命周期 停止了
    void glideStopAcyion();

    //生命周期 释放操作
    void glideRecycleAction();

}
