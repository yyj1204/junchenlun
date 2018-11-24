package com.wktx.www.emperor.apiresult.staff.staff;


import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 我的员工 \ 雇佣记录
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                   "hire_id": "151","rid": "1","tow": "1","tow_name": "美工","project_start_time": "1520006400",
 *                   "dismissal_id": "0","project_end_time": "1522684799","hire_price": "0.01","is_pay": "1",
 *                   "name": "胡图图","picture": "","type": "1","status_desc": "手动取消订单"}]
 *          }
 * }
 */

public class StaffData {
    private int code;
    private String msg;
    private List<StaffInfoData> info;

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
    public List<StaffInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<StaffInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "StaffData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
