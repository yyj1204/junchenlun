package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/2/8.
 * 公司解雇详情
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                  "id": "19","reason": "腿","should_paid_wages": "0.10","be_willing_paid_wages": "1.00",
 *                  "add_time": "2018-06-25 02:28:22","update_time": "0","status": "0","remark": null
 *                    }
 *          }
 * }
 */

public class FireDetailsData {
    private int code;
    private String msg;
    private FireDetailsInfoData info;

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
    public FireDetailsInfoData getInfo() {
        return info;
    }
    public void setInfo(FireDetailsInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "FireDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
