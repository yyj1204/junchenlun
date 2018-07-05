package com.wktx.www.emperor.ui.view.staff.report;

import com.wktx.www.emperor.apiresult.mine.store.StoreListInfoData;
import com.wktx.www.emperor.apiresult.staff.report.StaffReportInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/15.
 * 员工管理---工作报告（任务）界面
 */

public interface IStaffReportView extends IView<List<StaffReportInfoData>> {

    /**
     * 获取店铺id类型
     */
    String getStoreId();

    /**
     * 获取店铺检索成功的回调
     */
    void onGetConditionSuccessResult(List<StoreListInfoData> result);

    /**
     * 获取店铺检索失败的回调
     */
    void onGetConditionFailureResult(String result);
}
