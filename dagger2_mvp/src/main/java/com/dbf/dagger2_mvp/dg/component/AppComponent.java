package com.dbf.dagger2_mvp.dg.component;

import com.dbf.dagger2_mvp.App;
import com.dbf.dagger2_mvp.dg.module.AllActivitysModule;
import com.dbf.dagger2_mvp.network.dg.ApiServiceModule;
import com.dbf.dagger2_mvp.network.dg.HttpModule;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AllActivitysModule.class,
                HttpModule.class,
                ApiServiceModule.class
        }
)
public interface AppComponent extends AndroidInjector<App> {
}
