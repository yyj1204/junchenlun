package com.wktx.www.subjects.apiresult.message;

/**
 * Created by yyj on 2018/2/8.
 * 公司邀请详情内容
 *
 * hire_id : 383
 * name : 美工
 * project_start_time : 2018-06-08 00:00:00
 * project_end_time : 2018-06-12 09:24:14
 * hire_price : 1.00
 * demand: 444
 * commission: 0
 * qq : 111
 * wechat : 111
 * phone : 111
 * phone:
 * nickname : 劲氏软件
 * address_from : 北京市-市辖区-东城区
 * add_time : 2018-06-08 08:58:57
 * head_pic : http://shop.jcl.55085.cn/public/upload/head_pic/2018/04-18/201804182015280419.jpg
 *
 */

public class InviteDetailsInfoData {
    private String hire_id;//雇佣id
    private String name;//工作类型
    private String project_start_time;//项目开始时间
    private String project_end_time;//项目结束时间
    private String hire_price;//雇佣金额
    private String demand;//需求详情
    private String commission;//提成
    private String qq;//
    private String wechat;//微信
    private String phone;//手机
    private String nickname;//公司名称
    private String address_from;//公司地址
    private String add_time;//添加时间
    private String head_pic;//公司头像

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject_start_time() {
        return project_start_time;
    }

    public void setProject_start_time(String project_start_time) {
        this.project_start_time = project_start_time;
    }

    public String getProject_end_time() {
        return project_end_time;
    }

    public void setProject_end_time(String project_end_time) {
        this.project_end_time = project_end_time;
    }

    public String getHire_price() {
        return hire_price;
    }

    public void setHire_price(String hire_price) {
        this.hire_price = hire_price;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    @Override
    public String toString() {
        return "FireDetailsInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", name='" + name + '\'' +
                ", project_start_time='" + project_start_time + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", hire_price='" + hire_price + '\'' +
                ", demand='" + demand + '\'' +
                ", commission='" + commission + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address_from='" + address_from + '\'' +
                ", add_time='" + add_time + '\'' +
                ", head_pic='" + head_pic + '\'' +
                '}';
    }
}
