package com.fangzhi.app.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by smacr on 2016/9/9.
 */
public class RoomProductType implements Serializable {
    String type_id;
    String type_name;
    int order_num;
    int page_no;
    int position;
    ArrayList<RoomProduct> sonList;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public ArrayList<RoomProduct> getSonList() {
        return sonList;
    }

    public void setSonList(ArrayList<RoomProduct> sonList) {
        this.sonList = sonList;
    }
}
