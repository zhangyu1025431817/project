package com.buyiren.app.main.house.type_detail;

import com.buyiren.app.base.BaseModel;
import com.buyiren.app.base.BasePresenter;
import com.buyiren.app.base.BaseView;
import com.buyiren.app.bean.HouseTypeDetails;

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
