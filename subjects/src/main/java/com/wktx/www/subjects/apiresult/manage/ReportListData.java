package com.wktx.www.subjects.apiresult.manage;

import java.util.List;

/**
 * Created by yyj on 2018/3/6.
 * 我的工作---管理工作---安排工作---报告列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{ "id": "27","add_time": "2018-06-26 11:16:01","is_evaluate": 0}]
 *          }
 * }
 */

public class ReportListData {
    private int code;
    private String msg;
    private List<ReportListInfoData> info;

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

    public List<ReportListInfoData> getInfo() {
        return info;
    }

    public void setInfo(List<ReportListInfoData> info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ReportListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
