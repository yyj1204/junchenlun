package com.wktx.www.emperor.apiresult.staff.staff;

/**
 * Created by yyj on 2018/2/8.
 * 我的员工 \ 雇佣记录
 * "hire_id": "151","rid": "1","tow": "1","project_start_time": "1520006400","project_end_time": "1522684799",
 * "hire_price": "0.01","is_pay": "1","name": "胡图图","picture": "","type": "1","status_str": "合作中"
 */

public class StaffInfoData {
    private String hire_id;//订单id
    private String rid;//简历id
    private String tow;//1:美工 2:客服 3:运营
    private String project_start_time;//项目开始时间
    private String project_end_time;//项目结束时间
    private String hire_price;//雇佣需支付的金额
    private String is_pay;//0:未支付，1：已支付
    private String name;//名字
    private String picture;//照片
    private String sex;//性别 1:男，2:女
    private String type;//雇佣状态 1:合作中(雇佣成功) 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:完结 7:退款 8:未付款到期 9:续约中
    private String status_str;//状态

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

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

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus_str() {
        return status_str;
    }

    public void setStatus_str(String status_str) {
        this.status_str = status_str;
    }

    @Override
    public String toString() {
        return "StaffInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", rid='" + rid + '\'' +
                ", tow='" + tow + '\'' +
                ", project_start_time='" + project_start_time + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", hire_price='" + hire_price + '\'' +
                ", is_pay='" + is_pay + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", sex='" + sex + '\'' +
                ", type='" + type + '\'' +
                ", status_str='" + status_str + '\'' +
                '}';
    }
}

