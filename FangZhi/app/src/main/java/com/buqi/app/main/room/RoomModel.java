package com.buqi.app.main.room;

import com.buqi.app.bean.RoomProductTypes;
import com.buqi.app.bean.SameScenes;
import com.buqi.app.network.NetWorkRequest;
import com.buqi.app.network.Network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
* Created by smacr on 2016/9/22.
        */
public class RoomModel implements RoomContract.Model {
    @Override
    public Observable<RoomProductTypes> getRoomPartTypes(String token, String hotType,
                                                         String userId, String sceneId,String hlCode) {
        return NetWorkRequest.getRoomProductTypes(token,hotType,userId,sceneId,hlCode);
    }

    @Override
    public Observable<SameScenes> getScenes(String token, String sceneId) {
        return Network.getApiService().getSameScenes(token,sceneId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
