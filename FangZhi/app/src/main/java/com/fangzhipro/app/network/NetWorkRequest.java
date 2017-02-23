package com.fangzhipro.app.network;

import com.fangzhipro.app.bean.Area;
import com.fangzhipro.app.bean.CountyHouses;
import com.fangzhipro.app.bean.HouseTypeDetails;
import com.fangzhipro.app.bean.HouseTypes;
import com.fangzhipro.app.bean.Houses;
import com.fangzhipro.app.bean.LoginBean;
import com.fangzhipro.app.bean.RoomProductTypes;
import com.fangzhipro.app.bean.Scenes;
import com.fangzhipro.app.bean.SellType;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangyu on 2016/6/15.
 */
public class NetWorkRequest {

    public static Observable<LoginBean> login(String account, String password, String deviceId) {
        return Network.getApiService().login(account, password, deviceId, "A")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Area> getCities(String token) {
        return Network.getApiService().getCities(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<CountyHouses> getHouses(String token, String id, int pageCount, int page) {
        return Network.getApiService().getHouses(token, id, pageCount, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<CountyHouses> searchHouse(String token, String id, String name, int pageSize, int page) {
        return Network.getApiService().searchHouse(token, id, name, pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Houses> getCountyHouse(String token, String id) {
        return Network.getApiService().getCountyHouses(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<HouseTypes> getHouseTypes(String token, String id) {
        return Network.getApiService().getHouseTypes(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<HouseTypeDetails> getHouseTypeDetails(String token, String id) {
        return Network.getApiService().getHouseTypeDetails(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Scenes> getScenes(String token, String userId, String hotType, String decorateId) {
        return Network.getApiService().getScenes(token, hotType, userId, decorateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<RoomProductTypes> getRoomProductTypes(String token, String hotType,
                                                                   String userId, String sceneId, String hlCode) {
        return Network.getApiService().getRoomProductTypes(token, hotType, userId, sceneId, hlCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<SellType> getSellCategory(String token, String userId) {
        return Network.getApiService().getSellCategory(token, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
