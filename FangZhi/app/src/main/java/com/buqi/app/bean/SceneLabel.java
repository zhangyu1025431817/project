package com.buqi.app.bean;

import java.util.ArrayList;

/**
 * Created by smacr on 2016/12/1.
 */
public class SceneLabel {
    String id;
    String label_name;
    boolean isSelected;
    ArrayList<DDDTypeResponseBean.DDDType.DDD> sonList;

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

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    public ArrayList<DDDTypeResponseBean.DDDType.DDD> getSonList() {
        return sonList;
    }

    public void setSonList(ArrayList<DDDTypeResponseBean.DDDType.DDD> sonList) {
        this.sonList = sonList;
}
}
