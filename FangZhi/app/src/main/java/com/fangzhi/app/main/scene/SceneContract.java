package com.fangzhi.app.main.scene;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.Scene;
import com.fangzhi.app.bean.Scenes;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface SceneContract {
    interface Model extends BaseModel {
        Observable<Scenes> getScene(String token, String userId, String hotType);
    }

    interface View extends BaseView {
        String getToken();
        String getHouseHotTypeId();
        String getUserId();
        void showScene(List<Scene> list);

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract public void getScenes();
    }
}
