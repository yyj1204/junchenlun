package com.wktx.www.emperor.apiresult.staff.leave;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 请假记录内容
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                  "id": "2","uid": "2681","hire_id": "560","reason": "打萨芬","image": "",
 *                  "start_time": "2018-08-13 15:35:35","end_time": "2018-08-14 15:35:35",
 *                  "status": "已拒绝","add_time": "2018-08-13 15:48:48"
 *                  }]
 *          }
 * }
 */

public class LeaveListData {
    private int code;
    private String msg;
    private List<LeaveListInfoData> info;

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
    public List<LeaveListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<LeaveListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "LeaveListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
