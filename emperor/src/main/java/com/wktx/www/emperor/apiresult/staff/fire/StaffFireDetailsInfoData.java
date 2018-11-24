package com.wktx.www.emperor.apiresult.staff.fire;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---解雇详情内容
 * "id": "33","hire_id": "560","rid": "13","reason": "不会吧",
 * "should_paid_wages": "0.00","be_willing_paid_wages": "10.00","add_time": "1533868694",
 * "update_time": "1533885927","status": "3","remark": null,"work_days": 1,"name": "啊饭",
 * "picture": "http://shop.junchenlun.com/public/upload/head_pic/2018/07-03/201807031134475127.jpg",
 * "sex": "1","tow": "主播"
 */

public class StaffFireDetailsInfoData {
    private String id;//解雇id
    private String hire_id;//雇佣id
    private String rid;//简历id
    private String reason;//解雇理由
    private String should_paid_wages;//应发工资
    private String be_willing_paid_wages;//愿意支付工资
    private String add_time;//解雇时间
    private String update_time;//更新时间
    private String status;//解雇状态 0:等待臣民确认 1:臣民已确认 2:臣民已拒绝 3:君主放弃解雇 4:君主申请后台审核 5:后台同意解雇 6:后台不同意解雇
    private String remark;//后台处理结果
    private String work_days;//已工作天数
    private String name;//姓名
    private String picture;//头像
    private String sex;//性别 1:男 2:女
    private String tow;//工作类型 1:美工 2:客服 3:运营

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getShould_paid_wages() {
        return should_paid_wages;
    }

    public void setShould_paid_wages(String should_paid_wages) {
        this.should_paid_wages = should_paid_wages;
    }

    public String getBe_willing_paid_wages() {
        return be_willing_paid_wages;
    }

    public void setBe_willing_paid_wages(String be_willing_paid_wages) {
        this.be_willing_paid_wages = be_willing_paid_wages;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWork_days() {
        return work_days;
    }

    public void setWork_days(String work_days) {
        this.work_days = work_days;
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

    public String getTow() {
        return tow;
    }

    public void setTow(String tow) {
        this.tow = tow;
    }

    @Override
    public String toString() {
        return "StaffFireDetailsInfoData{" +
                "id='" + id + '\'' +
                ", hire_id='" + hire_id + '\'' +
                ", rid='" + rid + '\'' +
                ", reason='" + reason + '\'' +
                ", should_paid_wages='" + should_paid_wages + '\'' +
                ", be_willing_paid_wages='" + be_willing_paid_wages + '\'' +
                ", add_time='" + add_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", work_days='" + work_days + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", sex='" + sex + '\'' +
                ", tow='" + tow + '\'' +
                '}';
    }
}
