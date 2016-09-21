package com.fangzhi.app.network;

import com.fangzhi.app.bean.Area;
import com.fangzhi.app.bean.BaseResponseBean;
import com.fangzhi.app.bean.HouseTypes;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.bean.HousesResponseBean;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.SetPasswordBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangyu on 2016/6/15.
 */
public interface ApiService {
    @GET(ApiUrl.USER_LOGIN)
    Observable<LoginBean> login(@Query("loginName") String username, @Query("loginPwd") String password);

    @GET(ApiUrl.USER_LOGIN_TOKEN)
    Observable<LoginBean> loginToken(@Query("token") String token);

    @GET(ApiUrl.SET_PASSWORD)
    Observable<SetPasswordBean> setPassword(@Query("token") String token, @Query("loginPwd") String pwd);

    @GET(ApiUrl.GET_MSG_CODE)
    Observable<BaseResponseBean> getMsgCode(@Query("phoneNumber") String phoneNumber);

    @GET(ApiUrl.RESET_PWD)
    Observable<BaseResponseBean> resetPwd(@Query("phoneNumber") String phoneNumber, @Query("validateCode") String code, @Query("loginPwd") String pwd);

    @GET(ApiUrl.GET_HOUSES_LIST)
    Observable<HousesResponseBean> getHousesList(@Query("token") String token, @Query("areaCode") String areaCode, @Query("pageSize") int pageSize
            , @Query("curPage") int curPage);
    @POST(ApiUrl.CITY_LIST)
    Observable<Area> getCities(@Header("token") String token);

    @POST(ApiUrl.CITY_HOUSE_LIST)
    @FormUrlEncoded
    Observable<Houses> getHouses(@Header("token") String token,@Field("areaid") String id,
                                 @Field("pageSize") int count,
                                 @Field("pageNO") int page);
    @POST(ApiUrl.SEARCH_HOUSE_INFO)
    @FormUrlEncoded
    Observable<Houses> searchHouse(@Header("token") String token,
                                   @Field("areaid") String id,
                                   @Field("premiseName") String name,
                                   @Field("pageSize") int count,
                                   @Field("pageNO") int page);
    @POST(ApiUrl.GET_HOUSE_TYPE_LIST)
    @FormUrlEncoded
    Observable<HouseTypes> getHouseTypes(@Header("token") String token,
                                         @Field("preid") String id);
}
