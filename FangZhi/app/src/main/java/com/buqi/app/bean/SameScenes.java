package com.buqi.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/9/22.
 */
public class SameScenes {
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

    private List<Scene> attachSceneList;

    public List<Scene> getAttachSceneList() {
        return attachSceneList;
    }

    public void setAttachSceneList(List<Scene> attachSceneList) {
        this.attachSceneList = attachSceneList;
    }
}
