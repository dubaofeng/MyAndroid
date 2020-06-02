package com.dbf.dagger2_mvp.base;

public class AbsBasePresenter<M extends BaseModule, V extends BaseView> {
    protected M mModule;
    protected V mView;
}
