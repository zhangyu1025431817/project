package com.buyiren.app.main.house.house_type;

import com.buyiren.app.base.BaseModel;
import com.buyiren.app.base.BasePresenter;
import com.buyiren.app.base.BaseView;
import com.buyiren.app.bean.HouseTypes;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface HouseTypeContract {
    interface Model extends BaseModel {
        Observable<HouseTypes> getHouseTypes(String token,String houseId);
    }

    interface View extends BaseView {
        String getToken();
        String getHouseId();

        void showHouseTypes(List<HouseTypes.HouseType> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract public void getHouseTypes();
    }
}
