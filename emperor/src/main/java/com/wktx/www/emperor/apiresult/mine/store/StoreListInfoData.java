package com.wktx.www.emperor.apiresult.mine.store;
/**
 * Created by yyj on 2018/2/8.
 * 店铺列表(工作报告检索条件)内容
 * "id": "3","shop_name": "测试店铺3","shop_logo":""
 */

public class StoreListInfoData {
    private String id;
    private String shop_logo;
    private String shop_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    @Override
    public String toString() {
        return "StoreListInfoData{" +
                "id='" + id + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                ", shop_name='" + shop_name + '\'' +
                '}';
    }
}
