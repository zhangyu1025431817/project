package com.buyiren.app.main.house.scene;

import com.buyiren.app.bean.RoomProductTypes;
import com.buyiren.app.bean.Scenes;
import com.buyiren.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/9/22.
 */
public class SceneModel implements SceneContract.Model {
    @Override
    public Observable<Scenes> getScene(String token, String userId, String hotType,String decorateId  ) {
        return NetWorkRequest.getScenes(token,userId,hotType,decorateId);
    }

    @Override
    public Observable<RoomProductTypes> getRoomPartTypes(String token, String hotType, String userId, String sceneId, String hlCode) {
        return NetWorkRequest.getRoomProductTypes(token,hotType,userId,sceneId,hlCode);
    }
}
