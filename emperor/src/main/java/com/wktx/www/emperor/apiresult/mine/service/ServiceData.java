package com.wktx.www.emperor.apiresult.mine.service;

/**
 * Created by yyj on 2018/2/8.
 * 联系客服
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{"phone": "123456789","qq": "123456789","service_time": "9:00-20:00"}
 *          }
 * }
 */

public class ServiceData {
    private int code;
    private String msg;
    private ServiceInfoData info;

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
    public ServiceInfoData getInfo() {
        return info;
    }
    public void setInfo(ServiceInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "ServiceData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
