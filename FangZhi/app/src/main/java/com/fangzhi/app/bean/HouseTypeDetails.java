package com.fangzhi.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by smacr on 2016/9/9.
 */
public class HouseTypeDetails {
    String error_code;
    String msg;

    private List<HouseTypeDetail> hotList;

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

    public List<HouseTypeDetail> getHotList() {
        return hotList;
    }

    public void setHotList(List<HouseTypeDetail> hotList) {
        this.hotList = hotList;
    }

    public class HouseTypeDetail implements Serializable{
       String id;//热点ID
       String hot_top;//热点离图片顶部距离占图片的长度的百分比
       String hot_left;//热点离图片左边距离占图片的宽度的百分比
       String hot_long;//热点长度占图片长度的百分比
       String hot_wide;//热点宽度占图片宽度的百分比
       String hot_type;//热点类型

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHot_top() {
            return hot_top;
        }

        public void setHot_top(String hot_top) {
            this.hot_top = hot_top;
        }

        public String getHot_left() {
            return hot_left;
        }

        public void setHot_left(String hot_left) {
            this.hot_left = hot_left;
        }

        public String getHot_long() {
            return hot_long;
        }

        public void setHot_long(String hot_long) {
            this.hot_long = hot_long;
        }

        public String getHot_wide() {
            return hot_wide;
        }

        public void setHot_wide(String hot_wide) {
            this.hot_wide = hot_wide;
        }

        public String getHot_type() {
            return hot_type;
        }

        public void setHot_type(String hot_type) {
            this.hot_type = hot_type;
        }
    }
}
