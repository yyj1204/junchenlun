package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/2/8.
 * 我的工作列表、雇佣记录内容
 * "hire_id": "392","nickname": "尚仁网络有限公司","address_from": "江苏省-常州市-武进区","status": "1","status_desc": "臣民拒绝邀请",
 * "dismissal_id": "0","head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/04-12/201804121827586819.jpg",
 * "trusteeship_wages": "0.00","commission": "20", "project_start_time": "2018-07-02","project_end_time": "2018-08-01","tow": "客服"
 *
 */

public class WorkListInfoData{
    private String hire_id;//雇佣订单id
    private String nickname;//公司名称
    private String head_pic;//公司Logo
    private String address_from;//公司地址
    private String status;//状态 0:未开始 1:合作中 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:已完结 7:已退款 8:已取消 9:续约中 10：待入职;
    private String status_desc;//状态字符串
    private String dismissal_id;//解雇id，如果有值则跳到解雇详情
    private String trusteeship_wages;//托管金额
    private String commission;//提成
    private String project_start_time;//雇佣开始时间
    private String project_end_time;//雇佣结束时间
    private String tow;//工作类型

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getDismissal_id() {
        return dismissal_id;
    }

    public void setDismissal_id(String dismissal_id) {
        this.dismissal_id = dismissal_id;
    }

    public String getTrusteeship_wages() {
        return trusteeship_wages;
    }

    public void setTrusteeship_wages(String trusteeship_wages) {
        this.trusteeship_wages = trusteeship_wages;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
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

    public String getTow() {
        return tow;
    }

    public void setTow(String tow) {
        this.tow = tow;
    }

    @Override
    public String toString() {
        return "WorkListInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", address_from='" + address_from + '\'' +
                ", status='" + status + '\'' +
                ", status_desc='" + status_desc + '\'' +
                ", dismissal_id='" + dismissal_id + '\'' +
                ", trusteeship_wages='" + trusteeship_wages + '\'' +
                ", commission='" + commission + '\'' +
                ", project_start_time='" + project_start_time + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", tow='" + tow + '\'' +
                '}';
    }
}
