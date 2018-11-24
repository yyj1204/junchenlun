package com.wktx.www.emperor.basemvp;

import com.wktx.www.emperor.apiresult.login.AccountInfoData;

/**
 * 视图基类
 * Created by yyj on 2018/1/12.
 */

public interface IBaseView {
    /**
     * 获取用户的token与user_id
     */
    AccountInfoData getUserInfo();

    /**
     * 未登录或者token过期
     */
    void onLoginFailure(String msg);
}
