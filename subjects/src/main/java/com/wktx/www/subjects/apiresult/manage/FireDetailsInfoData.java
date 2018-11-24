package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/2/8.
 * 我的工作---工作管理---公司解雇详情内容
 *  "id": "19",
 *  "hire_id": "443",
 *  "uid": "2672",
 *  "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/06-29/201806291130245725.jpg",
 *  "nickname": "阿饭",
 *  "reason": "腿",
 *  "should_paid_wages": "0.10",
 *  "be_willing_paid_wages": "1.00",
 *  "work_days": 1,
 *  "add_time": "2018-06-25 02:28:22",
 *  "update_time": "0",
 *  "status": "0",
 *  "remark": null
 *
 */

public class FireDetailsInfoData {
    private String id;//解雇id
    private String hire_id;//雇佣id
    private String uid;//雇主id
    private String head_pic;//雇主头像
    private String nickname;//雇主昵称
    private String reason;//解雇理由
    private String should_paid_wages;//应发工资
    private String be_willing_paid_wages;//愿意支付工资
    private String work_days;//已工作天数
    private String add_time;//创建时间
    private String update_time;//更新时间
    private String status;//解雇状态 0:等待臣民确认 1:臣民已确认 2:臣民已拒绝 3:君主放弃解雇 4:君主申请后台审核 5:后台同意解雇 6:后台不同意解雇
    private String remark;//后台处理结果

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getWork_days() {
        return work_days;
    }

    public void setWork_days(String work_days) {
        this.work_days = work_days;
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

    @Override
    public String toString() {
        return "FireDetailsInfoData{" +
                "id='" + id + '\'' +
                ", hire_id='" + hire_id + '\'' +
                ", uid='" + uid + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", nickname='" + nickname + '\'' +
                ", reason='" + reason + '\'' +
                ", should_paid_wages='" + should_paid_wages + '\'' +
                ", be_willing_paid_wages='" + be_willing_paid_wages + '\'' +
                ", work_days='" + work_days + '\'' +
                ", add_time='" + add_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
