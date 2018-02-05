package com.wktx.www.subjects.view.mine;


import com.wktx.www.subjects.apiresult.mine.UserData;
import com.wktx.www.subjects.view.IView;

/**
 * Created by yyj on 2018/1/15.
 * 我的账户界面
 */

public interface IMineView extends IView<UserData> {

    /**
     * 退出登录的回调
     */
    void onLogout(boolean isSuccess, String msg);

}
