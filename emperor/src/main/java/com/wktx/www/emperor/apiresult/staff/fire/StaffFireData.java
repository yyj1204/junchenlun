package com.wktx.www.emperor.apiresult.staff.fire;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---发起解雇
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                "rid": "1","name": "啊西歌","picture": "","sex": "1","tow": "2","tow_name": "客服","start_time": "1521388800",
 *                "end_time": "1521529679","hire_days": "1","status": "1","should_paid_wages": "0","should_paid_wages": "0"
 *                }
 *          }
 * }
 */

public class StaffFireData {
    private int code;
    private String msg;
    private StaffFireInfoData info;

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

    public StaffFireInfoData getInfo() {
        return info;
    }

    public void setInfo(StaffFireInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StaffFireData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
