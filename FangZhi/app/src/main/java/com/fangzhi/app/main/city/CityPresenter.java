package com.fangzhi.app.main.city;

import com.fangzhi.app.bean.Area;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/9/20.
 */
public class CityPresenter extends CityContract.Presenter {
    @Override
    void getCityList() {
        mRxManager.add(mModel.getCities(mView.getToken()).subscribe(new MySubscriber<Area>() {
            @Override
            public void onNext(Area area) {
                if (ErrorCode.TOKEN_INVALID.equals(area.getError_code())) {
                    mView.tokenInvalid(area.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(area.getError_code())) {
                    mView.onError(area.getMsg());
                } else if (ErrorCode.SUCCEED.equals(area.getError_code())) {
                    mView.setCities(area.getAreaList());
                } else {
                    mView.setCities(null);
                }

            }

            @Override
            public void onError(Throwable e) {
                mView.setCities(null);
            }
        }));
    }


    @Override
    public void onStart() {

    }
}
