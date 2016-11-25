package com.fangzhi.app.network;


import rx.Subscriber;

/**
 * by y on 2016/5/6.
 */
public class MySubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {
    }


}
