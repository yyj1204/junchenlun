package com.wktx.www.subjects.apiresult.manage.salary;

/**
 * Created by yyj on 2018/3/21.
 * 我的工作---管理工作---我的工资
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                "hire_price": "3.00","trusteeship_wages": "0.00","paid_wages": "0.00",
 *                "payList": [{
 *                        "id": "40","amount": "1.00","add_time": "2018-06-26 17:10:18","remark": "同意解雇申请，雇主支付薪资"
 *                        }]
 *               }
 *          }
 * }
 */

public class SalaryData {
    private int code;
    private String msg;
    private SalaryInfoData info;

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

    public SalaryInfoData getInfo() {
        return info;
    }

    public void setInfo(SalaryInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "SalaryData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
