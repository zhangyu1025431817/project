package com.fangzhipro.app.main.room;

import com.fangzhipro.app.bean.RoomProductTypes;
import com.fangzhipro.app.network.MySubscriber;
import com.fangzhipro.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/9/22.
 */
public class RoomPresenter extends RoomContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    void getRoomPartTypeList() {
        mRxManager.add(mModel.getRoomPartTypes(mView.getToken(),mView.getHotType(),
                mView.getUserId(),mView.getSceneId(),mView.getHlCode()).subscribe(new MySubscriber<RoomProductTypes>(){
            @Override
            public void onNext(RoomProductTypes roomProductTypes) {
                if (ErrorCode.TOKEN_INVALID.equals(roomProductTypes.getError_code())) {
                    mView.tokenInvalid(roomProductTypes.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(roomProductTypes.getError_code())) {
                    mView.onError(roomProductTypes.getMsg());
                } else if (ErrorCode.SUCCEED.equals(roomProductTypes.getError_code())) {
                    mView.showRoomProductTypes(roomProductTypes.getPartTypeList(),roomProductTypes.getPosition());
                } else {
                    mView.showRoomProductTypes(null,roomProductTypes.getPosition());
                }

            }

            @Override
            public void onError(Throwable e) {
                mView.showRoomProductTypes(null,0);
            }
        }));
    }
}
