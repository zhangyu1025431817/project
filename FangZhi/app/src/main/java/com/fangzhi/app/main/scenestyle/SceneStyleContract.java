package com.fangzhi.app.main.scenestyle;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.SceneStyleResponse;

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
