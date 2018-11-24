package com.wktx.www.subjects.apiresult.main.demand;

import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;

/**
 * Created by yyj on 2018/5/8.
 * 职位招聘需求详情内容
 * "id": "12","user_id": "2649","title": "sr4002","content": "sr","tow": {"id": "1","name": "美工"},
 * "design_pattern": "直通车设计","working_years": "两年","bgap_name": "京东","bgat_name": "3C数码","hire_type": "不限",
 * "budget": "1888.00","shop_name": "永蛙田鸡","nickname": "阿饭","address_from": "江苏省-常州市-武进区",
 * "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/06-29/201806291130245725.jpg",
 * "shop_logo": "http://shop.jcl.55085.cn/public/upload/store_logo/2018/06-27/201806271524474142.jpg",
 * "shop_url": "www.taobao.com", "remark": "了122",
 * "qq": "12347","weixin": "22","phone": "12345678907",
 * "end_time": "2020-09-13","add_time": "2018-05-15 10:30:58",
 * "is_dated": "0","is_collect": 0
 */

public class DemandDetailsInfoData {
    private String id;//招聘id
    private String user_id;//雇主id
    private String title;//招聘标题
    private String content;//招聘内容
    private String design_pattern;//设计模式名称
    private String bgap_name;//平台名称
    private String bgat_name;//类目名称
    private String hire_type;//雇佣方式
    private ConditionBean tow;//招聘职位
    private String working_years;//工作经验
    private String budget;//薪资
    private String shop_name;//店铺名称
    private String shop_url;//店铺网址
    private String shop_logo;//店铺图片
    private String nickname;//公司名称
    private String head_pic;//公司logo
    private String remark;//公司介绍
    private String address_from;//公司地址
    private String qq;//qq号
    private String weixin;//微信
    private String phone;//手机号
    private String add_time;//开始时间
    private String end_time;//结束时间
    private String is_dated;//是否过期，0：未过期 1：已过期
    private String is_collect;//是否收藏
    private String resume_tow;//我的简历里是否有添加应聘职位：0：没有，1：有

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

    public String getDesign_pattern() {
        return design_pattern;
    }

    public void setDesign_pattern(String design_pattern) {
        this.design_pattern = design_pattern;
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

    public String getHire_type() {
        return hire_type;
    }

    public void setHire_type(String hire_type) {
        this.hire_type = hire_type;
    }

    public ConditionBean getTow() {
        return tow;
    }

    public void setTow(ConditionBean tow) {
        this.tow = tow;
    }

    public String getWorking_years() {
        return working_years;
    }

    public void setWorking_years(String working_years) {
        this.working_years = working_years;
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

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public String getResume_tow() {
        return resume_tow;
    }

    public void setResume_tow(String resume_tow) {
        this.resume_tow = resume_tow;
    }

    @Override
    public String toString() {
        return "DemandDetailsInfoData{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", design_pattern='" + design_pattern + '\'' +
                ", bgap_name='" + bgap_name + '\'' +
                ", bgat_name='" + bgat_name + '\'' +
                ", hire_type='" + hire_type + '\'' +
                ", tow=" + tow +
                ", working_years='" + working_years + '\'' +
                ", budget='" + budget + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_url='" + shop_url + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", remark='" + remark + '\'' +
                ", address_from='" + address_from + '\'' +
                ", qq='" + qq + '\'' +
                ", weixin='" + weixin + '\'' +
                ", phone='" + phone + '\'' +
                ", add_time='" + add_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", is_dated='" + is_dated + '\'' +
                ", is_collect='" + is_collect + '\'' +
                ", resume_tow='" + resume_tow + '\'' +
                '}';
    }
}
