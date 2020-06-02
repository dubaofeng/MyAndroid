package com.dbf.dagger2_mvp.ui.register;

import com.dbf.dagger2_mvp.network.WanAndroidApi;
import com.dbf.dagger2_mvp.network.bean.BaseResponse;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Model层负责数据的访问操作，同时开放接口提供给presenter层进行调用
 */
public class RegisterModule implements RegisterContract.Module {
    private String user;
    private boolean isSucess;
    private String msg;

    @Inject
    public RegisterModule() {

    }

    @Inject
    WanAndroidApi wanAndroidApi;

    public RegisterModule(String user, boolean isSucess, String msg) {
        this.user = user;
        this.isSucess = isSucess;
        this.msg = msg;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public boolean isSucess() {
        return isSucess;
    }

    @Override
    public String getMsg() {
        return msg;
    }


    @Override
    public Observable<RegisterContract.Module> register(String mobile, String password, String rePassword) {
//        Maybe<RegisterContract.Module> baseResponse1 = wanAndroidApi.register(mobile, password, rePassword).
//                map(new Function<BaseResponse, RegisterContract.Module>() {
//                    @Override
//                    public RegisterContract.Module apply(BaseResponse baseResponse) throws Exception {
//                        return new RegisterModule(mobile, baseResponse.getErrorCode() == 0 ? true : false, baseResponse.getErrorMsg());
//                    }
//                });
//        return baseResponse1.toObservable();
        //lambda 写法
        return wanAndroidApi.register(mobile, password, rePassword)
                .map(baseResponse ->
                        (RegisterContract.Module) new RegisterModule(mobile, baseResponse.getErrorCode() == 0 ? true : false, baseResponse.getErrorMsg()))
                .toObservable();
    }
}
