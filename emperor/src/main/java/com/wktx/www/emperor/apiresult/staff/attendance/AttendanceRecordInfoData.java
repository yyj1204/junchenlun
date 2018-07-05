package com.wktx.www.emperor.apiresult.staff.attendance;

import com.wktx.www.emperor.apiresult.staff.attendance.AttendanceRecordBean;

import java.util.List;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---考勤记录内容
 * "attendance_count": 8,"absence_from_duty_count": 3,
 * "attendance_record": [{"day": 1,"type": 1}]
 */

public class AttendanceRecordInfoData {
    private String attendance_count;//出勤数
    private String absence_from_duty_count;//缺勤数
   private List<AttendanceRecordBean> attendance_record;//考勤记录

    public String getAttendance_count() {
        return attendance_count;
    }

    public void setAttendance_count(String attendance_count) {
        this.attendance_count = attendance_count;
    }

    public String getAbsence_from_duty_count() {
        return absence_from_duty_count;
    }

    public void setAbsence_from_duty_count(String absence_from_duty_count) {
        this.absence_from_duty_count = absence_from_duty_count;
    }

    public List<AttendanceRecordBean> getAttendance_record() {
        return attendance_record;
    }

    public void setAttendance_record(List<AttendanceRecordBean> attendance_record) {
        this.attendance_record = attendance_record;
    }

    @Override
    public String toString() {
        return "AttendanceRecordInfoData{" +
                "attendance_count='" + attendance_count + '\'' +
                ", absence_from_duty_count='" + absence_from_duty_count + '\'' +
                ", attendance_record=" + attendance_record +
                '}';
    }
}
