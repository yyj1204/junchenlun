package com.wktx.www.emperor.ui.view.recruit.hire;

import com.wktx.www.emperor.apiresult.recruit.hire.CouponBalanceInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/2/2.
 * 美工简历---简历---雇佣---确认订单信息
 */

public interface IOrdersInfoView extends IView<CouponBalanceInfoData> {

    /**
     * 获取优惠券ID
     */
    String getCouponId();

    /**
     * 获取可用余额
     */
    String getUsableBalance();

    /**
     * 取消雇佣订单是否成功的回调
     */
    void onCancelOrdersResult(boolean isSuccess, String result);
}
