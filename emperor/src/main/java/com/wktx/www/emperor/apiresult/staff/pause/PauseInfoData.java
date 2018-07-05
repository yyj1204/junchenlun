package com.wktx.www.emperor.apiresult.staff.pause;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---暂停工作信息内容
 * "id": "16","suspend_describe": "暂停原因","suspend_days": "1"
 */

public class PauseInfoData {
    private String id;//暂停工作id
    private String suspend_describe;//暂停工作原因
    private String suspend_days;//暂停天数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "PauseInfoData{" +
                "id='" + id + '\'' +
                ", suspend_describe='" + suspend_describe + '\'' +
                ", suspend_days='" + suspend_days + '\'' +
                '}';
    }
}
