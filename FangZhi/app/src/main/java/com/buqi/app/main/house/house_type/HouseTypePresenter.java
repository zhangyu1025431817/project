package com.buqi.app.main.house.house_type;

import com.buqi.app.bean.HouseTypes;
import com.buqi.app.network.MySubscriber;
import com.buqi.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/9/21.
 */
public class HouseTypePresenter extends HouseTypeContract.Presenter {
    @Override
    public void getHouseTypes() {
        String token = mView.getToken();
        String id = mView.getHouseId();
        mRxManager.add(mModel.getHouseTypes(token,id).subscribe(new MySubscriber<HouseTypes>(){
            @Override
            public void onNext(HouseTypes houseTypes) {
                if (ErrorCode.TOKEN_INVALID.equals(houseTypes.getError_code())) {
                    mView.tokenInvalid(houseTypes.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(houseTypes.getError_code())) {
                    mView.onError(houseTypes.getMsg());
                } else if (ErrorCode.SUCCEED.equals(houseTypes.getError_code())) {
                    mView.showHouseTypes(houseTypes.getHouseList());
                } else {
                    mView.showHouseTypes(null);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.showHouseTypes(null);
            }
        }));
    }

    @Override
    public void onStart() {

    }
}
