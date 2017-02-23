package com.fangzhipro.app.main.room;

import com.fangzhipro.app.bean.RoomProductTypes;
import com.fangzhipro.app.network.NetWorkRequest;

import rx.Observable;

/**
* Created by smacr on 2016/9/22.
        */
public class RoomModel implements RoomContract.Model {
    @Override
    public Observable<RoomProductTypes> getRoomPartTypes(String token, String hotType,
                                                         String userId, String sceneId, String hlCode) {
        return NetWorkRequest.getRoomProductTypes(token,hotType,userId,sceneId,hlCode);
    }
}
