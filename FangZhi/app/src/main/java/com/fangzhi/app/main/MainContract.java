package com.fangzhi.app.main;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.Houses;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/1.
 */
public interface MainContract {
    interface Model extends BaseModel {
        Observable<Houses> getHousesList(String token, String areaCode, int pageSize, int curPage);
        Observable<Houses> searchHouseList(String token,String areaId,String key,int pageSize,int curPage);
    }

    interface View extends BaseView {
        void showHousesList(List<Houses.House> housingEstateList);
        String getToken();
        String getAreaCode();
        String getKey();
        int getPageSize();
        int getCurrentPage();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getHousesList();
        abstract void searchHouseList();
    }
}
