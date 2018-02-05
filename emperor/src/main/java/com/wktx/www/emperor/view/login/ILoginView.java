package com.wktx.www.emperor.view.login;

import com.wktx.www.emperor.apiresult.login.login8register.RegisterInfoData;
import com.wktx.www.emperor.view.IView;

/**
 * Created by yyj on 2018/1/18.
 * 登录界面
 */

public interface ILoginView extends IView<RegisterInfoData> {

    /**
     * 获取输入手机号码
     */
    String getPhoneStr();

    /**
     * 获取输入的密码
     */
    String getPwdStr();

}
