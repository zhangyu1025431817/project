package com.buqi.app.bean;

import java.io.Serializable;

/**
 * Created by smacr on 2016/9/1.
 */
public class BaseResponseBean implements Serializable{
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
