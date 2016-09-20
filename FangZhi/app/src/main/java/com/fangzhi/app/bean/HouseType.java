package com.fangzhi.app.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
public class HouseType {
    String id;
    String name;
    //户型图标
    String imageS;
    //几居室
    String roomCount;
    List<HouseTypeDetail> houseTypeModules = new ArrayList<>();
    Houses houses;

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

    public String getImageS() {
        return imageS;
    }

    public void setImageS(String imageS) {
        this.imageS = imageS;
    }

    public Houses getHouses() {
        return houses;
    }

    public void setHouses(Houses houses) {
        this.houses = houses;
    }

    public List<HouseTypeDetail> getHouseTypeModules() {
        return houseTypeModules;
    }

    public void setHouseTypeModules(List<HouseTypeDetail> houseTypeModules) {
        this.houseTypeModules = houseTypeModules;
    }

    public String getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }
}
