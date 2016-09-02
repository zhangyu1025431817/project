package com.fangzhi.app.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangyu on 2016/6/30.
 */
public class RxManager {
    /**
     * 管理订阅者
     */
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
//    public RxBus mRxBus = RxBus.$();
//    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察者

//    public void on(String eventName, Action1<Object> action1) {
//        Observable<?> mObservable = mRxBus.register(eventName);
//        mObservables.put(eventName, mObservable);
//        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(action1, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable e) {
//                        e.printStackTrace();
//                    }
//                }));
//    }

    public void add(Subscription sp) {
        mCompositeSubscription.add(sp);
    }

    public void clear() {
        //取消订阅
        mCompositeSubscription.unsubscribe();
//        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
//            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
//        }
    }
//
//    public void post(Object tag, Object content) {
//        mRxBus.post(tag, content);
//    }
}
