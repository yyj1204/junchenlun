package com.wktx.www.subjects.apiresult.main;

/**
 * Created by yyj on 2018/5/8.
 * 职位详情内容
 * "id": "6","user_id": "2649","title": "6","content": "6","bgap_name": "淘宝","bgat_name": "服装内衣",
 * "budget": "3.00","shop_name": "安时代的","nickname": "尚仁网络有限公司","address_from": "江苏省-常州市-武进区",
 * "qq": "12347","weixin": "22","phone": "12345678907","add_time": "2018-03-08 15:53:02",
 * "end_time": "2020-09-13 20:26:39","is_dated": "0",
 * "shop_logo": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png",
 * "is_collect": 0
 */

public class PositionDetailsInfoData {
    private String id;//招聘id
    private String user_id;//雇主id
    private String title;//招聘标题
    private String content;//招聘内容
    private String bgap_name;//平台名称
    private String bgat_name;//类目名称
    private String budget;//薪资
    private String shop_name;//店铺名称
    private String nickname;//公司名称
    private String address_from;//公司地址
    private String qq;//qq号
    private String weixin;//微信
    private String phone;//手机号
    private String add_time;//开始时间
    private String end_time;//结束时间
    private String is_dated;//是否过期，0：未过期 1：已过期
    private String shop_logo;//店铺图片
    private String is_collect;//是否收藏

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBgap_name() {
        return bgap_name;
    }

    public void setBgap_name(String bgap_name) {
        this.bgap_name = bgap_name;
    }

    public String getBgat_name() {
        return bgat_name;
    }

    public void setBgat_name(String bgat_name) {
        this.bgat_name = bgat_name;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress_from() {
        return address_from;
    }

    public void setAddress_from(String address_from) {
        this.address_from = address_from;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIs_dated() {
        return is_dated;
    }

    public void setIs_dated(String is_dated) {
        this.is_dated = is_dated;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    @Override
    public String toString() {
        return "PositionDetailsInfoData{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", bgap_name='" + bgap_name + '\'' +
                ", bgat_name='" + bgat_name + '\'' +
                ", budget='" + budget + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address_from='" + address_from + '\'' +
                ", qq='" + qq + '\'' +
                ", weixin='" + weixin + '\'' +
                ", phone='" + phone + '\'' +
                ", add_time='" + add_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", is_dated='" + is_dated + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                ", is_collect=" + is_collect +
                '}';
    }
}
