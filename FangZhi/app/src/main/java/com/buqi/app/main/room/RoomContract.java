package com.buqi.app.main.room;

import com.buqi.app.base.BaseModel;
import com.buqi.app.base.BasePresenter;
import com.buqi.app.base.BaseView;
import com.buqi.app.bean.RoomProductType;
import com.buqi.app.bean.RoomProductTypes;
import com.buqi.app.bean.SameScenes;
import com.buqi.app.bean.Scene;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface RoomContract {
    interface Model extends BaseModel {
        Observable<RoomProductTypes> getRoomPartTypes(String token, String hotType, String userId,
                                                      String sceneId, String hlCode);
        Observable<SameScenes> getScenes(String token, String sceneId);
    }

    interface View extends BaseView {
        String getToken();

        String getHotType();

        String getUserId();

        String getSceneId();

        String getHlCode();

        void showRoomProductTypes(List<RoomProductType> list, int position);
        void showSameScene(List<Scene> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getRoomPartTypeList();

        abstract void getSameScene();
    }
}
