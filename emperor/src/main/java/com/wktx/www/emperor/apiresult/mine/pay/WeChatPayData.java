package com.wktx.www.emperor.apiresult.mine.pay;

/**
 * Created by yyj on 2018/1/18.
 * 微信支付（钱包充值）
 */

public class WeChatPayData {
    private int code;
    private String msg;
    private WechatPayInfoData info;

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

    public WechatPayInfoData getInfo() {
        return info;
    }

    public void setInfo(WechatPayInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "WeChatPayData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
