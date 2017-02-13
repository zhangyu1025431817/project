package com.buqi.app.login;

import com.buqi.app.base.BaseModel;
import com.buqi.app.base.BasePresenter;
import com.buqi.app.base.BaseView;
import com.buqi.app.bean.BaseResponseBean;
import com.buqi.app.bean.LocationArea;
import com.buqi.app.bean.LoginBean;
import com.buqi.app.bean.LoginNewBean;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/8/30.
 */
public interface LoginContract {
    interface Model extends BaseModel {
        Observable<LoginBean> login(String deviceCode, String account, String password);
        Observable<LoginNewBean> loginNew(String deviceCode, String account,
                                          String password,String deviceSize,String width,String height,String isPad,String screenLayout);

        Observable<BaseResponseBean> getMsgCode(String phone, String key);

        Observable<BaseResponseBean> checkMsgCode(String phone, String code, int type);

        Observable<LocationArea> getLocationArea(String id);

        Observable<BaseResponseBean> register(String phone,String pwd,String userName,int sex
        ,String companyName,String address,String province,String city,String county,String scope,String key,String code);
        Observable<BaseResponseBean> modificationPassword(String phone,String password,String key);
    }

    interface View extends BaseView {
        String getDeviceId();

        String getPhoneNumber();

        String getPassword();

        String getCodePhone();

        String getKey();

        String getCode();
        String isPad();
        String getScreenLayout();

        int getType();
        String getLoginPwd();
        String getUserName();
        int getSex();
        String getCompanyName();
        String getAddress();
        String getProvinceCode();
        String getCityCode();
        String getCountyCode();
        String getScope();
        String getRegisterVcode();

        String getNewPassword();
        String getDeviceRealSize();
        String getScreenWidth();
        String getScreenHeight();

        //登录
        void loginSucceed(String img);
        void loginSucceedMultiple();
        void loginFailed(String msg);

        void checkMsgCodeSucceed();
        void checkMsgCodeFailed(String msg);

        void registerSucceed();
        void registerFailed(String msg);

        void modificationPasswordFailed(String msg);
        void modificationPasswordSucceed();

        void showProvince(List<LocationArea.Location> list);
        void showCity(List<LocationArea.Location> list);
        void showCounty(List<LocationArea.Location> list);

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void login();
        abstract void loginNew();
        abstract void getMsgCode();

        abstract void checkCode();

        abstract void register();
        abstract void getProvince();
        abstract void getCity();
        abstract void getCounty();
        abstract void modificationPassword();
    }
}
