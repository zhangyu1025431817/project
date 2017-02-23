package com.fangzhipro.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/10/25.
 */
public class LocationArea extends BaseResponseBean{
    List<Location> areaList;

    public List<Location> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Location> areaList) {
        this.areaList = areaList;
    }

    public class Location{
        String ID;
        String AREA_CNAME;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getAREA_CNAME() {
            return AREA_CNAME;
        }

        public void setAREA_CNAME(String AREA_CNAME) {
            this.AREA_CNAME = AREA_CNAME;
        }
    }
}
