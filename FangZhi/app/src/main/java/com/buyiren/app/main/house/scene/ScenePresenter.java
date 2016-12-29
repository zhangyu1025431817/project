package com.buyiren.app.main.house.scene;

import com.buyiren.app.bean.RoomProductTypes;
import com.buyiren.app.bean.Scenes;
import com.buyiren.app.network.MySubscriber;
import com.buyiren.app.network.http.api.ErrorCode;

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
    public void getRoomPartTypeList() {
        mRxManager.add(mModel.getRoomPartTypes(mView.getToken(), mView.getHotType(),
                mView.getUserId(), mView.getSceneId(), mView.getHlCode()).subscribe(new MySubscriber<RoomProductTypes>() {
            @Override
            public void onNext(RoomProductTypes roomProductTypes) {
                if (ErrorCode.TOKEN_INVALID.equals(roomProductTypes.getError_code())) {
                    mView.tokenInvalid(roomProductTypes.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(roomProductTypes.getError_code())) {
                    mView.onError(roomProductTypes.getMsg());
                } else if (ErrorCode.SUCCEED.equals(roomProductTypes.getError_code())) {
                    mView.showRoomProductTypes(roomProductTypes.getPartTypeList(), roomProductTypes.getPosition());
                } else {
                    mView.showRoomProductTypes(null, roomProductTypes.getPosition());
                }

            }

            @Override
            public void onError(Throwable e) {
                mView.showRoomProductTypes(null, 0);
            }
        }));

    }

    @Override
    public void onStart() {

    }
}
