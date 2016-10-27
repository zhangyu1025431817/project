package com.fangzhi.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
public class RoomProductTypes {
    String error_code;
    String msg;
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

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

    List<RoomProductType> partTypeList;

    public List<RoomProductType> getPartTypeList() {
        return partTypeList;
    }

    public void setPartTypeList(List<RoomProductType> partTypeList) {
        this.partTypeList = partTypeList;
    }
}
