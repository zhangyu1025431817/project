package com.fangzhi.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by smacr on 2016/8/30.
 */
public class LoginBean extends BaseResponseBean implements Serializable{

    private String token;
    private String storeName;
    private String validityTime;
    private List<Area> areaCodeList;

    public class Area implements Serializable{
        private String areaCode;
        private String areaName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(String validityTime) {
        this.validityTime = validityTime;
    }

    public List<Area> getAreaCodeList() {
        return areaCodeList;
    }

    public void setAreaCodeList(List<Area> areaCodeList) {
        this.areaCodeList = areaCodeList;
    }
}
