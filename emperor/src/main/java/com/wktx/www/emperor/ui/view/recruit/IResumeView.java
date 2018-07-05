package com.wktx.www.emperor.ui.view.recruit;


import com.wktx.www.emperor.apiresult.recruit.resume.ResumeInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/26.
 * 简历界面
 */

public interface IResumeView extends IView<ResumeInfoData> {
    /**
     * 获取面试员工(收藏、取消收藏)是否成功的回调
     */
    void onInterviewResult(boolean isSuccess, String msg);
}
