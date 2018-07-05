package com.wktx.www.subjects.ui.view.mine.resume;
import com.wktx.www.subjects.apiresult.main.condition.ConditionInfoData;
import com.wktx.www.subjects.ui.view.IView;


/**
 * Created by yyj on 2018/1/15.
 * 我的简历 --- 工作经历(增删改)
 */

public interface IWorkExperienceView extends IView<ConditionInfoData> {

    /**
     * 获取职位名称
     */
    String getPositionName();

    /**
     * 获取公司名称
     */
    String getCompanyName();

    /**
     * 获取开始时间
     */
    String getBeginTime();

    /**
     * 获取结束时间
     */
    String getEndTime();

    /**
     * 获取工作简介
     */
    String getWorkContent();

    /**
     * 获取编辑工作经历是否成功的回调
     */
    void onChangeWorkExperienceResult(boolean isSuccess, String result);
}
