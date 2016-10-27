package com.fangzhi.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/10/27.
 */
public class SearchPartBean extends BaseResponseBean {
    List<CategoryPart.Part> partList;

    public List<CategoryPart.Part> getPartList() {
        return partList;
    }

    public void setPartList(List<CategoryPart.Part> partList) {
        this.partList = partList;
    }
}
