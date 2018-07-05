package com.wktx.www.emperor.ui.view.login;

import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/15.
 * 忘记密码&修改密码&设置支付密码界面
 */

public interface IForgetPwdView extends IView<String> {

    /**
     * 获取输入手机号码(旧密码)
     */
    String getPhoneStr();

    /**
     * 获取验证码
     */
    String getCodeStr();

    /**
     * 获取输入的新密码
     */
    String getPwd1Str();

    /**
     * 获取第二次输入的新密码
     */
    String getPwd2Str();

    /**
     * 获取验证码的回调
     */
    void onSendCodeResult(boolean isSuccess, String msg);

}
