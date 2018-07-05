package com.wktx.www.subjects.apiresult.main.position;

/**
 * Created by yyj on 2018/2/12.
 * 轮播图内容
 */

public class BannerBean {
    private String ad_id;//广告id
    private String ad_name;//广告名称
    private String ad_link;//链接地址
    private String ad_code;//图片地址

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public String getAd_link() {
        return ad_link;
    }

    public void setAd_link(String ad_link) {
        this.ad_link = ad_link;
    }

    public String getAd_code() {
        return ad_code;
    }

    public void setAd_code(String ad_code) {
        this.ad_code = ad_code;
    }


    @Override
    public String toString() {
        return "BannerBean{" +
                "ad_id='" + ad_id + '\'' +
                ", ad_name='" + ad_name + '\'' +
                ", ad_link='" + ad_link + '\'' +
                ", ad_code='" + ad_code + '\'' +
                '}';
    }
}
