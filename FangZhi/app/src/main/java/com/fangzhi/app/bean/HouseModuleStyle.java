package com.fangzhi.app.bean;

/**
 * Created by smacr on 2016/9/9.
 */
public class HouseModuleStyle {
    String id;
    String name;
    String image;
    HouseTypeModule houseTypeModule;
    Room room;

    public HouseTypeModule getHouseTypeModule() {
        return houseTypeModule;
    }

    public void setHouseTypeModule(HouseTypeModule houseTypeModule) {
        this.houseTypeModule = houseTypeModule;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
