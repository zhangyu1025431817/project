package com.fangzhipro.app.main.house.type_detail;

import com.fangzhipro.app.base.BaseModel;
import com.fangzhipro.app.base.BasePresenter;
import com.fangzhipro.app.base.BaseView;
import com.fangzhipro.app.bean.HouseTypeDetails;

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
