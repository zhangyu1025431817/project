package com.fangzhi.app.login.regist;

import com.fangzhi.app.bean.BaseResponseBean;

import rx.Observable;

/**
 * Created by smacr on 2016/10/22.
 */
public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<BaseResponseBean> sendMsgCode(String phone) {
        return null;
    }

    @Override
    public Observable<BaseResponseBean> register(String phone, String code, int type) {
        return null;
    }
}
