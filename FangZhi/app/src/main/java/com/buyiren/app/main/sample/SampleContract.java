package com.buyiren.app.main.sample;

import com.buyiren.app.base.BaseModel;
import com.buyiren.app.base.BasePresenter;
import com.buyiren.app.base.BaseView;
import com.buyiren.app.bean.CooperativeResponseBean;
import com.buyiren.app.bean.DDDTypeResponseBean;
import com.buyiren.app.bean.SceneLabel;
import com.buyiren.app.bean.SceneLabelResponseBean;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface SampleContract {
    interface Model extends BaseModel {
        Observable<CooperativeResponseBean> getCooperativeList(String token);
        Observable<SceneLabelResponseBean> getTagList(String token,String id);
    }

    interface View extends BaseView {
        String getToken();
        String getId();

        void showAll(ArrayList<DDDTypeResponseBean.DDDType> typeList);
        void showCooperativeList(ArrayList<CooperativeResponseBean.Cooperative> cooperativeList
        ,ArrayList<DDDTypeResponseBean.DDDType> allList);
        void showTagList(ArrayList<SceneLabel>  labelList);
        void show3D2DList(ArrayList<DDDTypeResponseBean.DDDType> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getCooperativeList();
        abstract void  getTagList();
    }
}
