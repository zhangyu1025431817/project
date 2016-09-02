package com.fangzhi.app.network;

/**
 * Created by zhangyu on 2016/6/15.
 */
public class ApiUrl {
    public static final String BASE_URL = "http://admin.91fzz.com/";
    /**
     * 获取app版本号
     */
    public static final String APP_VERSION = "SmartHouses/app/configAppVersionAction.action";
    /**
     * 首次登录接口
     */
    public static final String USER_LOGIN = "SmartHouses/app/userLoginAction.action";
    /**
     * 自动登录
     */
    public static final String USER_LOGIN_TOKEN = "SmartHouses/app/userTokenLoginAction.action";
    /**
     * 邀请码登录后修改密码
     */
    public static final String SET_PASSWORD = "SmartHouses/app/memberUpdateLoginPwdAction.action";
    /**
     * 获取验证码
     */
    public static final String GET_MSG_CODE = "SmartHouses/app/memberStoreForgetPwdAction.action";
    /**
     * 重置密码
     */
    public static final String RESET_PWD = "SmartHouses/app/memberStoreUpdateNewLoginPwdAction.action";
    /**
     * 获取楼盘列表
     */
    public static final String GET_HOUSES_LIST = "SmartHouses/app/pagingQueryHousingEstateListAction.action";
}
