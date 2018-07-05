package com.wktx.www.emperor.apiresult.mine.companyinfo;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心---公司信息内容
 * "user_id": "2649","username": "ph_da92e307ae9ce43843c563119cdd00e7",
 * "nickname": "啊饭","head_pic": "","sex": "0","address_from": "","phone": ""，"qq": "0","weixin": "","remark": ""
 */

public class CompanyInfoData {
    private String user_id;//用户ID
    private String username;//用户名
    private String nickname;//用户昵称
    private String head_pic;//用户头像url
    private String sex;//0:保密 1:男 2:女
    private String address_from;//地址
    private String phone;//电话
    private String qq;//qq
    private String weixin;//微信
    private String remark;//公司简介

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress_from() {
        return address_from;
    }

    public void setAddress_from(String address_from) {
        this.address_from = address_from;
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

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public String toString() {
        return "CompanyInfoData{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", sex='" + sex + '\'' +
                ", address_from='" + address_from + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", weixin='" + weixin + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
