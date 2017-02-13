package com.buqi.app.main.room;

import com.buqi.app.bean.RoomProductTypes;
import com.buqi.app.bean.SameScenes;
import com.buqi.app.network.MySubscriber;
import com.buqi.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/9/22.
 */
public class RoomPresenter extends RoomContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    void getRoomPartTypeList() {
        mRxManager.add(mModel.getRoomPartTypes(mView.getToken(), mView.getHotType(),
                mView.getUserId(), mView.getSceneId(), mView.getHlCode()).subscribe(new MySubscriber<RoomProductTypes>() {
            @Override
            public void onNext(RoomProductTypes roomProductTypes) {
                //服务器返回的数据结构不统一无法进行统一拦截处理
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
    void getSameScene() {
        mRxManager.add(mModel.getScenes(mView.getToken(), mView.getSceneId()).subscribe(new MySubscriber<SameScenes>() {
            @Override
            public void onNext(SameScenes scenes) {
                if (ErrorCode.TOKEN_INVALID.equals(scenes.getError_code())) {
                    mView.tokenInvalid(scenes.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(scenes.getError_code())) {
                    mView.onError(scenes.getMsg());
                } else if (ErrorCode.SUCCEED.equals(scenes.getError_code())) {
                    mView.showSameScene(scenes.getAttachSceneList());
                } else {
                    mView.showSameScene(null);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.showSameScene(null);
            }
        }));
    }
}
