package com.fangzhi.app.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
public class City {
    String id;
    String name;
    List<Houses> housesList = new ArrayList<>();

    public City(String id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Houses> getHousesList() {
        return housesList;
    }

    public void setHousesList(List<Houses> housesList) {
        this.housesList = housesList;
    }
}
