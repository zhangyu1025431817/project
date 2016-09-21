package com.fangzhi.app.main.city;

import com.fangzhi.app.bean.Area;
import com.fangzhi.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/9/20.
 */
public class CityModel implements CityContract.Model {
    @Override
    public Observable<Area> getCities() {
        String token = "123";
        return NetWorkRequest.getCities(token);
    }
}
