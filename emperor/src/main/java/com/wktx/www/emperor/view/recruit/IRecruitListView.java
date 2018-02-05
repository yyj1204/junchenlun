package com.wktx.www.emperor.view.recruit;

import com.wktx.www.emperor.apiresult.recruit.recruitlist.RecruitListInfoData;
import com.wktx.www.emperor.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/24.
 * 招聘片段---职位检索结果
 */

public interface IRecruitListView extends IView<List<RecruitListInfoData>> {
    /**
     * 获取工作类型ID
     */
    String getJobTypeId();

    /**
     * 获取擅长类目ID
     */
    String getCategoryId();

    /**
     * 获取擅长平台ID
     */
    String getPlatformId();

    /**
     * 获取客服类型ID
     */
    String getServiceId();

    /**
     * 获取工作经验ID
     */
    String getExperienceId();

    /**
     * 获取性别ID
     */
    String getSexId();
}
