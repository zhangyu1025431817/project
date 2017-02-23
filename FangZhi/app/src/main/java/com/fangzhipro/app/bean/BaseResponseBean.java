package com.fangzhipro.app.bean;

/**
 * Created by smacr on 2016/9/1.
 */
public class BaseResponseBean {
    String error_code;
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}
