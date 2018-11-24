package com.wktx.www.subjects.apiresult.manage.kpi;

/**
 * Created by yyj on 2018/3/6.
 * 我的工作---考核指标
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                 "user_id": "2672",
 *                 "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/06-29/201806290952192574.jpeg",
 *                 "nickname": "尚仁网络旗舰店6.29",
 *                 "address_from": "福建省-泉州市-丰泽区",
 *                 "remark": "尚仁网络旗舰店6.29尚仁网络旗舰店6.29尚仁网络旗舰店6.29尚仁网络旗舰店6.29",
 *                 "phone": "20180629","qq": "123456","wechat": "123456",
 *                 "project_end_time": "2018-08-01",
 *                 "work_days": 32,
 *                 "arrangement_work_count": "1",
 *                 "attendance_days": "0",
 *                 "report_evaluate_count": "7"
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
