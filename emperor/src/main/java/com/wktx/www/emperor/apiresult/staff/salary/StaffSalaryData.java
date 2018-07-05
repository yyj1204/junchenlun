package com.wktx.www.emperor.apiresult.staff.salary;

/**
 * Created by yyj on 2018/3/21.
 * 我的员工---管理员工---员工工资
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *               "total_expenditure": "0.01","trusteeship_amount": "0.01","already_amount": "0.00",
 *               "trusteeship_list": [{"start_time": "1521388800","end_time": "1524067199","trusteeship_wages": "0.01"}],
 *               "already_hair": [{"paid_time": "1519920000","amount": "3500.00"}]
 *                }
 *          }
 * }
 */

public class StaffSalaryData {
    private int code;
    private String msg;
    private StaffSalaryInfoData info;

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

    public StaffSalaryInfoData getInfo() {
        return info;
    }

    public void setInfo(StaffSalaryInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StaffSalaryData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
