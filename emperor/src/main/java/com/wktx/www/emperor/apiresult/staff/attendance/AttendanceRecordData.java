package com.wktx.www.emperor.apiresult.staff.attendance;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---考勤记录
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                 "attendance_count": 8,"absence_from_duty_count": 3,
 *                 "attendance_record": [{"day": 1,"type": 1}]
 *                 }
 *          }
 * }
 */

public class AttendanceRecordData {
    private int code;
    private String msg;
    private AttendanceRecordInfoData info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AttendanceRecordInfoData getInfo() {
        return info;
    }

    public void setInfo(AttendanceRecordInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "AttendanceRecordData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
