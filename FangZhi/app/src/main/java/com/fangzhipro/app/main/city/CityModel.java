package com.fangzhipro.app.main.city;

import com.fangzhipro.app.bean.Area;
import com.fangzhipro.app.network.NetWorkRequest;

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
