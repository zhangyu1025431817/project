package com.buyiren.app.main.house.type_detail;

import com.buyiren.app.bean.HouseTypeDetails;
import com.buyiren.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/9/22.
 */
public class HouseTypeDetailModel implements  HouseTypeDetailContract.Model{
    @Override
    public Observable<HouseTypeDetails> getHouseTypeDetails(String token, String houseId) {
        return NetWorkRequest.getHouseTypeDetails(token,houseId);
    }
}
