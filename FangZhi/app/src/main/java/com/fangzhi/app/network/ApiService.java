package com.fangzhi.app.network;

import com.fangzhi.app.bean.Area;
import com.fangzhi.app.bean.HouseTypeDetails;
import com.fangzhi.app.bean.HouseTypes;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.RoomProductTypes;
import com.fangzhi.app.bean.Scenes;

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
    @POST(ApiUrl.USER_LOGIN)
    @FormUrlEncoded
    Observable<LoginBean> login(@Field("number") String username, @Field("password") String password,
                                @Field("machine_code") String deviceId,@Field("machine_type") String type);

    @POST(ApiUrl.USER_LOGIN_TOKEN)
    Observable<LoginBean> loginToken(@Header("token") String token);

    @GET(ApiUrl.CITY_LIST)
    Observable<Area> getCities(@Header("token") String token);

    @GET(ApiUrl.CITY_HOUSE_LIST)
    Observable<Houses> getHouses(@Header("token") String token,@Query("areaid") String id,
                                 @Query("pageSize") int count,
                                 @Query("pageNO") int page);
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
    @POST(ApiUrl.GET_HOUSE_TYPE_DETAILS)
    @FormUrlEncoded
    Observable<HouseTypeDetails> getHouseTypeDetails(@Header("token") String token,
                                                     @Field("houseTypeID") String id);
    @POST(ApiUrl.GET_SCENES)
    @FormUrlEncoded
    Observable<Scenes> getScenes(@Header("token") String token,
                                 @Field("hot_type") String hotType,
                                 @Field("userID") String userId);
    @GET(ApiUrl.GET_SCNEN_PART_TYPE)
    Observable<RoomProductTypes> getRoomProductTypes(@Header("token") String token,
                                                     @Query("hot_type") String hotType,
                                                     @Query("userID") String userId,
                                                     @Query("scene_id") String sceneId,
                                                     @Query("hlCode") String hlCode);
}
