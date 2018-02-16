package com.wktx.www.emperor.apiresult.mine.pay;

/**
 * Created by yyj on 2018/1/18.
 * 微信支付（钱包充值）详情
 */

public class WeChatPayInfoData {
    private WechatPayBean pay_info;

    public WechatPayBean getPay_info() {
        return pay_info;
    }

    public void setPay_info(WechatPayBean pay_info) {
        this.pay_info = pay_info;
    }

    @Override
    public String toString() {
        return "WeChatPayInfoData{" +
                "pay_info=" + pay_info +
                '}';
    }
}
