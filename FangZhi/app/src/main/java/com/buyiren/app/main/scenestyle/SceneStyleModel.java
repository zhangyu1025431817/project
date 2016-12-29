package com.buyiren.app.main.scenestyle;

import com.buyiren.app.bean.RoomProductTypes;
import com.buyiren.app.bean.SceneStyleResponse;
import com.buyiren.app.network.NetWorkRequest;
import com.buyiren.app.network.Network;

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

    @Override
    public Observable<RoomProductTypes> getRoomPartTypes(String token, String hotType, String userId, String sceneId, String hlCode) {
        return NetWorkRequest.getRoomProductTypes(token,hotType,userId,sceneId,hlCode);
    }
}
