package com.fangzhi.app.network;

/**
 * Created by zhangyu on 2016/6/15.
 */
public class ApiUrl {
    /**
     * 正式
     */
    public static final String BASE_URL = "http://120.76.212.114:8050/";
    /**
     * 测试
     */
    //public static final String BASE_URL = "http://120.76.209.107:8050/";
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
     * 根据户型图ID获取该户型的热点信息
     */
    public static final String GET_HOUSE_TYPE_DETAILS = "fznet/fzapp/FangzhiAction/getHouseHot.json";
    /**
     * 获取场景列表
     */
    public static final String GET_SCENES = "fznet/fzapp/FangzhiAction/getScenc.json";
    /**
     * 获取场景组成部件类型及部件
     */
    public static final String GET_SCNEN_PART_TYPE="fznet/fzapp/FangzhiAction/getPart.json";
    /**
     * 获取app版本号
     */
    public static final String APP_VERSION = "";
    /**
     * 首次登录接口
     */
    public static final String USER_LOGIN = "fznet/fzapp/FangzhiAction/login.json";
    /**
     * 获取区县楼盘
     */
    public static final String GET_COUNTY_HOUSE = "fznet/fzapp/FangzhiAction/getCountyPrimise.json";
    /**
     * 获取指定用户售卖种类
     */
    public static final String GET_CATEGORY = "fznet/fzapp/FangzhiAction/getCategory.json";
    /**
     * 获取指定种类的部件列表
     */
    public static final String GET_CATEGORY_PART_LIST = "fznet/fzapp/FangzhiAction/getPartList.json";
    /**
     * 部件搜索
     */
    public static final String SEARCH_CATEGORY_PART = "fznet/fzapp/FangzhiAction/searchPart.json";
    /**
     * 获取指定部件对应的场景
     */
    public static final String GET_PART_SENCE = "fznet/fzapp/FangzhiAction/getScene.json";
 }
