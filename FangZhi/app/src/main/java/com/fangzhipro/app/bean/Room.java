package com.fangzhipro.app.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
//房间
public class Room {
    String id;
    HouseModuleStyle houseModuleStyle;
    //进入当前房间需要展示的家居
    List<RoomProduct> roomProductList = new ArrayList<>();
    //房间拥有的家居模块
    List<RoomProductTypes> roomProductTypeList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HouseModuleStyle getHouseModuleStyle() {
        return houseModuleStyle;
    }

    public void setHouseModuleStyle(HouseModuleStyle houseModuleStyle) {
        this.houseModuleStyle = houseModuleStyle;
    }

    public List<RoomProduct> getRoomProductList() {
        return roomProductList;
    }

    public void setRoomProductList(List<RoomProduct> roomProductList) {
        this.roomProductList = roomProductList;
    }

    public List<RoomProductTypes> getRoomProductTypeList() {
        return roomProductTypeList;
    }

    public void setRoomProductTypeList(List<RoomProductTypes> roomProductTypeList) {
        this.roomProductTypeList = roomProductTypeList;
    }
}
