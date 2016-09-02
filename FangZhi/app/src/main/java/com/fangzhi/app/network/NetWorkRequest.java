package com.fangzhi.app.network;

import com.fangzhi.app.bean.BaseResponseBean;
import com.fangzhi.app.bean.HousesResponseBean;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.SetPasswordBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhangyu on 2016/6/15.
 */
public class NetWorkRequest {

    public static Observable<LoginBean> login(String account, String password) {
        return Network.getApiService().login(account, password)
                .flatMap((new Func1<LoginBean, Observable<LoginBean>>() {
                    @Override
                    public Observable<LoginBean> call(LoginBean loginBean) {
                        String errorMessage;
                        if (loginBean.getCode() == 1000) {
                            return NetWorkRequest.login(loginBean.getToken());
                        } else if (loginBean.getCode() == 1001) {
                            errorMessage = RequestCodeMessage.PASSWORD_ERROR;
                        } else if (loginBean.getCode() == 1002) {
                            errorMessage = RequestCodeMessage.ACCOUNT_PAST;
                        } else if (loginBean.getCode() == 1003) {
                            errorMessage = RequestCodeMessage.ACCOUNT_NOT_FOND;
                        } else if (loginBean.getCode() == 1004) {
                            errorMessage = RequestCodeMessage.MESSAGE_CODE_PAST;
                        } else {
                            errorMessage = RequestCodeMessage.OTHER_ERROR;
                        }
                        return Observable.error(new Exception(errorMessage));
                    }
                }))
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

    public static Observable<SetPasswordBean> setPassword(String token, String password){
        return Network.getApiService().setPassword(token,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<BaseResponseBean> getMsgCode(String phoneNumber){
        return Network.getApiService().getMsgCode(phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<BaseResponseBean> resetPwd(String phoneNumber,String code,String pwd){
        return Network.getApiService().resetPwd(phoneNumber,code,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<HousesResponseBean> getHousesList(String token,String areaCode,int pageSize,int curPage){
        return Network.getApiService().getHousesList(token,areaCode,pageSize,curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
