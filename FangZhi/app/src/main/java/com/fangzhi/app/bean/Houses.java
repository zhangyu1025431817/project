package com.fangzhi.app.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
public class Houses {
    String id;
    String name;
    //房型数量
    String typeCount;
    //本地图片地址
    String image;
    City city;
    List<HouseType> houseTypeList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(String typeCount) {
        this.typeCount = typeCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<HouseType> getHouseTypeList() {
        return houseTypeList;
    }

    public void setHouseTypeList(List<HouseType> houseTypeList) {
        this.houseTypeList = houseTypeList;
    }
}
