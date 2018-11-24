package com.wktx.www.emperor.ui.view.staff.fire;

import com.wktx.www.emperor.apiresult.staff.fire.StaffFireDetailsInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/26.
 * 管理我的员工---解雇详情界面
 */

public interface IStaffFireDetailsView extends IView<StaffFireDetailsInfoData> {

    /**
     * 获取取消解雇（申请后台介入）是否成功的回调
     */
    void onFireResult(boolean isSuccess, String msg);
}
