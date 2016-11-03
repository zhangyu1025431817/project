package com.fangzhi.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/10/26.
 */
public class CategoryPart extends BaseResponseBean {

    private List<HotType> partList;

    public List<HotType> getPartList() {
        return partList;
    }

    public void setPartList(List<HotType> partList) {
        this.partList = partList;
    }

    public static class HotType {
        String code_id;
        String code_desc;
        boolean isSelected;//是否选中
        ArrayList<? extends Part> sonList;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public List<? extends Part> getSonList() {
            return sonList;
        }

        public void setSonList(ArrayList<? extends Part> sonList) {
            this.sonList = sonList;
        }

        public String getCode_id() {
            return code_id;
        }

        public void setCode_id(String code_id) {
            this.code_id = code_id;
        }

        public String getCode_desc() {
            return code_desc;
        }

        public void setCode_desc(String code_desc) {
            this.code_desc = code_desc;
        }
    }

    public static class Part implements Serializable {
        String id;
        String part_name;
        String part_img;
        String part_brand;
        String type_name;
        String part_img_short;
        int type_id;
        boolean isSelected;//是否选中

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public String getPart_img() {
            return part_img;
        }

        public void setPart_img(String part_img) {
            this.part_img = part_img;
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
