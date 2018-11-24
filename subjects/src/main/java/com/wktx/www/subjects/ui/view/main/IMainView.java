package com.wktx.www.subjects.ui.view.main;

import com.wktx.www.subjects.apiresult.main.demand.BannerInfoData;
import com.wktx.www.subjects.apiresult.main.demand.DemandListInfoData;
import com.wktx.www.subjects.apiresult.main.condition.ConditionInfoData;
import com.wktx.www.subjects.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/5/8.
 * 首页职位招聘
 */

public interface IMainView extends IView<List<DemandListInfoData>> {
    /**
     * 获取工作类型ID
     */
    String getPositionId();

    /**
     * 获取擅长类目ID
     */
    String getCategoryId();

    /**
     * 获取擅长平台ID
     */
    String getPlatformId();

    /**
     * 获取工作经验ID
     */
    String getExperienceId();

    /**
     * 获取最低期望薪资
     */
    String getMinSalary();

    /**
     * 获取最高期望薪资
     */
    String getMaxSalary();

    /**
     * 获取列表检索条件成功的回调
     */
    void onGetConditionSuccessResult(ConditionInfoData result);

    /**
     * 获取列表检索条件失败的回调
     */
    void onGetConditionFailureResult(String result);

    /**
     * 获取轮播图成功的回调
     */
    void onGetBannerSuccessResult(BannerInfoData result);

    /**
     * 获取轮播图失败的回调
     */
    void onGetBannerFailureResult(String result);
}
