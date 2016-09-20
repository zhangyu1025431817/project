package com.fangzhi.app.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
public class HouseTypeDetail {
    String id;
    String name;
    //面积
    float area;
    //朝向
    String orientation;
    String image;
    //居室模块
    List<HouseTypeModule> houseTypeModuleList = new ArrayList<>();
    HouseType houseType;

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

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<HouseTypeModule> getHouseTypeModuleList() {
        return houseTypeModuleList;
    }

    public void setHouseTypeModuleList(List<HouseTypeModule> houseTypeModuleList) {
        this.houseTypeModuleList = houseTypeModuleList;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }
}
