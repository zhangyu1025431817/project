package com.fangzhi.app.bean;

import java.util.ArrayList;

/**
 * Created by smacr on 2016/12/1.
 */
public class CooperativeResponseBean extends BaseResponseBean {
    ArrayList<Cooperative> cooperativeList;
    ArrayList<DDDTypeResponseBean.DDDType> typeList;

    public ArrayList<DDDTypeResponseBean.DDDType> getTypeList() {
        return typeList;
    }

    public void setTypeList(ArrayList<DDDTypeResponseBean.DDDType> typeList) {
        this.typeList = typeList;
    }

    public ArrayList<Cooperative> getCooperativeList() {
        return cooperativeList;
    }

    public void setCooperativeList(ArrayList<Cooperative> cooperativeList) {
        this.cooperativeList = cooperativeList;
    }

    public static class Cooperative{
        String id;
        String cooperative_name;
        String cooperative_code;
        boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCooperative_name() {
            return cooperative_name;
        }

        public void setCooperative_name(String cooperative_name) {
            this.cooperative_name = cooperative_name;
        }

        public String getCooperative_code() {
            return cooperative_code;
        }

        public void setCooperative_code(String cooperative_code) {
            this.cooperative_code = cooperative_code;
        }
    }
}
