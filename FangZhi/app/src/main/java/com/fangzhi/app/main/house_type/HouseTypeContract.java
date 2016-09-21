package com.fangzhi.app.main.house_type;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.HouseTypes;

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
