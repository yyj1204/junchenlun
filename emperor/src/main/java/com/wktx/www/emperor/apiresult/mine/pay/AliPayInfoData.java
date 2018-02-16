package com.wktx.www.emperor.apiresult.mine.pay;

/**
 * Created by yyj on 2018/1/18.
 * 支付宝支付（钱包充值）详情
 */

public class AliPayInfoData {
    private String pay_info;

    public String getPay_info() {
        return pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
    }

    @Override
    public String toString() {
        return "AliPayInfoData{" +
                "pay_info=" + pay_info +
                '}';
    }
}
