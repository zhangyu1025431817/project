package com.buqi.app.bean;

/**
 * Created by smacr on 2016/9/9.
 */
public class RoomProduct extends CategoryPart.Part {
    String part_code;// 部件编码
    String part_unit;//部件规格


    public String getPart_code() {
        return part_code;
    }

    public void setPart_code(String part_code) {
        this.part_code = part_code;
    }

    public String getPart_unit() {
        return part_unit;
    }

    public void setPart_unit(String part_unit) {
        this.part_unit = part_unit;
    }


}
