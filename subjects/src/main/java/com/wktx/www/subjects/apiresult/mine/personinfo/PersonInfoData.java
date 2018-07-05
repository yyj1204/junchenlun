package com.wktx.www.subjects.apiresult.mine.personinfo;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---个人信息内容
 * "name": "啊饭","picture": "","sex": "1","highest_education": "1","date_of_birth": "1994.01",
 * "residential_city": "莆田","phone": "18150961675","qq": "123456789","wechat": "123456789","character_introduction": "好"
 */

public class PersonInfoData {
    private String name;//用户名
    private String picture;//用户头像url
    private String sex;//性别 0:未设置 1:男 2:女
    private String highest_education;//最高学历
    private String date_of_birth;//出生年月
    private String residential_city;//地址
    private String phone;//电话
    private String qq;//qq
    private String wechat;//微信
    private String character_introduction;//性格

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHighest_education() {
        return highest_education;
    }

    public void setHighest_education(String highest_education) {
        this.highest_education = highest_education;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getResidential_city() {
        return residential_city;
    }

    public void setResidential_city(String residential_city) {
        this.residential_city = residential_city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getCharacter_introduction() {
        return character_introduction;
    }

    public void setCharacter_introduction(String character_introduction) {
        this.character_introduction = character_introduction;
    }

    @Override
    public String toString() {
        return "PersonInfoData{" +
                "name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", sex='" + sex + '\'' +
                ", highest_education='" + highest_education + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", residential_city='" + residential_city + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", character_introduction='" + character_introduction + '\'' +
                '}';
    }
}
