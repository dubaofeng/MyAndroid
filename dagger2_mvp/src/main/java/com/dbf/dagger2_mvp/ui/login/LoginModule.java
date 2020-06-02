package com.dbf.dagger2_mvp.ui.login;

import com.dbf.dagger2_mvp.network.WanAndroidApi;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoginModule implements LoginContract.Module {

    @Inject
    public LoginModule() {

    }


    @Override
    public String login(String userId, String password) {
        return userId + password;
    }
}
