package com.dbf.dagger2_mvp.ui.login;


import dagger.Module;
import dagger.Provides;

@Module
public abstract class LoginActivityModule {
    @Provides
    static String provideName() {
        return LoginActivity.class.getName();
    }
}
