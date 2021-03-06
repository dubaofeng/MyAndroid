package com.dbf.dagger2_mvp;


import com.dbf.dagger2_mvp.dg.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.create();
    }
}
