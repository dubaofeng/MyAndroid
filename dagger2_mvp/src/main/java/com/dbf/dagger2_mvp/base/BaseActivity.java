package com.dbf.dagger2_mvp.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dbf.dagger2_mvp.App;
import com.dbf.dagger2_mvp.UIUtils;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private App application;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        application = (App) getApplication();
        mContext = this;
        initView();
    }


    protected abstract void initView();


    protected void toastLONG(String msg) {
        UIUtils.toastLONG(this, msg);
    }

    protected void toastLONG(int msg) {
        UIUtils.toastLONG(this, msg);
    }
}
