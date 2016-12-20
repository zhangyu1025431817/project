package com.buqi.app.main.house.scene;

import com.buqi.app.bean.Scenes;
import com.buqi.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/9/22.
 */
public class SceneModel implements SceneContract.Model {
    @Override
    public Observable<Scenes> getScene(String token, String userId, String hotType,String decorateId  ) {
        return NetWorkRequest.getScenes(token,userId,hotType,decorateId);
    }
}
