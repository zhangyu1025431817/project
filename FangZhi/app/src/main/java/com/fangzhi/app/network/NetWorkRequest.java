package com.fangzhi.app.network;

import com.fangzhi.app.bean.Area;
import com.fangzhi.app.bean.HouseTypeDetails;
import com.fangzhi.app.bean.HouseTypes;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.RoomProductTypes;
import com.fangzhi.app.bean.Scenes;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhangyu on 2016/6/15.
 */
public class NetWorkRequest {

    public static Observable<LoginBean> login(String account, String password,String deviceId) {
        return Network.getApiService().login(account, password,deviceId,"A")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<LoginBean> login(String token) {
        return Observable.just(token)
                .flatMap(new Func1<String, Observable<LoginBean>>() {
                    @Override
                    public Observable<LoginBean> call(String token) {
                        return token.isEmpty()
                                ? Observable.<LoginBean>error(new NullPointerException("Token is null!"))
                                : Network.getApiService().loginToken(token);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<Area> getCities(String token) {
        return Network.getApiService().getCities(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Houses> getHouses(String token, String id, int pageCount, int page) {
        return Network.getApiService().getHouses(token, id, pageCount, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Houses> searchHouse(String token, String id, String name, int pageSize, int page) {
        return Network.getApiService().searchHouse(token, id, name, pageSize, page)
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

    public static Observable<Scenes> getScenes(String token, String userId, String hotType) {
        return Network.getApiService().getScenes(token, hotType, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<RoomProductTypes> getRoomProductTypes(String token, String hotType,
                                                                   String userId, String sceneId) {
        return Network.getApiService().getRoomProductTypes(token, hotType, userId, sceneId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
