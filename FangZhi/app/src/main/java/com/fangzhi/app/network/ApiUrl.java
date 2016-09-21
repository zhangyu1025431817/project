package com.fangzhi.app.network;

/**
 * Created by zhangyu on 2016/6/15.
 */
public class ApiUrl {
    public static final String BASE_URL = "http://120.76.212.114:8050/";
    /**
     * 获取城市列表
     */
    public static final String CITY_LIST = "fznet/fzapp/FangzhiAction/getArea.json";
    /**
     * 获取城市楼盘列表
     */
    public static final String CITY_HOUSE_LIST = "fznet/fzapp/FangzhiAction/getPremise.json";
    /**
     * 搜索楼盘信息
     */
    public static final String SEARCH_HOUSE_INFO = "fznet/fzapp/FangzhiAction/searchPremise.json";
    /**
     * 根据楼盘ID获取户型图
     */
    public static final String GET_HOUSE_TYPE_LIST = "fznet/fzapp/FangzhiAction/getHouseType.json";
    /**
     * 获取app版本号
     */
    public static final String APP_VERSION = "";
    /**
     * 首次登录接口
     */
    public static final String USER_LOGIN = "";
    /**
     * 自动登录
     */
    public static final String USER_LOGIN_TOKEN = "";
    /**
     * 邀请码登录后修改密码
     */
    public static final String SET_PASSWORD = "";
    /**
     * 获取验证码
     */
    public static final String GET_MSG_CODE = "";
    /**
     * 重置密码
     */
    public static final String RESET_PWD = "";
    /**
     * 获取楼盘列表
     */
    public static final String GET_HOUSES_LIST = "";
}
