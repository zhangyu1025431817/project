package com.buyiren.app.main.city;

import com.buyiren.app.base.BaseModel;
import com.buyiren.app.base.BasePresenter;
import com.buyiren.app.base.BaseView;
import com.buyiren.app.bean.Area;
import com.buyiren.app.bean.City;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/20.
 */
public interface CityContract {
    interface Model extends BaseModel {
        Observable<Area> getCities(String token);
    }

    interface View extends BaseView {
        void setCities(List<City> list);
        String getToken();

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getCityList();

    }
}
