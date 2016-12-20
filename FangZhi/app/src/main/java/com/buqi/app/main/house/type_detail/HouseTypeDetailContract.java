package com.buqi.app.main.house.type_detail;

import com.buqi.app.base.BaseModel;
import com.buqi.app.base.BasePresenter;
import com.buqi.app.base.BaseView;
import com.buqi.app.bean.HouseTypeDetails;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface HouseTypeDetailContract {
    interface Model extends BaseModel {
        Observable<HouseTypeDetails> getHouseTypeDetails(String token, String houseId);
    }

    interface View extends BaseView {
        String getToken();
        String getHouseTypeId();

        void showHouseTypeDetails(List<HouseTypeDetails.HouseTypeDetail> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract public void getHouseTypeDetails();
    }
}
