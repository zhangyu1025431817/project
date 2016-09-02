package com.fangzhi.app.bean;

/**
 * Created by smacr on 2016/9/1.
 */
public class BaseResponseBean {
    int code;
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
