package com.wktx.www.emperor.apiresult.staff.kpi;

import com.wktx.www.emperor.apiresult.staff.fire.StaffFireInfoData;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---考核指标
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                "id": "16","uid": "2684", "name": "15750965106","sex": "2",
 *                "picture": "http://shop.junchenlun.com/public/upload/head_pic/2018/06-29/201806291044030265.jpg",
 *                "working_years": "7","residential_city": "上海市-市辖区-黄浦区","qq": "123456","phone": "15750965106","project_end_time": "2018-07-27",
 *                "work_days": 21,"arrangement_work_count": "3","attendance_days": "2",
 *                "bgat": [{"id": "5","name": "3C数码"},{"id": "6","name": "家电办公"}]
 *                }
 *          }
 * }
 */

public class StaffKPIData {
    private int code;
    private String msg;
    private StaffKPIInfoData info;

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

    public StaffKPIInfoData getInfo() {
        return info;
    }

    public void setInfo(StaffKPIInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "StaffKPIData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
