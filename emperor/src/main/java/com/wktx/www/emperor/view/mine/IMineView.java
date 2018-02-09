package com.wktx.www.emperor.view.mine;

import com.wktx.www.emperor.apiresult.mine.UserInfoData;
import com.wktx.www.emperor.view.IView;

/**
 * Created by yyj on 2018/1/15.
 * 我的账户界面
 */

public interface IMineView extends IView<UserInfoData> {

    /**
     * 退出登录的回调
     */
    void onLogout(boolean isSuccess, String msg);
}
