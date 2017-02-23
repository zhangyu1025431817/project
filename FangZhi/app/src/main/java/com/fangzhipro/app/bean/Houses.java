package com.fangzhipro.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
public class Houses {
    String error_code;
    String msg;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private List<House> premiseList;


    public List<House> getPremiseList() {
        return premiseList;
    }

    public void setPremiseList(List<House> premiseList) {
        this.premiseList = premiseList;
    }

    public class House{
        String id;//楼盘ID
        String pre_cname;// 楼盘中文名称
        String pre_ename;// 楼盘引文名称
        String count;//楼盘所有户型数量
        String pre_img;//

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPre_cname() {
            return pre_cname;
        }

        public void setPre_cname(String pre_cname) {
            this.pre_cname = pre_cname;
        }

        public String getPre_ename() {
            return pre_ename;
        }

        public void setPre_ename(String pre_ename) {
            this.pre_ename = pre_ename;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPre_img() {
            return pre_img;
        }

        public void setPre_img(String pre_img) {
            this.pre_img = pre_img;
        }
    }
}
