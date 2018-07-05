package com.wktx.www.emperor.apiresult.recruit.hire;

/**
 * Created by yyj on 2018/2/8.
 * 简历---雇佣信息
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                 "hire_id": "13","hire_type": "1","project_start_time": "1519660800","project_end_time": 1522339199,
 *                 "hire_price": "3500.00","user_money": "0.01",
 *                 "coupon_list": [{
 *                     "id":"1","money":"4500", "condition":"使用条件，满足金额才能使用",
 *                     "end_time":"结束时间","available":"优惠券是否可用 true:可用 false:不可用"}]
 *                }
 *          }
 * }
 */

public class HireData {
    private int code;
    private String msg;
    private HireInfoData info;

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
    public HireInfoData getInfo() {
        return info;
    }
    public void setInfo(HireInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "HireData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
