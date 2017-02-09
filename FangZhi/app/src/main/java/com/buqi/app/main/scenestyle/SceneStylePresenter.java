package com.buqi.app.main.scenestyle;

import com.buqi.app.bean.SceneStyleResponse;
import com.buqi.app.network.MySubscriber;
import com.buqi.app.network.http.api.ErrorCode;

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
}