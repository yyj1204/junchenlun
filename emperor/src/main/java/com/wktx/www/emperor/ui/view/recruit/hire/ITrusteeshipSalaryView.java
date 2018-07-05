package com.wktx.www.emperor.ui.view.recruit.hire;

import com.wktx.www.emperor.apiresult.mine.pay.WechatPayInfoData;
import com.wktx.www.emperor.apiresult.recruit.hire.TrusteeshipSalaryInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/18.
 * 托管工资界面
 */

public interface ITrusteeshipSalaryView extends IView<TrusteeshipSalaryInfoData> {

    /**
     * 获取雇佣ID
     */
    String getHireId();

    /**
     * 获取余额支付密码
     */
    String getPayPwd();

    /**
     * 取消雇佣订单是否成功的回调
     */
    void onCancelOrdersResult(boolean isSuccess, String result);

    /**
     * 余额支付是否成功的回调
     */
    void onBanlancePayResult(boolean isSuccess, String result);

    /**
     * 获取支付宝订单信息是否成功的回调  成功---调起支付宝支付
     */
    void onAlipayResult(boolean isSuccess, String result);

    /**
     * 获取微信订单信息是否成功的回调  成功---调起微信支付
     */
    void onWechatSuccessResult(WechatPayInfoData result);
    /**
     * 获取微信订单信息失败的回调
     */
    void onWechatFailureResult(String result);
}
