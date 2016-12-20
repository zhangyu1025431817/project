package com.buqi.app.bean;

import java.util.List;

/**
 * Created by smacr on 2016/11/30.
 */
public class SceneStyleResponse extends BaseResponseBean {
    List<SceneStyle> styleList;

    public List<SceneStyle> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<SceneStyle> styleList) {
        this.styleList = styleList;
    }

    public class SceneStyle{
        String code_id;
        String code_desc;
        boolean isSelected;
        List<Scene> sceneList;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
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

        public List<Scene> getSceneList() {
            return sceneList;
        }

        public void setSceneList(List<Scene> sceneList) {
            this.sceneList = sceneList;
        }
    }
}
