package com.wktx.www.emperor.apiresult.staff.fire;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---发起解雇内容
 * "rid": "1","name": "啊西歌","picture": "","sex": "1","tow": "2","tow_name": "客服","start_time": "1521388800",
 * "end_time": "1521529679","hire_days": "1","status": "1","should_paid_wages": "0","should_paid_wages": "0"
 */

public class StaffFireInfoData {
    private String rid;//简历id
    private String name;//姓名
    private String picture;//头像
    private String sex;//性别 1:男 2:女
    private String tow;//工作类型 1:美工 2:客服 3:运营
    private String tow_name;//工作类型名称 1:美工 2:客服 3:运营
    private String start_time;//合作开始时间
    private String end_time;//合作结束时间
    private String hire_days;//合作天数
    private String status;//雇佣状态 1:合作中(雇佣成功) 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:完结 7:退款 8:未付款到期 9:续约中
    private String should_paid_wages;//应发工资
    private String max_should_paid_wages;//最多能发出去的工资

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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

    public String getTow_name() {
        return tow_name;
    }

    public void setTow_name(String tow_name) {
        this.tow_name = tow_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getHire_days() {
        return hire_days;
    }

    public void setHire_days(String hire_days) {
        this.hire_days = hire_days;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShould_paid_wages() {
        return should_paid_wages;
    }

    public void setShould_paid_wages(String should_paid_wages) {
        this.should_paid_wages = should_paid_wages;
    }

    public String getMax_should_paid_wages() {
        return max_should_paid_wages;
    }

    public void setMax_should_paid_wages(String max_should_paid_wages) {
        this.max_should_paid_wages = max_should_paid_wages;
    }

    @Override
    public String toString() {
        return "PauseInfoData{" +
                "rid='" + rid + '\'' +
                ",name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", sex='" + sex + '\'' +
                ", tow='" + tow + '\'' +
                ", tow_name='" + tow_name + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", hire_days='" + hire_days + '\'' +
                ", status='" + status + '\'' +
                ", should_paid_wages='" + should_paid_wages + '\'' +
                ", max_should_paid_wages='" + max_should_paid_wages + '\'' +
                '}';
    }
}
