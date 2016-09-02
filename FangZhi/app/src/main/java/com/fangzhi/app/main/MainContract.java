package com.fangzhi.app.main;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.HousesResponseBean;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/1.
 */
public interface MainContract {
    interface Model extends BaseModel {
        Observable<HousesResponseBean> getHousesList(String token, String areaCode, int pageSize, int curPage);
    }

    interface View extends BaseView {
        void showHousesList(List<HousesResponseBean.Houses> housingEstateList);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getHousesList(String areaCode, int pageSize, int curPage);
    }
}
