package com.wktx.www.emperor.apiresult.mine.pay;

/**
 * Created by yyj on 2018/1/18.
 * 支付宝支付（钱包充值）
 */

public class AliPayData {
    private int code;
    private String msg;
    private String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "AliPayData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
