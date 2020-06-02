package com.dbf.dagger2_mvp.ui.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dbf.dagger2_mvp.R;
import com.dbf.dagger2_mvp.base.BaseActivity;

import javax.inject.Inject;


public class LoginActivity extends BaseActivity implements LoginContract.View {
    @Inject
    protected LoginPresenter mPresenter;

    @Override
    protected void initView() {
        mPresenter.login("xiaoming", "123456");
    }


    @Override
    public void loginResult(String result) {
        toastLONG(result);
    }
}
