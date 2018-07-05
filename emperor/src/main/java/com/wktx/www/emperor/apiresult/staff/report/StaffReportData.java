package com.wktx.www.emperor.apiresult.staff.report;

import java.util.List;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---工作报告（任务）
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{"id": "1","demand_title":"测试任务1","end_time": "1523375999","shop_name": "测试店铺1","shop_logo": ""}]
 *          }
 * }
 */

public class StaffReportData {
    private int code;
    private String msg;
    private List<StaffReportInfoData> info;

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

    public List<StaffReportInfoData> getInfo() {
        return info;
    }

    public void setInfo(List<StaffReportInfoData> info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StaffReportData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
