package com.buyiren.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
public class HouseTypes {
    String error_code;
    String msg;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private List<HouseType> houseList;

    public List<HouseType> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<HouseType> houseList) {
        this.houseList = houseList;
    }

    public class HouseType {
        String id;//户型id
        String house_name;//户型名称
        String house_img;//户型图
        String house_forest;//面积
        String house_room;//居室

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHouse_name() {
            return house_name;
        }

        public void setHouse_name(String house_name) {
            this.house_name = house_name;
        }

        public String getHouse_img() {
            return house_img;
        }

        public void setHouse_img(String house_img) {
            this.house_img = house_img;
        }

        public String getHouse_forest() {
            return house_forest;
        }

        public void setHouse_forest(String house_forest) {
            this.house_forest = house_forest;
        }

        public String getHouse_room() {
            return house_room;
        }

        public void setHouse_room(String house_room) {
            this.house_room = house_room;
        }
    }
}
