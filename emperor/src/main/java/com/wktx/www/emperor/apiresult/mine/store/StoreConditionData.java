package com.wktx.www.emperor.apiresult.mine.store;

/**
 * Created by yyj on 2018/3/9.
 * 个人中心---需要的选择参数 （平台、类目）
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                   "bgat": [{"id": "0","name": "服装内衣"}]
 *                   "bgap": [{"id": "0","name": "美工"}]
 *                }
 *          }
 * }
 */

public class StoreConditionData {
    private int code;
    private String msg;
    private StoreConditionInfoData info;

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
    public StoreConditionInfoData getInfo() {
        return info;
    }
    public void setInfo(StoreConditionInfoData info) {
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
