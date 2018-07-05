package com.wktx.www.emperor.apiresult.staff.attendance;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---考勤记录列表内容
 * "day": 1,"type": 1
 */

public class AttendanceRecordBean {
    private String day;//日期
    private String type;//1:出勤 其他:待定

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AttendanceRecordBean{" +
                "day='" + day + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
