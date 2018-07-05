package com.wktx.www.emperor.apiresult.staff.pause;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---暂停工作信息
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                 "id": "16","suspend_describe": "暂停原因","suspend_days": "1"
 *                }
 *          }
 * }
 */

public class PauseData {
    private int code;
    private String msg;
    private PauseInfoData info;

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

    public PauseInfoData getInfo() {
        return info;
    }

    public void setInfo(PauseInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "PauseData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
