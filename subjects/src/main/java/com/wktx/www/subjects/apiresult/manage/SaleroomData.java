package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/3/6.
 * 我的工作---管理工作---销售额
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                 "total_sales": "1266.00","last_month_sales": "1266.00",
 *                 "last_sales_data": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png"
 *                 }
 *          }
 * }
 */

public class SaleroomData {
    private int code;
    private String msg;
    private SaleroomInfoData info;

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

    public SaleroomInfoData getInfo() {
        return info;
    }

    public void setInfo(SaleroomInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "SaleroomData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
