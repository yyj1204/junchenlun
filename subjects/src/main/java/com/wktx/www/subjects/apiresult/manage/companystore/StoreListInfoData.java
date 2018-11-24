package com.wktx.www.subjects.apiresult.manage.companystore;
/**
 * Created by yyj on 2018/2/8.
 * 公司店铺列表内容
 * "id": "14","bgap": "天猫","bgat": "母婴玩具","shop_name": "20180629","shop_url": "wktx.cn",
 * "shop_logo": "http://shop.jcl.55085.cn/public/upload/store_logo/2018/06-29/201806291018170927.jpg"
 */

public class StoreListInfoData {
    private String id;//店铺id
    private String bgap;//平台名称
    private String bgat;//类目名称
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

    public String getBgat() {
        return bgat;
    }

    public void setBgat(String bgat) {
        this.bgat = bgat;
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

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    @Override
    public String toString() {
        return "StoreListInfoData{" +
                "id='" + id + '\'' +
                ", bgap='" + bgap + '\'' +
                ", bgat='" + bgat + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_url='" + shop_url + '\'' +
                '}';
    }
}
