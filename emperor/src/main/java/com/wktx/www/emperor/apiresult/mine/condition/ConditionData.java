package com.wktx.www.emperor.apiresult.mine.condition;
/**
 * Created by yyj on 2018/2/8.
 * 个人中心---各个列表界面的检索条件
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                   "tow": [{"id": "0","name": "全部"}]
 *                   "type": [{"id": "0","name": "全部"}]
 *                }
 *          }
 * }
 */

public class ConditionData {
    private int code;
    private String msg;
    private ConditionInfoData info;

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
    public ConditionInfoData getInfo() {
        return info;
    }
    public void setInfo(ConditionInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "StoreConditionData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
