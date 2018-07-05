package com.wktx.www.emperor.apiresult.recruit.hire;

/**
 * Created by yyj on 2018/2/8.
 * 简历---雇佣---使用优惠券或者余额
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                 "hire_id": "13","project_start_time": "1519660800","project_end_time": 1522339199,"hire_money": "3500.00","user_money_pay_all":0
 *                }
 *          }
 * }
 */

public class CouponBalanceData {
    private int code;
    private String msg;
    private CouponBalanceInfoData info;

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
    public CouponBalanceInfoData getInfo() {
        return info;
    }
    public void setInfo(CouponBalanceInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "CouponBalanceData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
