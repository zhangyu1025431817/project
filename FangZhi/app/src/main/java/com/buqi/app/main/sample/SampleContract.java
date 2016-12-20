package com.buqi.app.main.sample;

import com.buqi.app.base.BaseModel;
import com.buqi.app.base.BasePresenter;
import com.buqi.app.base.BaseView;
import com.buqi.app.bean.CooperativeResponseBean;
import com.buqi.app.bean.DDDTypeResponseBean;
import com.buqi.app.bean.SceneLabel;
import com.buqi.app.bean.SceneLabelResponseBean;

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
