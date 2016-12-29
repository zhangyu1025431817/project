package com.buyiren.app.bean;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
//主卧次卧...
public class HouseTypeModule {
    String id;
    //面积
    String name;
    //坐标
    Rect rect;
    HouseTypeDetails houseTypeDetail;
    //房间风格样式，欧式，中式..
    List<HouseModuleStyle> houseModuleStyleList = new ArrayList<>();

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

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public HouseTypeDetails getHouseTypeDetail() {
        return houseTypeDetail;
    }

    public void setHouseTypeDetail(HouseTypeDetails houseTypeDetail) {
        this.houseTypeDetail = houseTypeDetail;
    }

    public List<HouseModuleStyle> getHouseModuleStyleList() {
        return houseModuleStyleList;
    }

    public void setHouseModuleStyleList(List<HouseModuleStyle> houseModuleStyleList) {
        this.houseModuleStyleList = houseModuleStyleList;
    }
}
