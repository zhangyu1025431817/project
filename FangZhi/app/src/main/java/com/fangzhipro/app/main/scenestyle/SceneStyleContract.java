package com.fangzhipro.app.main.scenestyle;

import com.fangzhipro.app.base.BaseModel;
import com.fangzhipro.app.base.BasePresenter;
import com.fangzhipro.app.base.BaseView;
import com.fangzhipro.app.bean.SceneStyleResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface SceneStyleContract {
    interface Model extends BaseModel {
        Observable<SceneStyleResponse> getStyleScene(String token);
    }

    interface View extends BaseView {
        String getToken();
        void showScene(List<SceneStyleResponse.SceneStyle> styleList);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract public void getStyleScenes();
    }
}
