package com.fangzhi.app.main.house;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.County;
import com.fangzhi.app.bean.CountyHouses;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.bean.LoginBean;

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
        Observable<LoginBean> changeParent(String token, String userId, String parentId);
    }

    interface View extends BaseView {
        void showHousesList(List<Houses.House> houseList);
        void showNoMoreHouseList(List<Houses.House> houseList);
        void showCountyList(List<County> countyList);
        void changeSucceed(String token);
        void changeFailed(String msg);
        String getToken();
        String getUserId();
        String getAreaCode();
        String getKey();
        String getCountyId();
        String getParentId();
        int getPageSize();
        int getCurrentPage();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getHousesList();
        abstract void getCountyHousesList();
        abstract void searchHouseList();
        abstract void changeParent();
    }
}
