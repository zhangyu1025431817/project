package com.fangzhi.app.main.house_type;

import com.fangzhi.app.bean.HouseTypes;
import com.fangzhi.app.network.MySubscriber;

import java.util.ArrayList;

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
                mView.showHouseTypes(houseTypes.getHouseList());
            }

            @Override
            public void onError(Throwable e) {
                mView.showHouseTypes(new ArrayList<HouseTypes.HouseType>());
            }
        }));
    }

    @Override
    public void onStart() {

    }
}
