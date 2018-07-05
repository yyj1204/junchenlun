package com.wktx.www.emperor.ui.view.staff.report;

import com.wktx.www.emperor.apiresult.staff.report.ReportDetailsInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/15.
 * 员工管理---工作报告详情界面
 */

public interface IReportDetailsView extends IView<ReportDetailsInfoData> {

    /**
     * 获取服务态度
     */
    String getAttitude();
    /**
     * 获取工作能力
     */
    String getAbility();
    /**
     * 获取响应速度
     */
    String getSpeed();
    /**
     * 获取评价内容
     */
    String getEvaluateContent();

    /**
     * 获取评价报告是否成功的回调
     */
    void onEvaluateReportResult(boolean isSuccess,String msg);
}
