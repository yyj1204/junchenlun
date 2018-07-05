package com.wktx.www.emperor.apiresult.staff.manage;

import java.io.Serializable;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工内容
 * "hire_id": "151","rid": "1","tow": "1","tow_name": "客服","project_start_time": "1520006400","project_end_time": "1522684799","hire_price": "0.01",
 * "type": "1","name": "胡图图","picture": "","sex": "2","report_count": 0,"attendance_count": 0,"absence_from_duty_count": 0
 */

public class StaffManageInfoData implements Serializable{
    private String hire_id;//雇佣订单id
    private String rid;//简历id
    private String tow;//工作类型 1:美工 2:客服 3:运营
    private String tow_name;//工作类型名称 1:美工 2:客服 3:运营
    private String project_start_time;//项目开始时间
    private String project_end_time;//项目结束时间
    private String hire_price;//雇佣金额
    private String type;//雇佣状态 1:合作中(雇佣成功) 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:完结 7:退款 9:续约中
    private String name;//姓名
    private String picture;//照片
    private String sex;//性别 1:男 2:女
    private int report_count;//报告数
    private int attendance_count;//出勤数
    private int absence_from_duty_count;//缺勤数

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

    public String getTow_name() {
        return tow_name;
    }

    public void setTow_name(String tow_name) {
        this.tow_name = tow_name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getReport_count() {
        return report_count;
    }

    public void setReport_count(int report_count) {
        this.report_count = report_count;
    }

    public int getAttendance_count() {
        return attendance_count;
    }

    public void setAttendance_count(int attendance_count) {
        this.attendance_count = attendance_count;
    }

    public int getAbsence_from_duty_count() {
        return absence_from_duty_count;
    }

    public void setAbsence_from_duty_count(int absence_from_duty_count) {
        this.absence_from_duty_count = absence_from_duty_count;
    }

    @Override
    public String toString() {
        return "StaffManageInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", rid='" + rid + '\'' +
                ", tow='" + tow + '\'' +
                ", tow_name='" + tow_name + '\'' +
                ", project_start_time='" + project_start_time + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", hire_price='" + hire_price + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", sex='" + sex + '\'' +
                ", report_count=" + report_count +
                ", attendance_count=" + attendance_count +
                ", absence_from_duty_count=" + absence_from_duty_count +
                '}';
    }
}
