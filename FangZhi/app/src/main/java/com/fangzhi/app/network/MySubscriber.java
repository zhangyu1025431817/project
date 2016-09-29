package com.fangzhi.app.network;


import com.fangzhi.app.tools.L;

import rx.Subscriber;

/**
 * by y on 2016/5/6.
 */
public class MySubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
        L.e("MySubscriber", "onStart被调用了");
    }

    @Override
    public void onCompleted() {
        L.e("MySubscriber", "onCompleted被调用了");
    }

    @Override
    public void onError(Throwable e) {
        L.e("MySubscriber", "onError被调用了");
    }

    @Override
    public void onNext(T t) {
        L.e("MySubscriber", "onNext被调用了");
    }
    
    
}
