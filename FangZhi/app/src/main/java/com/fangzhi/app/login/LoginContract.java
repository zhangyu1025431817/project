package com.fangzhi.app.login;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.LoginBean;

import rx.Observable;

/**
 * Created by smacr on 2016/8/30.
 */
public interface LoginContract {
    interface Model extends BaseModel{
        Observable<LoginBean> login(String deviceCode, String account, String password);
        Observable<LoginBean> login(String token);
    }
    interface View extends BaseView{
        String getDeviceId();
        String getPhoneNumber();
        String getPassword();
        //登录
        void loginSucceed(String url);
        void loginFailed(String msg);

    }
    abstract class Presenter extends BasePresenter<Model,View>{
        abstract public void login();
    }
}
