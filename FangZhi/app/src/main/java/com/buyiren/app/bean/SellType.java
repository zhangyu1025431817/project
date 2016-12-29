package com.buyiren.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/10/21.
 */
public class SellType extends BaseResponseBean{

    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public class Category{
        String id;
        String cate_img;
        int is_use;
        String cate_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCate_img() {
            return cate_img;
        }

        public void setCate_img(String cate_img) {
            this.cate_img = cate_img;
        }

        public int getIs_use() {
            return is_use;
        }

        public void setIs_use(int is_use) {
            this.is_use = is_use;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }
    }
}
