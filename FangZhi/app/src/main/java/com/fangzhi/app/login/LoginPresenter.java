package com.fangzhi.app.login;

import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.network.MySubscriber;

/**
 * Created by smacr on 2016/8/30.
 */
public class LoginPresenter extends LoginContract.Presenter {
//    @Override
//    public void login(String account, String password) {
//        mRxManager.add(mModel.login(account, password).subscribe(new MySubscriber<LoginBean>() {
//            @Override
//            public void onNext(LoginBean loginBean) {
//                if (RequestCodeMessage.verificationResponseCode(loginBean)) {
//                    SPUtils.put(MyApplication.getContext(), SpKey.TOKEN, loginBean.getToken());
//                    mView.loginSucceed();
//                } else {
//                    mView.loginFailed(loginBean.getMsg());
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.loginFailed(e.getMessage());
//            }
//        }));
//    }
//
//    @Override
//    public void getMessageCode(String phoneNumber) {
//        mRxManager.add(mModel.getMessageCode(phoneNumber).subscribe(new MySubscriber<BaseResponseBean>() {
//            @Override
//            public void onNext(BaseResponseBean bean) {
//                if (RequestCodeMessage.verificationResponseCode(bean)) {
//                    mView.getCodeSucceed();
//                }else{
//                    mView.getCodeFailed(bean.getMsg());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.getCodeFailed(e.getMessage());
//            }
//        }));
//    }
//
//    @Override
//    public void resetPwd(String phoneNumber, String code, String newPwd) {
//        mRxManager.add(mModel.resetPwd(phoneNumber, code, newPwd).subscribe(new MySubscriber<BaseResponseBean>() {
//            @Override
//            public void onNext(BaseResponseBean bean) {
//                if (RequestCodeMessage.verificationResponseCode(bean)) {
//                    mView.resetPwdSucceed();
//                } else {
//                    mView.resetPwdFailed(bean.getMsg());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.resetPwdFailed(e.getMessage());
//            }
//        }));
//    }
//
//
//    @Override
//    public void setPassword(String password) {
//        String token = (String) SPUtils.get(MyApplication.getContext(), SpKey.TOKEN, "");
//        mRxManager.add(mModel.setPassword(token, password).subscribe(new MySubscriber<SetPasswordBean>() {
//            @Override
//            public void onNext(SetPasswordBean bean) {
//                if (RequestCodeMessage.verificationResponseCode(bean)) {
//                    SPUtils.put(MyApplication.getContext(), SpKey.TOKEN, bean.getToken());
//                    mView.setPwdSucceed();
//                } else {
//                    mView.setPwdFailed(bean.getMsg());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.setPwdFailed(e.getMessage());
//            }
//        }));
//    }

    @Override
    public void onStart() {

    }

    @Override
    public void login() {
        mRxManager.add(mModel.login(mView.getDeviceId(),mView.getPhoneNumber(),mView.getPassword(),mView.getRandomCode())
                .subscribe(new MySubscriber<LoginBean>(){
            @Override
            public void onNext(LoginBean loginBean) {
                super.onNext(loginBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }));
    }
}
