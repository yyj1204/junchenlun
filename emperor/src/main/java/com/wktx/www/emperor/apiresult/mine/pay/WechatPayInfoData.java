package com.wktx.www.emperor.apiresult.mine.pay;

/**
 * Created by yyj on 2017/10/18.
 * 微信订单信息
 */

public class WechatPayInfoData {
    private String appid;//应用ID:微信开放平台审核通过的应用APPID  appId
    private String noncestr;//随机字符串:随机字符串，不长于32位。  nonceStr
    private String pay_package;//扩展字段:暂填写固定值Sign=WXPay  packageValue
    private String partnerid;//商户号:微信支付分配的商户号  partnerId
    private String prepayid;//预支付交易会话ID:微信返回的支付交易会话ID  prepayId
    private String timestamp;//时间戳  timeStamp
    private String sign;//签名

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPay_package() {
        return pay_package;
    }

    public void setPay_package(String pay_package) {
        this.pay_package = pay_package;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "WechatPayInfoData{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", pay_package='" + pay_package + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
