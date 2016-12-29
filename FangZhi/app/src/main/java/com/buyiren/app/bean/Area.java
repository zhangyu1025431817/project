package com.buyiren.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/9/20.
 */
public class Area {
    String error_code;
    String msg;
    List<City> areaList;

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

    public List<City> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<City> areaList) {
        this.areaList = areaList;
    }
}
