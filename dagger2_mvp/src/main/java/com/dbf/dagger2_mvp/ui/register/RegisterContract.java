package com.dbf.dagger2_mvp.ui.register;

import com.dbf.dagger2_mvp.base.BaseModule;
import com.dbf.dagger2_mvp.base.BasePresenter;
import com.dbf.dagger2_mvp.base.BaseView;

import io.reactivex.Observable;

public interface RegisterContract {
    interface Module extends BaseModule {
        String getUser();

        boolean isSucess();

        String getMsg();

        /**
         * 注册
         *
         * @param mobile
         * @param password
         * @param rePassword
         */
        Observable<Module> register(String mobile, String password, String rePassword);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 注册结果
         *
         * @param result
         */
        void registerResult(Module result);
    }

    interface Presenter extends BasePresenter {
        void register(String mobile, String password, String rePassword);
    }
}
