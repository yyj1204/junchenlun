package com.wktx.www.emperor.apiresult.mine.store;
/**
 * Created by yyj on 2018/2/8.
 * 店铺信息内容
 * "id": "4","bgap": "1","bgat": "1","shop_logo":"","shop_name": "编辑2","shop_url": "1",
 * "tow_name": "美工","bgat_name": "服装内衣"
 */

public class StoreInfoData {
    private String id;//店铺id
    private String bgap;//平台id
    private String bgap_name;//平台名称
    private String bgat;//类目id
    private String bgat_name;//擅长类目名称
    private String shop_logo;//店铺头像
    private String shop_name;//店铺名称
    private String shop_url;//店铺网址

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBgap() {
        return bgap;
    }

    public void setBgap(String bgap) {
        this.bgap = bgap;
    }

    public String getBgap_name() {
        return bgap_name;
    }

    public void setBgap_name(String bgap_name) {
        this.bgap_name = bgap_name;
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

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
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
                ", bgap='" + bgap + '\'' +
                ", bgap_name='" + bgap_name + '\'' +
                ", bgat='" + bgat + '\'' +
                ", bgat_name='" + bgat_name + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_url='" + shop_url + '\'' +
                '}';
    }
}
