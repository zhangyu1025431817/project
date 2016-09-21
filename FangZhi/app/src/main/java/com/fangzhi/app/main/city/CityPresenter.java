package com.fangzhi.app.main.city;

import com.fangzhi.app.bean.Area;
import com.fangzhi.app.network.MySubscriber;

/**
 * Created by smacr on 2016/9/20.
 */
public class CityPresenter extends CityContract.Presenter {
    @Override
    void getCityList() {
        mRxManager.add(mModel.getCities().subscribe(new MySubscriber<Area>(){
            @Override
            public void onNext(Area area) {
                mView.setCities(area.getAreaList());
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
