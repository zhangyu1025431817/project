package com.fangzhi.app.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
public class RoomProductType {
    String id;
    String name;
    //层次
    String index;
    //产品列表
    List<Product> productList= new ArrayList<>();
    //对应房间
    Room room;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
