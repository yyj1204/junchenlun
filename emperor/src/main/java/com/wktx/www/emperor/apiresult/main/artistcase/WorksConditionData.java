package com.wktx.www.emperor.apiresult.main.artistcase;

/**
 * Created by yyj on 2018/2/8.
 * 首页---美工案例列表界面的检索条件
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                   "bgat": [{"id": "0","name": "全部"}]
 *                   "design_pattern": [{"id": "0","name": "全部"}]
 *                }
 *          }
 * }
 */

public class WorksConditionData {
    private int code;
    private String msg;
    private WorksConditionInfoData info;

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
    public WorksConditionInfoData getInfo() {
        return info;
    }
    public void setInfo(WorksConditionInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "WorksConditionData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
