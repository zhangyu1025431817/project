package com.fangzhipro.app.main.house.scene;

import com.fangzhipro.app.bean.Scenes;
import com.fangzhipro.app.network.MySubscriber;
import com.fangzhipro.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/9/22.
 */
public class ScenePresenter extends SceneContract.Presenter {
    @Override
    public void getScenes() {
        mRxManager.add(mModel.getScene(mView.getToken(), mView.getUserId(),
                mView.getHouseHotTypeId(), mView.getDecorateId()).
                subscribe(new MySubscriber<Scenes>() {
                    @Override
                    public void onNext(Scenes scenes) {
                        if (ErrorCode.TOKEN_INVALID.equals(scenes.getError_code())) {
                            mView.tokenInvalid(scenes.getMsg());
                        } else if (ErrorCode.SERVER_EXCEPTION.equals(scenes.getError_code())) {
                            mView.onError(scenes.getMsg());
                        } else if (ErrorCode.SUCCEED.equals(scenes.getError_code())) {
                            mView.showScene(scenes.getSceneList());
                        } else {
                            mView.showScene(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showScene(null);
                    }
                }));
    }

    @Override
    public void onStart() {

    }
}
