package com.wktx.www.emperor.pay;

/**
 * Created by yyj on 2017/10/12
 */

public interface IPayView {

    //支付宝支付成功
     void alipaySuccess();

    //支付宝支付失败
     void alipayFailed();

    //微信支付成功
     void wxSuccess();

    //微信支付失败
     void wxFailed();
}
