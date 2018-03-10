package com.wktx.www.emperor.apiresult.recruit.hire;

/**
 * Created by yyj on 2018/2/8.
 * 简历---雇佣信息---托管工资
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                  "hire_id": "75","hire_sn": "15197978878508","is_pay": "0","hire_price": "3500.00","total_amount": "3500.00",
 *                  "user_money": "0.00","coupon_price": "0.00","project_start_time": "1519747200","project_end_time": "1522166399","hire_time": "1",
 *                  "hire_type": "1","name": "胡图图","picture": "","sex": "1","working_years": "2","user_money_pay_all": 0,"bgat": "服装内衣/母婴玩具/精品鞋包"
 *                }
 *          }
 * }
 */

public class TrusteeshipSalaryData {
    private int code;
    private String msg;
    private TrusteeshipSalaryInfoData info;

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
    public TrusteeshipSalaryInfoData getInfo() {
        return info;
    }
    public void setInfo(TrusteeshipSalaryInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "TrusteeshipSalaryData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
