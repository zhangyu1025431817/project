package com.fangzhi.app.login;

import com.fangzhi.app.MyApplication;
import com.fangzhi.app.bean.BaseResponseBean;
import com.fangzhi.app.bean.LocationArea;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.LoginNewBean;
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

    @Override
    void loginNew() {
        mRxManager.add(mModel.loginNew(mView.getDeviceId(), mView.getPhoneNumber(), mView.getPassword())
                .subscribe(new MySubscriber<LoginNewBean>() {
                    @Override
                    public void onNext(LoginNewBean loginBean) {
                        if (ErrorCode.SUCCEED.equals(loginBean.getError_code())) {
                            SPUtils.put(MyApplication.getContext(), SpKey.TOKEN,loginBean.getToken());
                            SPUtils.put(MyApplication.getContext(), SpKey.USER_NAME,mView.getPhoneNumber());
                            SPUtils.put(MyApplication.getContext(), SpKey.PASSWORD,mView.getPassword());
                            SPUtils.put(MyApplication.getContext(), SpKey.USER_ID,loginBean.getUserID());
                            mView.loginSucceed(loginBean.getImg());
                        } else if( ErrorCode.PARENT_MULTIPLE.equals(loginBean.getError_code())){
                            SPUtils.put(MyApplication.getContext(), SpKey.TOKEN,loginBean.getToken());
                            SPUtils.put(MyApplication.getContext(), SpKey.USER_NAME,mView.getPhoneNumber());
                            SPUtils.put(MyApplication.getContext(), SpKey.PASSWORD,mView.getPassword());
                            SPUtils.put(MyApplication.getContext(), SpKey.USER_ID,loginBean.getUserID());
                            mView.loginSucceedMultiple(loginBean.getParentList());
                        }else{
                            mView.loginFailed(loginBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loginFailed("网络连接失败");
                    }
                }));
    }

    @Override
    void getMsgCode() {
        mRxManager.add(mModel.getMsgCode(mView.getCodePhone(),mView.getKey()).subscribe(new MySubscriber<BaseResponseBean>(){
            @Override
            public void onNext(BaseResponseBean bean) {
                if(!ErrorCode.SUCCEED.equals(bean.getError_code())){
                    mView.onError(bean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError("服务器连接失败！");
            }
        }));
    }

    @Override
    void checkCode() {
        mRxManager.add(mModel.checkMsgCode(mView.getCodePhone(),mView.getCode(),mView.getType()).subscribe(new MySubscriber<BaseResponseBean>(){
            @Override
            public void onNext(BaseResponseBean bean) {
                if(!ErrorCode.SUCCEED.equals(bean.getError_code())){
                    mView.checkMsgCodeFailed(bean.getMsg());
                }else {
                    mView.checkMsgCodeSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.checkMsgCodeFailed("服务器连接失败！");
            }
        }));
    }

    @Override
    void register() {
        mRxManager.add(mModel.register(mView.getCodePhone(),mView.getLoginPwd(),mView.getUserName(),mView.getSex(),
                mView.getCompanyName(),mView.getAddress(),mView.getProvinceCode(),mView.getCityCode(),mView.getCountyCode()
        ,mView.getScope(),mView.getKey()).subscribe(new MySubscriber<BaseResponseBean>(){
            @Override
            public void onNext(BaseResponseBean bean) {
                if(!ErrorCode.SUCCEED.equals(bean.getError_code())){
                    mView.registerFailed(bean.getMsg());
                }else {
                    mView.registerSucceed();
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.registerFailed("服务器连接失败！");
            }
        }));
    }

    @Override
    void getProvince() {
        mRxManager.add(mModel.getLocationArea("").subscribe(new MySubscriber<LocationArea>(){
            @Override
            public void onNext(LocationArea locationArea) {
                if(!ErrorCode.SUCCEED.equals(locationArea.getError_code())){
                    mView.onError(locationArea.getMsg());
                }else {
                    mView.showProvince(locationArea.getAreaList());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError("服务器连接失败");
            }
        }));
    }

    @Override
    void getCity() {
        mRxManager.add(mModel.getLocationArea(mView.getProvinceCode()).subscribe(new MySubscriber<LocationArea>(){
            @Override
            public void onNext(LocationArea locationArea) {
                if(!ErrorCode.SUCCEED.equals(locationArea.getError_code())){
                    mView.onError(locationArea.getMsg());
                }else {
                    mView.showCity(locationArea.getAreaList());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError("服务器连接失败");
            }
        }));
    }

    @Override
    void getCounty() {
        mRxManager.add(mModel.getLocationArea(mView.getCityCode()).subscribe(new MySubscriber<LocationArea>(){
            @Override
            public void onNext(LocationArea locationArea) {
                if(!ErrorCode.SUCCEED.equals(locationArea.getError_code())){
                    mView.onError(locationArea.getMsg());
                }else {
                    mView.showCounty(locationArea.getAreaList());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError("服务器连接失败");
            }
        }));
    }

    @Override
    void modificationPassword() {
        mRxManager.add(mModel.modificationPassword(mView.getCodePhone(),mView.getNewPassword(),mView.getKey()).
                subscribe(new MySubscriber<BaseResponseBean>(){
                    @Override
                    public void onNext(BaseResponseBean bean) {
                        if(!ErrorCode.SUCCEED.equals(bean.getError_code())){
                            mView.modificationPasswordFailed(bean.getMsg());
                        }else {
                            mView.modificationPasswordSucceed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.modificationPasswordFailed("服务器连接失败");
                    }
                }));
    }
}
