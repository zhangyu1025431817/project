package com.buqi.app.main.house;

import com.buqi.app.bean.CountyHouses;
import com.buqi.app.bean.Houses;
import com.buqi.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/9/1.
 */
public class HouseModel implements HouseContract.Model {
    @Override
    public Observable<CountyHouses> getHousesList(String token, String areaCode, int pageSize, int curPage) {
        return NetWorkRequest.getHouses(token,areaCode,pageSize,curPage);
    }

    @Override
    public Observable<Houses> getCountyHousesList(String token, String id) {
        return NetWorkRequest.getCountyHouse(token,id);
    }

    @Override
    public Observable<CountyHouses> searchHouseList(String token, String areaId, String key, int pageSize, int curPage) {
        return NetWorkRequest.searchHouse(token,areaId,key,pageSize,curPage);
    }
}
