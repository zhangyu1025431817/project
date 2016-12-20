package com.buqi.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/10/20.
 */
public class CountyHouses extends Houses {
    private List<County> countyList;

    public List<County> getCountyList() {
        return countyList;
    }

    public void setCountyList(List<County> countyList) {
        this.countyList = countyList;
    }
}
