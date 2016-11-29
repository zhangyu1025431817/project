package com.fangzhi.app.bean;

import java.util.ArrayList;

/**
 * Created by smacr on 2016/11/29.
 */
public class CellGraphResponseBean extends BaseResponseBean {
    ArrayList<CellGraph> cellList;

    public ArrayList<CellGraph> getCellList() {
        return cellList;
    }

    public void setCellList(ArrayList<CellGraph> cellList) {
        this.cellList = cellList;
    }

    public static class CellGraph{
        String IMG_URL;
        String CELL_CODE;
        String CELL_NAME;
        boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getIMG_URL() {
            return IMG_URL;
        }

        public void setIMG_URL(String IMG_URL) {
            this.IMG_URL = IMG_URL;
        }

        public String getCELL_CODE() {
            return CELL_CODE;
        }

        public void setCELL_CODE(String CELL_CODE) {
            this.CELL_CODE = CELL_CODE;
        }

        public String getCELL_NAME() {
            return CELL_NAME;
        }

        public void setCELL_NAME(String CELL_NAME) {
            this.CELL_NAME = CELL_NAME;
        }
    }
}
