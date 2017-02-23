package com.fangzhipro.app.bean;

import java.util.ArrayList;

/**
 * Created by smacr on 2016/11/16.
 */
public class FitmentTypeResponseBean extends BaseResponseBean{

    private ArrayList<FitmentType> caseTypeList;

    public ArrayList<FitmentType> getCaseTypeList() {
        return caseTypeList;
    }

    public void setCaseTypeList(ArrayList<FitmentType> caseTypeList) {
        this.caseTypeList = caseTypeList;
    }

    public class FitmentType{
        int case_type;
        String type_name;
        boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getCase_type() {
            return case_type;
        }

        public void setCase_type(int case_type) {
            this.case_type = case_type;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}
