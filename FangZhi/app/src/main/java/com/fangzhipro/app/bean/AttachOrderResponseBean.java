package com.fangzhipro.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/11/19.
 */
public class AttachOrderResponseBean extends BaseResponseBean {
    List<AttachOrder> attachPartList;

    public List<AttachOrder> getAttachPartList() {
        return attachPartList;
    }

    public void setAttachPartList(List<AttachOrder> attachPartList) {
        this.attachPartList = attachPartList;
    }

    public class AttachOrder {
        String id;
        String part_code;// 部件编码
        String part_unit;//部件规格
        String part_name;
        String part_brand;
        String type_name;
        String part_img_short;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public String getPart_brand() {
            return part_brand;
        }

        public void setPart_brand(String part_brand) {
            this.part_brand = part_brand;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getPart_img_short() {
            return part_img_short;
        }

        public void setPart_img_short(String part_img_short) {
            this.part_img_short = part_img_short;
        }
    }

}
