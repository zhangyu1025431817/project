package com.buqi.app.main.decoration;

import com.buqi.app.bean.SellType;
import com.buqi.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/10/21.
 */
public class SellPartModel implements SellPartContract.Model {
    @Override
    public Observable<SellType> getSellCategory(String token, String userId) {
            return NetWorkRequest.getSellCategory(token,userId);
    }
}