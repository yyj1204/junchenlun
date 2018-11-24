package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/3/6.
 * 我的工作---工作管理---暂停工作信息
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                  "id": "30","uid": "2649","hire_id": "560","suspend_describe": "先暂停吧","suspend_days": "2",
 *                  "suspend_start": "2018-08-13","suspend_end": "2018-08-14",
 *                  "plan_suspend_end": "0","add_time": "2018-08-13 16:00:28"
 *                }
 *          }
 * }
 */

public class PauseDetailsData {
    private int code;
    private String msg;
    private PauseDetailsInfoData info;

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

    public PauseDetailsInfoData getInfo() {
        return info;
    }

    public void setInfo(PauseDetailsInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "PauseDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
