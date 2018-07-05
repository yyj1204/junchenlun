package com.wktx.www.emperor.ui.view.recruit.hire;

import com.wktx.www.emperor.apiresult.recruit.hire.HireInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/2/2.
 * 美工简历---简历---雇佣
 */

public interface IHireView extends IView<HireInfoData> {

    /**
     * 获取雇佣方式（包月/定制）
     */
    String getHireWay();

    /**
     * 获取客服轮班情况
     */
    String getServiceShifts();

    /**
     * 获取项目开始时间
     */
    String getBeginTime();

    /**
     * 获取项目结束时间
     */
    String getEndTime();

    /**
     * 获取输入的定制价格
     */
    String getcustomPrice();

    /**
     * 获取雇佣时间
     */
    String getHireTime();

    /**
     * 获取输入的提成方案
     */
    String getPushMoney();

    /**
     * 获取输入的需求内容
     */
    String getDemandContent();

    /**
     * 获取输入的QQ号码
     */
    String getQQNumber();

    /**
     * 获取输入的微信号码
     */
    String getWeChatNumber();
}
