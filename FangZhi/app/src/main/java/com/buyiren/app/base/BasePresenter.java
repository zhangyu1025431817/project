package com.buyiren.app.base;

import android.content.Context;

/**
 * Created by zhangyu on 2016/6/30.
 */
public abstract class BasePresenter<E, T> {
    public Context context;
    public E mModel;
    public T mView;
    public RxManager mRxManager = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public abstract void onStart();

    public void onDestroy() {
        mRxManager.clear();
    }
}
