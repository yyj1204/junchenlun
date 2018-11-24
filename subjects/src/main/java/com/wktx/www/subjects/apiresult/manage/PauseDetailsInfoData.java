package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/3/6.
 * 我的工作---工作管理---暂停工作信息内容
 *  "id": "30","uid": "2649","hire_id": "560","suspend_describe": "先暂停吧","suspend_days": "2",
 *  "suspend_start": "2018-08-13","suspend_end": "2018-08-14",
 *  "plan_suspend_end": "0","add_time": "2018-08-13 16:00:28"
 */

public class PauseDetailsInfoData {
    private String id;//暂停工作id
    private String uid;//雇主id
    private String hire_id;//雇佣id
    private String suspend_describe;//暂停工作原因
    private String suspend_days;//暂停天数
    private String suspend_start;//暂停开始时间
    private String suspend_end;//暂停结束时间
    private String plan_suspend_end;//原暂停结束时间
    private String add_time;//	创建时间

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

    public String getSuspend_describe() {
        return suspend_describe;
    }

    public void setSuspend_describe(String suspend_describe) {
        this.suspend_describe = suspend_describe;
    }

    public String getSuspend_days() {
        return suspend_days;
    }

    public void setSuspend_days(String suspend_days) {
        this.suspend_days = suspend_days;
    }

    public String getSuspend_start() {
        return suspend_start;
    }

    public void setSuspend_start(String suspend_start) {
        this.suspend_start = suspend_start;
    }

    public String getSuspend_end() {
        return suspend_end;
    }

    public void setSuspend_end(String suspend_end) {
        this.suspend_end = suspend_end;
    }

    public String getPlan_suspend_end() {
        return plan_suspend_end;
    }

    public void setPlan_suspend_end(String plan_suspend_end) {
        this.plan_suspend_end = plan_suspend_end;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "PauseDetailsInfoData{" +
                "id='" + id + '\'' +
                ", hire_id='" + hire_id + '\'' +
                ", uid='" + uid + '\'' +
                ", suspend_describe='" + suspend_describe + '\'' +
                ", suspend_days='" + suspend_days + '\'' +
                ", suspend_start='" + suspend_start + '\'' +
                ", suspend_end='" + suspend_end + '\'' +
                ", plan_suspend_end='" + plan_suspend_end + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
