package com.buyiren.app.main.city;

import com.buyiren.app.bean.Area;
import com.buyiren.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/9/20.
 */
public class CityModel implements CityContract.Model {
    @Override
    public Observable<Area> getCities(String token) {
        return NetWorkRequest.getCities(token);
    }
}
