package com.wktx.www.emperor.ui.view.recruit;


import com.wktx.www.emperor.apiresult.recruit.demand.DemandListInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/26.
 * 简历界面
 */

public interface IResumeView extends IView<ResumeInfoData> {
    /**
     * 获取面试员工(收藏、取消收藏)是否成功的回调
     */
    void onInterviewResult(boolean isSuccess, String msg);

    /**
     * 获取需求列表成功回调
     */
    void onDemandListSuccessResult(List<DemandListInfoData> result);

    /**
     * 获取需求列表失败回调
     */
    void onDemandListFailureResult(String msg);
}
