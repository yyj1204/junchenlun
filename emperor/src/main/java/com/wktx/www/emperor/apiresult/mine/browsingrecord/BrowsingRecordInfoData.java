package com.wktx.www.emperor.apiresult.mine.browsingrecord;
/**
 * Created by yyj on 2018/2/8.
 * 浏览记录内容 \ 我的收藏内容 \ 面试记录内容
 * "rid": "2","tow": "2","tow_name": "客服","name": "啊西歌","picture": "","working_years": "5","typing_speed": "0",
 * "sex": "1","residential_city": "江西-南昌","monthly_money": "0.01","bgat": "母婴玩具/精品鞋包/生活百货"
 *
 */

public class BrowsingRecordInfoData {
    private String rid;//简历id
    private String tow;//工作类型 1:美工 2:客服 3:运营
    private String tow_name;//工作类型名称 1:美工 2:客服 3:运营
    private String name;//名字
    private String picture;//头像
    private String working_years;//工龄
    private String typing_speed;//打字速度
    private String sex;//性别 0:未设置 1:男 2:女
    private String residential_city;//所在城市
    private String monthly_money;//包月金额
    private String bgat;//擅长类目

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getWorking_years() {
        return working_years;
    }

    public void setWorking_years(String working_years) {
        this.working_years = working_years;
    }

    public String getTyping_speed() {
        return typing_speed;
    }

    public void setTyping_speed(String typing_speed) {
        this.typing_speed = typing_speed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getResidential_city() {
        return residential_city;
    }

    public void setResidential_city(String residential_city) {
        this.residential_city = residential_city;
    }

    public String getMonthly_money() {
        return monthly_money;
    }

    public void setMonthly_money(String monthly_money) {
        this.monthly_money = monthly_money;
    }

    public String getBgat() {
        return bgat;
    }

    public void setBgat(String bgat) {
        this.bgat = bgat;
    }


    @Override
    public String toString() {
        return "BrowsingRecordInfoData{" +
                "rid='" + rid + '\'' +
                ", tow='" + tow + '\'' +
                ", tow_name='" + tow_name + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", working_years='" + working_years + '\'' +
                ", typing_speed='" + typing_speed + '\'' +
                ", sex='" + sex + '\'' +
                ", residential_city='" + residential_city + '\'' +
                ", monthly_money='" + monthly_money + '\'' +
                ", bgat='" + bgat + '\'' +
                '}';
    }
}
