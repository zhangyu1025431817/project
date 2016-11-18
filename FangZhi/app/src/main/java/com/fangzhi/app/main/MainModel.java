package com.fangzhi.app.main;

import com.fangzhi.app.bean.CountyHouses;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.network.NetWorkRequest;
import com.fangzhi.app.network.Network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/9/1.
 */
public class MainModel implements MainContract.Model {
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

    @Override
    public Observable<LoginBean> changeParent(String token, String userId, String parentId) {
        return Network.getApiService().loginParent(token, parentId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
