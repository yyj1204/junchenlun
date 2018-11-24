package com.wktx.www.emperor.ui.view.mine.authent;

import com.wktx.www.emperor.apiresult.mine.authent.AuthentAlipayInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/2/13.
 * 账户安全---支付宝认证界面
 */

public interface IAuthentAlipayView extends IView<String> {

    /**
     * 获取输入支付宝账户
     */
    String getAlipayNum();

    /**
     * 获取输入支付宝真实姓名
     */
    String getAlipayName();

    /**
     * 获取支付宝认证详情成功的回调
     */
    void onGetAuthentInfoSuccessResult(AuthentAlipayInfoData result);

    /**
     * 获取支付宝认证详情失败的回调
     */
    void onGetAuthentInfoFailureResult(String result);
}
