package com.buyiren.app.main.scenestyle;

import com.buyiren.app.bean.RoomProductTypes;
import com.buyiren.app.bean.SceneStyleResponse;
import com.buyiren.app.network.MySubscriber;
import com.buyiren.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/9/22.
 */
public class SceneStylePresenter extends SceneStyleContract.Presenter {


    @Override
    public void onStart() {

    }

    @Override
    public void getStyleScenes() {
        mRxManager.add(mModel.getStyleScene(mView.getToken()).subscribe(new MySubscriber<SceneStyleResponse>(){
            @Override
            public void onNext(SceneStyleResponse sceneStyleResponse) {
                if (ErrorCode.SUCCEED.equals(sceneStyleResponse.getError_code())) {
                    mView.showScene(sceneStyleResponse.getStyleList());
                }else{
                    mView.onError(sceneStyleResponse.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError("网络连接失败!");
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
}
