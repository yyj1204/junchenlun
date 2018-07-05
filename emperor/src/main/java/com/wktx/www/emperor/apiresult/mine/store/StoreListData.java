package com.wktx.www.emperor.apiresult.mine.store;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 店铺列表(工作报告检索条件)
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{"id": "3","shop_name": "测试店铺3","shop_logo":""}]
 *          }
 * }
 */

public class StoreListData {
    private int code;
    private String msg;
    private List<StoreListInfoData> info;

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
    public List<StoreListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<StoreListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "StoreListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
