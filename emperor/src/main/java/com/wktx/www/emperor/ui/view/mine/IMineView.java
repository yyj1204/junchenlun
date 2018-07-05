package com.wktx.www.emperor.ui.view.mine;

import com.wktx.www.emperor.apiresult.mine.center.CenterInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/15.
 * 我的账户界面
 */

public interface IMineView extends IView<CenterInfoData> {

    /**
     * 退出登录的回调
     */
    void onLogout(boolean isSuccess, String msg);
}
