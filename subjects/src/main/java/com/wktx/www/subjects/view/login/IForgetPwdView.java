package com.wktx.www.subjects.view.login;


import com.wktx.www.subjects.apiresult.login.ForgetPwdData;
import com.wktx.www.subjects.view.IView;

/**
 * Created by yyj on 2018/1/15.
 * 忘记密码界面
 */

public interface IForgetPwdView extends IView<ForgetPwdData> {

    /**
     * 获取输入手机号码
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