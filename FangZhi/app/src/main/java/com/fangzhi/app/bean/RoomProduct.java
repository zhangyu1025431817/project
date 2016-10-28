package com.fangzhi.app.bean;

import java.io.Serializable;

/**
 * Created by smacr on 2016/9/9.
 */
public class RoomProduct implements Serializable{
    String id;
    int type_id;//部件排序号
    String part_img;//部件图片
    String part_img_short;//部件缩略图
    String part_name;// 部件名称
    String part_code;// 部件编码
    String part_unit;//部件规格
    String part_brand;//部件品牌
    String type_name;
    boolean isSelected;//是否选中

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getPart_img() {
        return part_img;
    }

    public void setPart_img(String part_img) {
        this.part_img = part_img;
    }

    public String getPart_img_short() {
        return part_img_short;
    }

    public void setPart_img_short(String part_img_short) {
        this.part_img_short = part_img_short;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public String getPart_code() {
        return part_code;
    }

    public void setPart_code(String part_code) {
        this.part_code = part_code;
    }

    public String getPart_unit() {
        return part_unit;
    }

    public void setPart_unit(String part_unit) {
        this.part_unit = part_unit;
    }

    public String getPart_brand() {
        return part_brand;
    }

    public void setPart_brand(String part_brand) {
        this.part_brand = part_brand;
    }
}
