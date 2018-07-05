package com.wktx.www.emperor.ui.view.staff;

import com.wktx.www.emperor.apiresult.recruit.hire.HireInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/26.
 * 管理我的员工---续签界面
 */

public interface IStaffRenewalView extends IView<HireInfoData> {


    /**
     * 获取续签时间
     */
    String getRenewalTime();

    /**
     * 获取输入的提成方案
     */
    String getPushMoney();
}
