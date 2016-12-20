package com.buqi.app.main.scenestyle;

import com.buqi.app.bean.SceneStyleResponse;
import com.buqi.app.network.Network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/9/22.
 */
public class SceneStyleModel implements SceneStyleContract.Model {

    @Override
    public Observable<SceneStyleResponse> getStyleScene(String token) {
        return Network.getApiService().getStyleScene(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
