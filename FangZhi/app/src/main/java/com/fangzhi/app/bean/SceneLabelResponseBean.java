package com.fangzhi.app.bean;

import java.util.ArrayList;

/**
 * Created by smacr on 2016/12/1.
 */
public class SceneLabelResponseBean extends BaseResponseBean {
    ArrayList<SceneLabel>  labelList;
    ArrayList<DDDTypeResponseBean.DDDType> typeList;

    public ArrayList<DDDTypeResponseBean.DDDType> getTypeList() {
        return typeList;
    }

    public void setTypeList(ArrayList<DDDTypeResponseBean.DDDType> typeList) {
        this.typeList = typeList;
    }

    public ArrayList<SceneLabel> getLabelList() {
        return labelList;
    }

    public void setLabelList(ArrayList<SceneLabel> labelList) {
        this.labelList = labelList;
    }
}
