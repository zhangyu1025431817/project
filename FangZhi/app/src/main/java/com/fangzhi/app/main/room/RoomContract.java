package com.fangzhi.app.main.room;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.RoomProductType;
import com.fangzhi.app.bean.RoomProductTypes;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface RoomContract {
    interface Model extends BaseModel {
        Observable<RoomProductTypes> getRoomPartTypes(String token,String hotType,String userId,String sceneId);
    }

    interface View extends BaseView {
        String getToken();
        String getHotType();
        String getUserId();
        String getSceneId();
        void showRoomProductTypes(List<RoomProductType> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
       abstract void getRoomPartTypeList();
    }
}
