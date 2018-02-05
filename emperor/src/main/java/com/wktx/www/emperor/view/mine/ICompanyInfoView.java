package com.wktx.www.emperor.view.mine;

import com.wktx.www.emperor.apiresult.login.login8register.RegisterInfoData;
import com.wktx.www.emperor.view.IView;

/**
 * Created by yyj on 2018/1/18.
 * 公司信息界面
 */

public interface ICompanyInfoView extends IView<RegisterInfoData> {


    /**
     * 退出登录的回调
     */
    void onLogout(boolean isSuccess, String msg);

}
