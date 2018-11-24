package com.wktx.www.emperor.apiresult.staff.manage;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                  "hire_id": "151","rid": "1","tow": "1","tow_name": "客服","project_start_time": "1520006400","project_end_time": "1522684799",
 *                  "hire_price": "0.01","dismissal_id": "0","type": "1","name": "胡图图","picture": "","sex": "2",
 *                  "report_count": 0,"attendance_count": 0,"absence_from_duty_count": 0,"evaluate_id": "11"
 *                }
 *          }
 * }
 */

public class StaffManageData {
    private int code;
    private String msg;
    private StaffManageInfoData info;

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

    public StaffManageInfoData getInfo() {
        return info;
    }

    public void setInfo(StaffManageInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StaffManageData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }


}
