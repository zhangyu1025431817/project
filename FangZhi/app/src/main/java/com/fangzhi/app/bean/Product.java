package com.fangzhi.app.bean;

/**
 * Created by smacr on 2016/9/9.
 */
public class Product {
    String id;
    //展示图标
    String imageS;
    //渲染图片
    String imageL;
    //产品型号
    String productCode;
    //价格
    String price;
    //单位
    String unit;
    //品牌名称
    String brandName;
    //图层
    String index;
    RoomProductType roomProductType;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public RoomProductType getRoomProductType() {
        return roomProductType;
    }

    public void setRoomProductType(RoomProductType roomProductType) {
        this.roomProductType = roomProductType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageS() {
        return imageS;
    }

    public void setImageS(String imageS) {
        this.imageS = imageS;
    }

    public String getImageL() {
        return imageL;
    }

    public void setImageL(String imageL) {
        this.imageL = imageL;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
