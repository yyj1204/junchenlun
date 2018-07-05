package com.wktx.www.emperor.ui.view.staff;

import com.wktx.www.emperor.apiresult.staff.complaint.ComplaintInfoData;
import com.wktx.www.emperor.apiresult.staff.pause.PauseInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/26.
 * 管理我的员工---暂停工作、发起投诉界面
 */

public interface IStaffPauseWorkView extends IView<String> {

    /**
     * 获取输入暂停原因（投诉问题）
     */
    String getPauseCause();

    /**
     * 获取暂停时间（投诉内容）
     */
    String getPauseTime();

    /**
     * 获取暂停内容成功的回调
     */
    void onGetPauseSuccessResult(PauseInfoData result);

    /**
     * 获取暂停内容失败的回调
     */
    void onGetPauseFailureResult(String result);

    /**
     * 获取投诉内容成功的回调
     */
    void onGetComplaintSuccessResult(ComplaintInfoData result);

    /**
     * 获取投诉内容失败的回调
     */
    void onGetComplaintFailureResult(String result);
}
