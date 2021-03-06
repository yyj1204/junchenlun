package com.wktx.www.emperor.apiresult.staff.report;

import java.util.List;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---工作报告（任务）---报告列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{ "id": "1","add_time": "1520910453", "evaluate_time": "1531132249"}]
 *          }
 * }
 */

public class StaffReportListData {
    private int code;
    private String msg;
    private List<StaffReportListInfoData> info;

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

    public List<StaffReportListInfoData> getInfo() {
        return info;
    }

    public void setInfo(List<StaffReportListInfoData> info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StaffReportListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
