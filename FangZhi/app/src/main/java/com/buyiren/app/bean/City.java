package com.buyiren.app.bean;

/**
 * Created by smacr on 2016/9/9.
 */
public class City {
    public static final String ID = "id";
    public static final String LEVEL = "level";
    public static final String E_NAME = "area_ename";
    public static final String C_NAME = "area_cname";
    String id;
    String level;
    String area_ename;
    String area_cname;

    public City(String id, String level, String area_ename, String area_cname) {
        this.id = id;
        this.level = level;
        this.area_ename = area_ename;
        this.area_cname = area_cname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getArea_ename() {
        return area_ename;
    }

    public void setArea_ename(String area_ename) {
        this.area_ename = area_ename;
    }

    public String getArea_cname() {
        return area_cname;
    }

    public void setArea_cname(String area_cname) {
        this.area_cname = area_cname;
    }
}
