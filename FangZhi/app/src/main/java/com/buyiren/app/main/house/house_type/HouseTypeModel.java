package com.buyiren.app.main.house.house_type;

import com.buyiren.app.bean.HouseTypes;
import com.buyiren.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public class HouseTypeModel implements HouseTypeContract.Model {
    @Override
    public Observable<HouseTypes> getHouseTypes(String token, String houseId) {
        return NetWorkRequest.getHouseTypes(token,houseId);
    }
}
