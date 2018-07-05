package com.wktx.www.emperor.ui.view.mine.purse;

import com.wktx.www.emperor.apiresult.mine.pay.WechatPayInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/18.
 * 钱包充值界面
 */

public interface IPurseRechargeView extends IView<WechatPayInfoData> {

    /**
     * 获取输入充值金额
     */
    String getMoneyStr();

    /**
     * 获取支付宝订单信息是否成功的回调  成功---调起支付宝支付
     */
    void onAlipaySuccessResult(boolean isSuccess,String msg);
}
