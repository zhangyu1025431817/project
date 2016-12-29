package com.buyiren.app.main.house.scene;

import com.buyiren.app.base.BaseModel;
import com.buyiren.app.base.BasePresenter;
import com.buyiren.app.base.BaseView;
import com.buyiren.app.bean.RoomProductType;
import com.buyiren.app.bean.RoomProductTypes;
import com.buyiren.app.bean.Scene;
import com.buyiren.app.bean.Scenes;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface SceneContract {
    interface Model extends BaseModel {
        Observable<Scenes> getScene(String token, String userId, String hotType,String decorateId);
        Observable<RoomProductTypes> getRoomPartTypes(String token, String hotType, String userId,
                                                      String sceneId, String hlCode);
    }

    interface View extends BaseView {
        String getToken();
        String getHouseHotTypeId();
        String getDecorateId();
        String getUserId();
        String getHotType();
        String getSceneId();
        String getHlCode();
        void showScene(List<Scene> list);
        void showRoomProductTypes(ArrayList<RoomProductType> list, int position);

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract public void getScenes();
        abstract public void getRoomPartTypeList();
    }
}
