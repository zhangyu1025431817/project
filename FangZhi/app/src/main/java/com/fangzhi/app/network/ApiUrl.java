package com.fangzhi.app.network;

/**
 * Created by zhangyu on 2016/6/15.
 */
public class ApiUrl {

    /**
     * 正式
     */
  //  public static final String BASE_URL = "http://51fangz.com:8050/";
    /**
     * 测试
     */
    public static final String BASE_URL = "http://test.51fangz.com:8050/";
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
     * 新登录接口
     */
    public static final String USER_LOGIN_NEW = "fznet/fzmangge/FzUser/loginNew.json";
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
    public static final String GET_CATEGORY_PART_LIST = "fznet/fzapp/FangzhiAction/newGetPartList.json";
    /**
     * 部件搜索
     */
    public static final String SEARCH_CATEGORY_PART = "fznet/fzapp/FangzhiAction/searchPart.json";
    /**
     * 获取指定部件对应的场景
     */
    public static final String GET_PART_SENCE = "fznet/fzapp/FangzhiAction/getScene.json";
    /**
     * 发送验证码
     */
    public static final String GET_MSG_CODE = "fznet/fzmangge/FzUser/sendSms.json";
    /**
     * 查询验证码是否有效
     */
    public static final String CHECK_MSG_CODE = "fznet/fzmangge/FzUser/queryVerCode.json";
    /**
     * 修改密码
     */
    public static final String MODIFI_PASSWORD = "fznet/fzmangge/FzUser/modifyPassword.json";
    /**
     * 插入体验号用户
     */
    public static final String REGISTER_EXPERIENCE_ACCOUNT = "fznet/fzmangge/FzUser/newUserExperience.json";
    /**
     * 查询用户的所属厂家
     */
    public static final String QUERY_PARENT_FACTORY = "fznet/fzmangge/FzUser/queryParentFac.json";
    /**
     * 选择父级id登陆
     */
    public static final String PARENT_LOGIN_NEW = "fznet/fzmangge/FzUser/parentLoginNew.json";
    /**
     * 查询省市县
     */
    public static final String QUERY_LOCATION = "fznet/fzmangge/FzUser/loginArea.json";
    /**
     * 版本更新
     */
    public static final String UPDATE_VERSION = "fznet/fzmangge/FzUser/getEdition.json";
    /**
     * 获取3D场景类型（家装，工装等）
     */
    public static final String GET_FITMENT_TYPE = "fznet/fzapp/FangzhiAction/getCaseTypeList.json";
    /**
     * 获取3D场景分类列表（KTV,别墅等的3D场景列表）
     */
    public static final String GET_3_D = "fznet/fzapp/FangzhiAction/getCaseList.json";
    /**
     * 获取附属部件列表
     */
    public static final String GET_ATTACH_PART = "fznet/fzapp/FangzhiAction/getAttachPartList.json";
    /**
     *查询单元图
     */
    public static final String QUERY_CELL_GRAPH = "fznet/fzmangge/FzUser/queryCellGraph.json";
    /**
     * 获取场景列表根据场景风格进行分组
     */
    public static final String GET_STYLE_SCENE = "fznet/fzapp/FangzhiAction/getStyleScenc.json";
 }
