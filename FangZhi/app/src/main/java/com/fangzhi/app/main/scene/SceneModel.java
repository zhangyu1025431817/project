package com.fangzhi.app.main.scene;

import com.fangzhi.app.bean.Scenes;
import com.fangzhi.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/9/22.
 */
public class SceneModel implements SceneContract.Model {
    @Override
    public Observable<Scenes> getScene(String token, String userId, String hotType) {
        return NetWorkRequest.getScenes(token,userId,hotType);
    }
}
