package com.fangzhipro.app.bean;

import java.util.ArrayList;

/**
 * Created by smacr on 2016/10/27.
 */
public class CategoryPartRoomBean extends BaseResponseBean {
    int position;
    ArrayList<RoomProductType> partTypeList;
    ArrayList<Scene> sceneList;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<RoomProductType> getPartTypeList() {
        return partTypeList;
    }

    public void setPartTypeList(ArrayList<RoomProductType> partTypeList) {
        this.partTypeList = partTypeList;
    }

    public ArrayList<Scene> getSceneList() {
        return sceneList;
    }

    public void setSceneList(ArrayList<Scene> sceneList) {
        this.sceneList = sceneList;
    }
}
