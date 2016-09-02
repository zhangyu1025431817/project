package com.fangzhi.app.main;

import com.fangzhi.app.bean.HousesResponseBean;
import com.fangzhi.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/9/1.
 */
public class MainModel implements MainContract.Model {
    @Override
    public Observable<HousesResponseBean> getHousesList(String token, String areaCode, int pageSize, int curPage) {
        return NetWorkRequest.getHousesList(token,areaCode,pageSize,curPage);
    }
}
