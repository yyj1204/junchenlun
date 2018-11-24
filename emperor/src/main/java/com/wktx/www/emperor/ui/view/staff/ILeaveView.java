package com.wktx.www.emperor.ui.view.staff;

import com.wktx.www.emperor.apiresult.staff.leave.LeaveListInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;


/**
 * Created by yyj on 2018/1/15.
 * 请假列表（同意、拒绝）
 */

public interface ILeaveView extends IView<List<LeaveListInfoData>> {

    /**
     * 获取请假同意（拒绝）是否成功的回调
     */
    void onLeaveResult(boolean isSuccess, String msg);
}
