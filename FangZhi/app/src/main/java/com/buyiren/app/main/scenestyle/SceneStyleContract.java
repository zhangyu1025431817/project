package com.buyiren.app.main.scenestyle;

import com.buyiren.app.base.BaseModel;
import com.buyiren.app.base.BasePresenter;
import com.buyiren.app.base.BaseView;
import com.buyiren.app.bean.RoomProductType;
import com.buyiren.app.bean.RoomProductTypes;
import com.buyiren.app.bean.SceneStyleResponse;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface SceneStyleContract {
    interface Model extends BaseModel {
        Observable<SceneStyleResponse> getStyleScene(String token);
        Observable<RoomProductTypes> getRoomPartTypes(String token, String hotType, String userId,
                                                      String sceneId, String hlCode);
    }

    interface View extends BaseView {
        String getToken();
        String getUserId();
        String getHotType();
        String getSceneId();
        String getHlCode();
        void showScene(List<SceneStyleResponse.SceneStyle> styleList);
        void showRoomProductTypes(ArrayList<RoomProductType> list, int position);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract public void getStyleScenes();
        abstract public void getRoomPartTypeList();
    }
}
