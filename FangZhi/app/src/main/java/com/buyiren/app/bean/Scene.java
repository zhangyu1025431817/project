package com.buyiren.app.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by smacr on 2016/9/22.
 */
public class Scene{
    private String hl_code;
    private String  hl_id;
    private String  hl_img;
    private String is_default;
    private String scene_code;
    private String scene_id;
    private String scene_img;
    private String scene_name;
    private String show_img;
    private String hot_type;
    private ArrayList<Part> sonList;

    public String getHot_type() {
        return hot_type;
    }

    public void setHot_type(String hot_type) {
        this.hot_type = hot_type;
    }

    public String getHl_code() {
        return hl_code;
    }

    public void setHl_code(String hl_code) {
        this.hl_code = hl_code;
    }

    public String getHl_id() {
        return hl_id;
    }

    public void setHl_id(String hl_id) {
        this.hl_id = hl_id;
    }

    public String getHl_img() {
        return hl_img;
    }

    public void setHl_img(String hl_img) {
        this.hl_img = hl_img;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getScene_code() {
        return scene_code;
    }

    public void setScene_code(String scene_code) {
        this.scene_code = scene_code;
    }

    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }

    public String getScene_img() {
        return scene_img;
    }

    public void setScene_img(String scene_img) {
        this.scene_img = scene_img;
    }

    public String getScene_name() {
        return scene_name;
    }

    public void setScene_name(String scene_name) {
        this.scene_name = scene_name;
    }

    public String getShow_img() {
        return show_img;
    }

    public void setShow_img(String show_img) {
        this.show_img = show_img;
    }

    public ArrayList<Part> getSonList() {
        return sonList;
    }

    public void setSonList(ArrayList<Part> sonList) {
        this.sonList = sonList;
    }

    public class Part implements Serializable{
        String id;
        int order_num;
        String part_brand;
        String part_img;
        String type_name;
        String part_img_short;
        String part_name;
        String part_unit;
        int type_id;
        String scene_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public int getOrder_num() {
            return order_num;
        }

        public void setOrder_num(int order_num) {
            this.order_num = order_num;
        }

        public String getPart_brand() {
            return part_brand;
        }

        public void setPart_brand(String part_brand) {
            this.part_brand = part_brand;
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

        public String getPart_unit() {
            return part_unit;
        }

        public void setPart_unit(String part_unit) {
            this.part_unit = part_unit;
        }

        public String getScene_id() {
            return scene_id;
        }

        public void setScene_id(String scene_id) {
            this.scene_id = scene_id;
        }
    }
}
