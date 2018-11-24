package com.wktx.www.emperor.apiresult.staff.report;

import java.util.List;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---工作报告---工作安排列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{"id": "1","demand_title":"测试任务1", "demand_content": "628","end_time": "1523375999","shop_name": "测试店铺1","shop_logo": ""}]
 *          }
 * }
 */

public class StaffWorkListData {
    private int code;
    private String msg;
    private List<StaffWorkListInfoData> info;

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

    public List<StaffWorkListInfoData> getInfo() {
        return info;
    }

    public void setInfo(List<StaffWorkListInfoData> info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StaffWorkListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
