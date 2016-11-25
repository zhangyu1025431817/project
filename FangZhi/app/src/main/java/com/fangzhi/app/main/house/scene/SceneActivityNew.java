package com.fangzhi.app.main.house.scene;

import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.Scene;

import java.util.List;

/**
 * Created by smacr on 2016/11/24.
 */
public class SceneActivityNew extends BaseActivity<ScenePresenter,SceneModel> implements SceneContract.View {

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getHouseHotTypeId() {
        return null;
    }

    @Override
    public String getDecorateId() {
        return null;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public void showScene(List<Scene> list) {

    }

    @Override
    public void tokenInvalid(String msg) {

    }

    @Override
    public void onError(String msg) {

    }
}
