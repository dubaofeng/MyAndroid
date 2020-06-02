package com.dbf.dagger2_mvp.dg.module;

import com.dbf.dagger2_mvp.ui.login.LoginActivity;
import com.dbf.dagger2_mvp.ui.login.LoginActivityModule;
import com.dbf.dagger2_mvp.ui.register.RegisterActivity;
import com.dbf.dagger2_mvp.ui.register.RegisterActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AllActivitysModule {

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity contributeRegisterActivity();

}
