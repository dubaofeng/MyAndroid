package com.dbf.dagger2_mvp.ui.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.dbf.dagger2_mvp.R;
import com.dbf.dagger2_mvp.UIUtils;
import com.dbf.dagger2_mvp.base.BaseActivity;
import com.dbf.dagger2_mvp.databinding.ActivityRegisterBinding;
import com.dbf.dagger2_mvp.ui.login.LoginPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    private static int TIME = 20;
    private Observable<Boolean> registerObservable;
    private ActivityRegisterBinding mRootView;
    @Inject
    protected RegisterPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initView() {
        mRootView = ActivityRegisterBinding.inflate(LayoutInflater.from(this));
        setContentView(mRootView.getRoot());
        registerObservable = RxView.clicks(mRootView.registerBt)
                .throttleFirst(TIME, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<Object, Boolean>() {
                    @Override
                    public Boolean apply(Object o) throws Exception {
                        return false;
                    }
                })
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mRootView.registerBt.setEnabled(aBoolean);
                    }
                });
        registerObservable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .take(TIME)
                        .subscribe(new Consumer<Long>() {
                                       @Override
                                       public void accept(Long aLong) throws Exception {
                                           RxTextView.text(mRootView.registerBt).accept("剩余" + (TIME - aLong) + "秒");
                                       }
                                   }
                                , new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        throwable.printStackTrace();
                                    }
                                }
                                , new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        RxTextView.text(mRootView.registerBt).accept("注 册");
                                        mRootView.registerBt.setEnabled(true);
                                    }
                                }

                        );
            }
        });


//lambda写法
//        registerObservable = RxView.clicks(mRootView.registerBt)
//                .throttleFirst(TIME, TimeUnit.SECONDS) //防止20秒内连续点击,或者只使用doOnNext部分
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .map(o -> false)
//                .doOnNext(mRootView.registerBt::setEnabled);
//        registerObservable.subscribe(s ->
//                Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                        .take(TIME)
//                        .subscribe(aLong ->
//                                        RxTextView.text(mRootView.registerBt).accept("剩余" + (TIME - aLong) + "秒")
//                                , Throwable::printStackTrace
//                                , () -> {
//                                    RxTextView.text(mRootView.registerBt).accept("注 册");
//                                    RxView.enabled(mRootView.registerBt).accept(true);
//                                }));
        Observable<CharSequence> observableName = RxTextView.textChanges(mRootView.userIDEt);
        Observable<CharSequence> observablePassword = RxTextView.textChanges(mRootView.userPWEt);
        Observable<CharSequence> observableRePassword = RxTextView.textChanges(mRootView.userRePWEt);
        Observable.combineLatest(observableName
                , observablePassword
                , observableRePassword
                , (usr, pwd, rePwd) -> registerValid(usr.toString(), pwd.toString(), rePwd.toString()))
                .subscribe(mRootView.registerBt::setEnabled);
        RxView.clicks(mRootView.registerBt)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> {
                    String username = mRootView.userIDEt.getText().toString();
                    String password = mRootView.userPWEt.getText().toString();
                    String rePassword = mRootView.userRePWEt.getText().toString();
                    mPresenter.register(username, password, rePassword);
                });
    }

    private static Boolean registerValid(String usr, String pwd, String rePwd) {
        return isUsrValid(usr) && isPasswordValid(pwd, rePwd);
    }

    private static boolean isUsrValid(String usr) {
        return usr.length() == 11;
    }

    private static boolean isPasswordValid(String pwd, String rePwd) {
        return pwd.length() >= 6 && TextUtils.equals(pwd, rePwd);
    }


    @Override
    public void registerResult(RegisterContract.Module result) {
        if (result.isSucess()) {
            toastLONG(result.getMsg());
        } else {
            toastLONG(result.getMsg());
        }
    }
}
