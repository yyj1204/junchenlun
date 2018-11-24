package com.wktx.www.subjects.apiresult.manage.kpi;

/**
 * Created by yyj on 2018/3/6.
 * 我的工作---考核指标内容
 * "user_id": "2672",
 * "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/06-29/201806290952192574.jpeg",
 * "nickname": "尚仁网络旗舰店6.29",
 * "address_from": "福建省-泉州市-丰泽区",
 * "remark": "尚仁网络旗舰店6.29尚仁网络旗舰店6.29尚仁网络旗舰店6.29尚仁网络旗舰店6.29",
 * "phone": "20180629","qq": "123456","wechat": "123456",
 * "project_end_time": "2018-08-01",
 * "work_days": 32,
 * "arrangement_work_count": "1",
 * "attendance_days": "0",
 * "report_evaluate_count": "7"
 */

public class StaffKPIInfoData {
    private String user_id;//雇主id
    private String nickname;//雇主昵称
    private String head_pic;//头像
    private String address_from;//地址
    private String remark;//公司介绍
    private String qq;//qq
    private String phone;//电话
    private String wechat;//微信
    private String project_end_time;//项目结束时间
    private int work_days;//工作天数
    private String arrangement_work_count;//安排工作总数
    private String attendance_days;//签到天数
    private String report_evaluate_count;//评价数

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getAddress_from() {
        return address_from;
    }

    public void setAddress_from(String address_from) {
        this.address_from = address_from;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getProject_end_time() {
        return project_end_time;
    }

    public void setProject_end_time(String project_end_time) {
        this.project_end_time = project_end_time;
    }

    public int getWork_days() {
        return work_days;
    }

    public void setWork_days(int work_days) {
        this.work_days = work_days;
    }

    public String getArrangement_work_count() {
        return arrangement_work_count;
    }

    public void setArrangement_work_count(String arrangement_work_count) {
        this.arrangement_work_count = arrangement_work_count;
    }

    public String getAttendance_days() {
        return attendance_days;
    }

    public void setAttendance_days(String attendance_days) {
        this.attendance_days = attendance_days;
    }

    public String getReport_evaluate_count() {
        return report_evaluate_count;
    }

    public void setReport_evaluate_count(String report_evaluate_count) {
        this.report_evaluate_count = report_evaluate_count;
    }

    @Override
    public String toString() {
        return "StaffKPIInfoData{" +
                "user_id='" + user_id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", address_from='" + address_from + '\'' +
                ", remark='" + remark + '\'' +
                ", qq='" + qq + '\'' +
                ", phone='" + phone + '\'' +
                ", wechat='" + wechat + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", work_days=" + work_days +
                ", arrangement_work_count='" + arrangement_work_count + '\'' +
                ", attendance_days='" + attendance_days + '\'' +
                ", report_evaluate_count='" + report_evaluate_count + '\'' +
                '}';
    }
}
