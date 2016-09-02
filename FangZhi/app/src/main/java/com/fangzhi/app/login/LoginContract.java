package com.fangzhi.app.login;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.BaseResponseBean;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.SetPasswordBean;

import rx.Observable;

/**
 * Created by smacr on 2016/8/30.
 */
public interface LoginContract {
    interface Model extends BaseModel{
        Observable<LoginBean> login(String account,String password);
        Observable<LoginBean> login(String token);
        Observable<BaseResponseBean> getMessageCode(String phoneNumber);
        Observable<BaseResponseBean> resetPwd(String phoneNumber,String code,String newPwd);
        Observable<SetPasswordBean> setPassword(String token, String password);
    }
    interface View extends BaseView{
        //登录
        void loginSucceed();
        void loginFailed(String msg);
        //获取验证码
        void getCodeSucceed();
        void getCodeFailed(String msg);

        //设置密码
        void setPwdSucceed();
        void setPwdFailed(String msg);

        //重置密码
        void resetPwdSucceed();
        void resetPwdFailed(String msg);


    }
    abstract class Presenter extends BasePresenter<Model,View>{
        abstract public void login(String account,String password);
        abstract public void getMessageCode(String phoneNumber);
        abstract public void resetPwd(String phoneNumber,String code,String newPwd);
        abstract public void setPassword(String password);
    }
}
