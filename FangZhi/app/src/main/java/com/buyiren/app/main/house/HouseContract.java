package com.buyiren.app.main.house;

import com.buyiren.app.base.BaseModel;
import com.buyiren.app.base.BasePresenter;
import com.buyiren.app.base.BaseView;
import com.buyiren.app.bean.County;
import com.buyiren.app.bean.CountyHouses;
import com.buyiren.app.bean.Houses;

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
