package com.wktx.www.emperor.ui.view.staff;

import com.wktx.www.emperor.apiresult.staff.fire.StaffFireInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/26.
 * 管理我的员工---发起解雇界面
 */

public interface IStaffFireView extends IView<StaffFireInfoData> {

    /**
     * 获取输入的支付金额
     */
    String getAmount();

    /**
     * 获取解雇原因
     */
    String getFireCause();

    /**
     * 获取发起解雇是否成功的回调
     */
    void onFireResult(boolean isSuccess, String msg);
}
