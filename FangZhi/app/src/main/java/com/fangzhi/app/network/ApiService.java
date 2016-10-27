package com.fangzhi.app.network;

import com.fangzhi.app.bean.Area;
import com.fangzhi.app.bean.BaseResponseBean;
import com.fangzhi.app.bean.CategoryPart;
import com.fangzhi.app.bean.CategoryPartRoomBean;
import com.fangzhi.app.bean.CountyHouses;
import com.fangzhi.app.bean.HouseTypeDetails;
import com.fangzhi.app.bean.HouseTypes;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.bean.LocationArea;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.LoginNewBean;
import com.fangzhi.app.bean.RoomProductTypes;
import com.fangzhi.app.bean.Scenes;
import com.fangzhi.app.bean.SearchPartBean;
import com.fangzhi.app.bean.SellType;

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
                                @Field("machine_code") String deviceId, @Field("machine_type") String type);
    @GET(ApiUrl.USER_LOGIN_NEW)
    Observable<LoginNewBean> loginNew(@Query("number") String username, @Query("password") String password,
                                      @Query("machine_code") String deviceId, @Query("machine_type") String type);
    @GET(ApiUrl.PARENT_LOGIN_NEW)
    Observable<LoginBean> loginParent(@Header("token") String token,@Query("parentId") String parentId,@Query("userID") String userId);

    @GET(ApiUrl.CITY_LIST)
    Observable<Area> getCities(@Header("token") String token);

    @GET(ApiUrl.CITY_HOUSE_LIST)
    Observable<CountyHouses> getHouses(@Header("token") String token, @Query("areaid") String id,
                                       @Query("pageSize") int count,
                                       @Query("pageNO") int page);

    @POST(ApiUrl.SEARCH_HOUSE_INFO)
    @FormUrlEncoded
    Observable<CountyHouses> searchHouse(@Header("token") String token,
                                         @Field("areaid") String id,
                                         @Field("premiseName") String name,
                                         @Field("pageSize") int count,
                                         @Field("pageNO") int page);

    @GET(ApiUrl.GET_COUNTY_HOUSE)
    Observable<Houses> getCountyHouses(@Header("token") String token, @Query("countyID") String id);

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

    @GET(ApiUrl.GET_CATEGORY)
    Observable<SellType> getSellCategory(@Header("token") String token,
                                         @Query("userID") String userId);

    @GET(ApiUrl.GET_MSG_CODE)
    Observable<BaseResponseBean> getMsgCode(@Query("phone") String phone, @Query("key") String key);

    @GET(ApiUrl.CHECK_MSG_CODE)
    Observable<BaseResponseBean> checkMsgCode(@Query("phone") String phone, @Query("code") String code, @Query("type") int type);

    @GET(ApiUrl.QUERY_LOCATION)
    Observable<LocationArea> getLocationArea(@Query("parentId") String id);

    @GET(ApiUrl.REGISTER_EXPERIENCE_ACCOUNT)
    Observable<BaseResponseBean> registerExperienceUser(@Query("phone") String phone,
                                                        @Query("passWord") String pwd,
                                                        @Query("userName") String userName,
                                                        @Query("sex") int sex,
                                                        @Query("companyName") String companyName,
                                                        @Query("address") String address,
                                                        @Query("province") String province,
                                                        @Query("city") String city,
                                                        @Query("county") String county,
                                                        @Query("scope") String scope,
                                                        @Query("key") String key);

    @GET(ApiUrl.MODIFI_PASSWORD)
    Observable<BaseResponseBean> modificationPassword(@Query("phone") String phone, @Query("passWord") String password,
                                                      @Query("key") String key);
    @GET(ApiUrl.GET_CATEGORY_PART_LIST)
    Observable<CategoryPart> getCategoryPartList(@Header("token") String token, @Query("categoryID") String categoryId,
                                                 @Query("userID") String userId);
    @GET(ApiUrl.SEARCH_CATEGORY_PART)
    Observable<SearchPartBean> searchCategoryPart(@Header("token")String token,
                                                 @Query("categoryName") String categoryName,
                                                 @Query("userID") String userId,
                                                 @Query("hot_type") String id, @Query("cate_id") String cateId);
    @GET(ApiUrl.GET_PART_SENCE)
    Observable<CategoryPartRoomBean> getCategoryScene(@Header("token") String token,@Query("userID") String userId,
                                                      @Query("partID") String partId);
}
