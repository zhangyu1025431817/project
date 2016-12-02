package com.fangzhi.app.login;


import com.fangzhi.app.bean.BaseResponseBean;
import com.fangzhi.app.bean.LocationArea;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.LoginNewBean;
import com.fangzhi.app.network.NetWorkRequest;
import com.fangzhi.app.network.Network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/8/30.
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginBean> login(String deviceCode, String account, String password) {
        return NetWorkRequest.login(account, password, deviceCode);
    }

    @Override
    public Observable<LoginNewBean> loginNew(String deviceCode, String account, String password
    ,String deviceSize,String width,String height) {
        return Network.getApiService().loginNew(account,password,deviceCode,"A",deviceSize,width,height)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseResponseBean> getMsgCode(String phone, String key) {
        return Network.getApiService().getMsgCode(phone, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseResponseBean> checkMsgCode(String phone, String code, int type) {
        return Network.getApiService().checkMsgCode(phone, code, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<LocationArea> getLocationArea(String id) {
        return Network.getApiService().getLocationArea(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseResponseBean> register(String phone, String pwd, String userName, int sex,
                                                 String companyName, String address, String province,
                                                 String city, String county, String scope, String key,String code) {
        return Network.getApiService().registerExperienceUser(phone, pwd, userName, sex, companyName, address,
                province, city, county, scope, key,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseResponseBean> modificationPassword(String phone, String password, String key) {
        return Network.getApiService().modificationPassword(phone,password,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
