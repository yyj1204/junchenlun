package com.wktx.www.emperor.apiresult.mine.store;

/**
 * Created by yyj on 2018/2/8.
 * 店铺信息
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{"id": "4","bgap": "1","bgat": "1","shop_logo":"","shop_name": "编辑2",
 *         "shop_url": "1","tow_name": "美工","bgat_name": "服装内衣"}
 *          }
 * }
 */

public class StoreData {
    private int code;
    private String msg;
    private StoreInfoData info;

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
    public StoreInfoData getInfo() {
        return info;
    }
    public void setInfo(StoreInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "StoreData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
