package com.wktx.www.emperor.apiresult.recruit.hire;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yyj on 2018/2/8.
 * 简历---雇佣信息内容
 * "hire_id": "75","hire_sn": "15197978878508","is_pay": "0","hire_price": "3500.00","total_amount": "3500.00",
 * "user_money": "0.00","coupon_price": "0.00","project_start_time": "1519747200","project_end_time": "1522166399","hire_time": "1",
 * "hire_type": "1","name": "胡图图","picture": "","sex": "1","working_years": "2","user_money_pay_all": 0,"bgat": "服装内衣/母婴玩具/精品鞋包"
 */

public class TrusteeshipSalaryInfoData implements Serializable{
    private String hire_id;//雇佣订单id
    private String hire_sn;//雇佣订单号
    private String is_pay;//0:未支付 1:已支付
    private String hire_price;//雇佣金额
    private String total_amount;//支付总金额
    private String user_money;//使用的余额金额
    private String coupon_price;//使用优惠券抵扣的金额
    private String project_start_time;//项目开始时间
    private String project_end_time;//项目结束时间
    private String hire_time;//雇佣时间（月）
    private String hire_type;//雇佣方式 1:包月,2:定制 3:雇佣单人
    private String name;//姓名
    private String picture;//照片
    private String sex;//性别 1:男，2:女
    private String working_years;//工龄
    private String bgat;//类目
    private int user_money_pay_all;//0:不是全款余额支付 1:全款余额支付

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

    public String getHire_sn() {
        return hire_sn;
    }

    public void setHire_sn(String hire_sn) {
        this.hire_sn = hire_sn;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getHire_price() {
        return hire_price;
    }

    public void setHire_price(String hire_price) {
        this.hire_price = hire_price;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(String coupon_price) {
        this.coupon_price = coupon_price;
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

    public String getHire_time() {
        return hire_time;
    }

    public void setHire_time(String hire_time) {
        this.hire_time = hire_time;
    }

    public String getHire_type() {
        return hire_type;
    }

    public void setHire_type(String hire_type) {
        this.hire_type = hire_type;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWorking_years() {
        return working_years;
    }

    public void setWorking_years(String working_years) {
        this.working_years = working_years;
    }

    public String getBgat() {
        return bgat;
    }

    public void setBgat(String bgat) {
        this.bgat = bgat;
    }

    public int getUser_money_pay_all() {
        return user_money_pay_all;
    }

    public void setUser_money_pay_all(int user_money_pay_all) {
        this.user_money_pay_all = user_money_pay_all;
    }


    @Override
    public String toString() {
        return "TrusteeshipSalaryInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", hire_sn='" + hire_sn + '\'' +
                ", is_pay='" + is_pay + '\'' +
                ", hire_price='" + hire_price + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", user_money='" + user_money + '\'' +
                ", coupon_price='" + coupon_price + '\'' +
                ", project_start_time='" + project_start_time + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", hire_time='" + hire_time + '\'' +
                ", hire_type='" + hire_type + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", sex='" + sex + '\'' +
                ", working_years='" + working_years + '\'' +
                ", bgat='" + bgat + '\'' +
                ", user_money_pay_all=" + user_money_pay_all +
                '}';
    }
}
