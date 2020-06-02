package com.dbf.dagger2_mvp.ui.login;

import com.dbf.dagger2_mvp.base.BaseModule;
import com.dbf.dagger2_mvp.base.BasePresenter;
import com.dbf.dagger2_mvp.base.BaseView;

import io.reactivex.Observable;

public interface LoginContract {
    /**
     * 对于Model层也是数据层。它区别于MVC架构中的Model，在这里不仅仅只是数据模型。
     * 在MVP架构中Model它负责对数据的存取操作，例如对数据库的读写，网络的数据的请求等
     */
    interface Module extends BaseModule {

        String login(String userId, String password);

    }

    interface View extends BaseView<Presenter> {
        /**
         * 登录结果
         */
        void loginResult(String result);
    }

    interface Presenter extends BasePresenter {
        void login(String userId, String password);
    }
}
