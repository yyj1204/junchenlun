package com.wktx.www.emperor.ui.view.mine.purse;

import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/15.
 * 钱包提现界面
 */

public interface IPurseWithdrawView extends IView<String> {

    /**
     * 获取输入手机号码
     */
    String getPhoneStr();

    /**
     * 获取验证码
     */
    String getCodeStr();

    /**
     * 获取输入的支付密码
     */
    String getPayPwdStr();

    /**
     * 获取输入的支付宝账号
     */
    String getAlipayNumStr();

    /**
     * 获取输入的身份证号码/信用代码
     */
    String getIdNumStr();

    /**
     * 获取输入的提现金额
     */
    String getWithdrawalMoney();

    /**
     * 获取验证码的回调
     */
    void onSendCodeResult(boolean isSuccess, String msg);

}
