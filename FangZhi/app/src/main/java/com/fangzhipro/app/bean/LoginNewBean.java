package com.fangzhipro.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/10/26.
 */
public class LoginNewBean extends BaseResponseBean {
    String token;
    String userID;
    String img;
    List<Parent> parentList;
    List<BannerMain> carouselList;

    public List<BannerMain> getCarouselList() {
        return carouselList;
    }

    public void setCarouselList(List<BannerMain> carouselList) {
        this.carouselList = carouselList;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Parent> getParentList() {
        return parentList;
    }

    public void setParentList(List<Parent> parentList) {
        this.parentList = parentList;
    }

    public class Parent{
        String NAME;
        String ID;
        String URL;
        String USER_IMG;
        boolean isSelected;


        public String getUSER_IMG() {
            return USER_IMG;
        }

        public void setUSER_IMG(String USER_IMG) {
            this.USER_IMG = USER_IMG;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }
    }
}
