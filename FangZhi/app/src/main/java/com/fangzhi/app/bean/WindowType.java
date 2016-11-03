package com.fangzhi.app.bean;

import java.io.Serializable;

/**
 * Created by smacr on 2016/11/2.
 */
public class WindowType implements Serializable{
    String decorate_id;// 窗型id
    String decorate_code;//窗型code
    String decorate_img;// 窗型图片
    String decorate_name;//窗型名称

    public String getDecorate_id() {
        return decorate_id;
    }

    public void setDecorate_id(String decorate_id) {
        this.decorate_id = decorate_id;
    }

    public String getDecorate_code() {
        return decorate_code;
    }

    public void setDecorate_code(String decorate_code) {
        this.decorate_code = decorate_code;
    }

    public String getDecorate_img() {
        return decorate_img;
    }

    public void setDecorate_img(String decorate_img) {
        this.decorate_img = decorate_img;
    }

    public String getDecorate_name() {
        return decorate_name;
    }

    public void setDecorate_name(String decorate_name) {
        this.decorate_name = decorate_name;
    }
}
