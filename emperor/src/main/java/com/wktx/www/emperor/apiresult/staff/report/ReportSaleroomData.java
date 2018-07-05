package com.wktx.www.emperor.apiresult.staff.report;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---工作报告---销售额
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                 "total_sales": "1266.00","last_month_sales": "1266.00",
 *                 "last_sales_data": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png",
 *                 "list": [{"id": "0","name": "上月"}]
 *                 }
 *          }
 * }
 */

public class ReportSaleroomData {
    private int code;
    private String msg;
    private ReportSaleroomInfoData info;

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

    public ReportSaleroomInfoData getInfo() {
        return info;
    }

    public void setInfo(ReportSaleroomInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ReportSaleroomData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
