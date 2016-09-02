package com.fangzhi.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/9/1.
 */
public class HousesResponseBean extends BaseResponseBean{
    private int total;
    private List<Houses> housingEstateList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Houses> getHousingEstateList() {
        return housingEstateList;
    }

    public void setHousingEstateList(List<Houses> housingEstateList) {
        this.housingEstateList = housingEstateList;
    }

    public class Houses{
        String gid;
        String housingEstateName;
        String imagePath;
        String houseTypeNum;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getHousingEstateName() {
            return housingEstateName;
        }

        public void setHousingEstateName(String housingEstateName) {
            this.housingEstateName = housingEstateName;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getHouseTypeNum() {
            return houseTypeNum;
        }

        public void setHouseTypeNum(String houseTypeNum) {
            this.houseTypeNum = houseTypeNum;
        }
    }
}
