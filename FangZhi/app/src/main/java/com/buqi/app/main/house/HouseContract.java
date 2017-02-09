package com.buqi.app.main.house;

import com.buqi.app.base.BaseModel;
import com.buqi.app.base.BasePresenter;
import com.buqi.app.base.BaseView;
import com.buqi.app.bean.County;
import com.buqi.app.bean.CountyHouses;
import com.buqi.app.bean.Houses;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/1.
 */
public interface HouseContract {
    interface Model extends BaseModel {
        Observable<CountyHouses> getHousesList(String token, String areaCode, int pageSize, int curPage);
        Observable<Houses> getCountyHousesList(String token, String id);
        Observable<CountyHouses> searchHouseList(String token, String areaId, String key, int pageSize, int curPage);
    }

    interface View extends BaseView {
        void showHousesList(List<Houses.House> houseList);
        void showNoMoreHouseList(List<Houses.House> houseList);
        void showCountyList(List<County> countyList);
        String getToken();
        String getUserId();
        String getAreaCode();
        String getKey();
        String getCountyId();
        int getPageSize();
        int getCurrentPage();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getHousesList();
        abstract void getCountyHousesList();
        abstract void searchHouseList();
    }
}