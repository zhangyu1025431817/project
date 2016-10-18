package com.fangzhi.app.login;

import com.fangzhi.app.MyApplication;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.http.api.ErrorCode;
import com.fangzhi.app.tools.SPUtils;

/**
 * Created by smacr on 2016/8/30.
 */
public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void onStart() {

    }

    @Override
    public void login() {
        mRxManager.add(mModel.login(mView.getDeviceId(), mView.getPhoneNumber(), mView.getPassword())
                .subscribe(new MySubscriber<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {

                        if (ErrorCode.SUCCEED.equals(loginBean.getError_code())) {
                            SPUtils.put(MyApplication.getContext(), SpKey.TOKEN,loginBean.getToken());
                            SPUtils.put(MyApplication.getContext(), SpKey.USER_NAME,mView.getPhoneNumber());
                            SPUtils.put(MyApplication.getContext(), SpKey.PASSWORD,mView.getPassword());
                            SPUtils.put(MyApplication.getContext(), SpKey.USER_ID,loginBean.getUserID());
                            mView.loginSucceed(loginBean.getImg());
                        } else {
                            mView.loginFailed(loginBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loginFailed("服务器连接失败");
                    }
                }));
    }
}
