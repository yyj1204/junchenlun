package com.wktx.www.emperor.apiresult.mine.store;
/**
 * Created by yyj on 2018/2/8.
 * 店铺信息内容
 * "id": "4","tow": "1","bgat": "1","shop_name": "编辑2","shop_url": "1","tow_name": "美工","bgat_name": "服装内衣"
 */

public class StoreInfoData {
    private String id;
    private String tow;
    private String tow_name;
    private String bgat;
    private String bgat_name;
    private String shop_name;
    private String shop_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTow() {
        return tow;
    }

    public void setTow(String tow) {
        this.tow = tow;
    }

    public String getTow_name() {
        return tow_name;
    }

    public void setTow_name(String tow_name) {
        this.tow_name = tow_name;
    }

    public String getBgat() {
        return bgat;
    }

    public void setBgat(String bgat) {
        this.bgat = bgat;
    }

    public String getBgat_name() {
        return bgat_name;
    }

    public void setBgat_name(String bgat_name) {
        this.bgat_name = bgat_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }


    @Override
    public String toString() {
        return "StoreInfoData{" +
                "id='" + id + '\'' +
                ", tow='" + tow + '\'' +
                ", tow_name='" + tow_name + '\'' +
                ", bgat='" + bgat + '\'' +
                ", bgat_name='" + bgat_name + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_url='" + shop_url + '\'' +
                '}';
    }
}
