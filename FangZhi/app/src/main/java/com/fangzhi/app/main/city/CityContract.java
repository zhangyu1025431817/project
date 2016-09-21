package com.fangzhi.app.main.city;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.Area;
import com.fangzhi.app.bean.City;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/20.
 */
public interface CityContract {
    interface Model extends BaseModel {
        Observable<Area> getCities();
    }

    interface View extends BaseView {
        void setCities(List<City> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getCityList();

    }
}
