package com.dbf.dagger2_mvp.ui.login;

import com.dbf.dagger2_mvp.base.AbsBasePresenter;

import javax.inject.Inject;

public class LoginPresenter extends AbsBasePresenter<LoginModule, LoginActivity> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter(LoginModule module, LoginActivity view) {
        mModule = module;
        mView = view;
    }

    @Override
    public void onDestory() {

    }

    @Override
    public void login(String userId, String password) {
        String result = mModule.login(userId, password);
        mView.loginResult(result);
    }
}
