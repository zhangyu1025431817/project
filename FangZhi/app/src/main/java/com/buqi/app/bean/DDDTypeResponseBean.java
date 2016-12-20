package com.buqi.app.bean;

import java.util.ArrayList;

/**
 * Created by smacr on 2016/11/16.
 */
public class DDDTypeResponseBean extends BaseResponseBean {
    private ArrayList<DDDType> caseList;

    public ArrayList<DDDType> getCaseList() {
        return caseList;
    }

    public void setCaseList(ArrayList<DDDType> caseList) {
        this.caseList = caseList;
    }

    public static class DDDType{
        private String image_type;
        private String image_type_name;
        private ArrayList<DDD> sonList;
        boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getImage_type() {
            return image_type;
        }

        public void setImage_type(String image_type) {
            this.image_type = image_type;
        }

        public String getImage_type_name() {
            return image_type_name;
        }

        public void setImage_type_name(String image_type_name) {
            this.image_type_name = image_type_name;
        }

        public ArrayList<DDD> getSonList() {
            return sonList;
        }

        public void setSonList(ArrayList<DDD> sonList) {
            this.sonList = sonList;
        }

        public static class DDD{
            String id;
            String case_name;
            String case_img;
            String case_url;
            String image_type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCase_name() {
                return case_name;
            }

            public void setCase_name(String case_name) {
                this.case_name = case_name;
            }

            public String getCase_img() {
                return case_img;
            }

            public void setCase_img(String case_img) {
                this.case_img = case_img;
            }

            public String getCase_url() {
                return case_url;
            }

            public void setCase_url(String case_url) {
                this.case_url = case_url;
            }

            public String getImage_type() {
                return image_type;
            }

            public void setImage_type(String image_type) {
                this.image_type = image_type;
            }
        }
    }
}
