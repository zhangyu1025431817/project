package com.fangzhi.app.login;

import com.fangzhi.app.bean.BaseResponseBean;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.SetPasswordBean;
import com.fangzhi.app.network.NetWorkRequest;

import rx.Observable;

/**
 * Created by smacr on 2016/8/30.
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginBean> login(String account, String password) {
        return NetWorkRequest.login(account,password);
    }

    @Override
    public Observable<LoginBean> login(String token) {
        return NetWorkRequest.login(token);
    }

    @Override
    public Observable<BaseResponseBean> getMessageCode(String phoneNumber) {
        return NetWorkRequest.getMsgCode(phoneNumber);
    }

    @Override
    public Observable<BaseResponseBean> resetPwd(String phoneNumber, String code, String newPwd) {
        return NetWorkRequest.resetPwd(phoneNumber,code,newPwd);
    }


    @Override
    public Observable<SetPasswordBean> setPassword(String token, String password) {
        return NetWorkRequest.setPassword(token,password);
    }
}
