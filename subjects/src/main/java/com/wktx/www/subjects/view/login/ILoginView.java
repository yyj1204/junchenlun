package com.wktx.www.subjects.view.login;


import com.wktx.www.subjects.apiresult.login.login8register.RegisterData;
import com.wktx.www.subjects.view.IView;

/**
 * Created by yyj on 2018/1/18.
 * 登录界面
 */

public interface ILoginView extends IView<RegisterData> {

    /**
     * 获取输入手机号码
     */
    String getPhoneStr();

    /**
     * 获取输入的密码
     */
    String getPwdStr();

}
