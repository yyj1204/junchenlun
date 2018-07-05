package com.wktx.www.emperor.ui.view.main;

import com.wktx.www.emperor.apiresult.main.artistcase.WorksConditionInfoData;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksListInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/24.
 * 首页片段---美工案例
 */

public interface IArtistCaseView extends IView<List<WorksListInfoData>> {
    /**
     * 获取设计类型ID
     */
    String getDesignTypeId();

    /**
     * 获取擅长类目ID
     */
    String getCategoryId();

    /**
     * 获取最多浏览ID
     */
    String getBrowseId();

    /**
     * 获取最多喜欢ID
     */
    String getLikedId();

    /**
     * 获取案例列表检索条件成功的回调
     */
    void onGetConditionSuccessResult(WorksConditionInfoData result);

    /**
     * 获取案例列表检索条件失败的回调
     */
    void onGetConditionFailureResult(String result);
}
