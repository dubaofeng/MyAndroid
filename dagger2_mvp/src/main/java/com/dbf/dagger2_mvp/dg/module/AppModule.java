package com.dbf.dagger2_mvp.dg.module;

import android.app.Application;

import com.dbf.dagger2_mvp.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    public Application getApplication() {
        return this.application;
    }
}
