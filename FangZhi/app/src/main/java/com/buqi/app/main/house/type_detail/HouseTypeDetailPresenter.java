package com.buqi.app.main.house.type_detail;

import com.buqi.app.bean.HouseTypeDetails;
import com.buqi.app.network.MySubscriber;
import com.buqi.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/9/22.
 */
public class HouseTypeDetailPresenter extends HouseTypeDetailContract.Presenter {
    @Override
    public void getHouseTypeDetails() {
        mRxManager.add(mModel.getHouseTypeDetails(mView.getToken(), mView.getHouseTypeId())
                .subscribe(new MySubscriber<HouseTypeDetails>() {
                    @Override
                    public void onNext(HouseTypeDetails houseTypeDetails) {
                        if (ErrorCode.TOKEN_INVALID.equals(houseTypeDetails.getError_code())) {
                            mView.tokenInvalid(houseTypeDetails.getMsg());
                        } else if (ErrorCode.SERVER_EXCEPTION.equals(houseTypeDetails.getError_code())) {
                            mView.onError(houseTypeDetails.getMsg());
                        } else if (ErrorCode.SUCCEED.equals(houseTypeDetails.getError_code())) {
                            mView.showHouseTypeDetails(houseTypeDetails.getHotList());
                        } else {
                            mView.showHouseTypeDetails(null);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showHouseTypeDetails(null);
                    }
                }));
    }

    @Override
    public void onStart() {

    }
}
