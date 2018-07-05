package com.wktx.www.subjects.ui.view.mine.resume;
import com.wktx.www.subjects.apiresult.main.condition.ConditionInfoData;
import com.wktx.www.subjects.ui.view.IView;


/**
 * Created by yyj on 2018/1/15.
 * 我的简历 --- 应聘职位(增删改)
 */

public interface IResumePositionView extends IView<ConditionInfoData> {

    /**
     * 获取职位Id
     */
    String getPositionId();

    /**
     * 获取类目id
     */
    String getCategoryIds();

    /**
     * 获取平台id
     */
    String getPlatformId();

    /**
     * 获取工作经验id
     */
    String getExperienceId();

    /**
     * 获取风格id
     */
    String getStyleIds();

    /**
     * 获取打字速度
     */
    String getSpeed();

    /**
     * 获取薪资
     */
    String getSalary();


    /**
     * 获取编辑应聘职位是否成功的回调
     */
    void onChangeApplyPositionResult(boolean isSuccess, String result);
}
