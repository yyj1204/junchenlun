package com.wktx.www.emperor.ui.view.staff;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionInfoData;
import com.wktx.www.emperor.apiresult.staff.staff.StaffInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/15.
 *我的员工 \ 雇佣记录界面
 */

public interface IStaffView extends IView<List<StaffInfoData>> {

    /**
     * 获取工作类型
     */
    String getJobType();

    /**
     * 获取工作类型
     */
    String getHireState();

    /**
     * 获取列表检索条件成功的回调
     */
    void onGetConditionSuccessResult(ConditionInfoData result);

    /**
     * 获取列表检索条件失败的回调
     */
    void onGetConditionFailureResult(String result);
}
