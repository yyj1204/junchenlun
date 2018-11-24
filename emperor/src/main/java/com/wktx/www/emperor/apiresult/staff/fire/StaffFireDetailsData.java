package com.wktx.www.emperor.apiresult.staff.fire;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---解雇详情
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                "id": "33","hire_id": "560","rid": "13","reason": "不会吧",
 *                "should_paid_wages": "0.00","be_willing_paid_wages": "10.00",
 *                "add_time": "1533868694","update_time": "1533885927",
 *                "status": "3","remark": null,"work_days": 1,"name": "啊饭",
 *                "picture": "http://shop.junchenlun.com/public/upload/head_pic/2018/07-03/201807031134475127.jpg",
 *                "sex": "1","tow": "主播"
 *                }
 *          }
 * }
 */

public class StaffFireDetailsData {
    private int code;
    private String msg;
    private StaffFireDetailsInfoData info;

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

    public StaffFireDetailsInfoData getInfo() {
        return info;
    }

    public void setInfo(StaffFireDetailsInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StaffFireDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
