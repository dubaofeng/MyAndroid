package com.dbf.dagger2_mvp.ui.register;

import com.dbf.dagger2_mvp.base.AbsBasePresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter层负责逻辑功能代码、调用网络数据、本地数据封装层的编写
 */
public class RegisterPresenter extends AbsBasePresenter<RegisterModule, RegisterActivity> implements RegisterContract.Presenter {
    private Disposable disposable;

    @Inject
    public RegisterPresenter(RegisterModule module, RegisterActivity view) {
        mModule = module;
        mView = view;
    }


    @Override
    public void register(String mobile, String password, String rePassword) {
        disposable = mModule.register(mobile, password, rePassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::registerResult);
    }

    @Override
    public void onDestory() {
        mView = null;
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }

    }
}
