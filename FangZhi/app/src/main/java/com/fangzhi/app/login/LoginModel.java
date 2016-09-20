package com.fangzhi.app.login;

import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/8/30.
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginBean> login(String deviceCode,String account, String password,String randomCode) {
        return NetWorkRequest.login(account,password);
    }

    @Override
    public Observable<LoginBean> login(String token) {
        return NetWorkRequest.login(token);
    }

}
