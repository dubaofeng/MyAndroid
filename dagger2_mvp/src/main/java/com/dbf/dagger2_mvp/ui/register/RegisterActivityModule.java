package com.dbf.dagger2_mvp.ui.register;


import com.dbf.dagger2_mvp.ui.login.LoginActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RegisterActivityModule {
    @Provides
    static String provideName() {
        return RegisterActivity.class.getName();
    }

}
