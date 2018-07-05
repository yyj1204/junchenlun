package com.wktx.www.emperor.ui.view.main;

import com.wktx.www.emperor.apiresult.main.home.HomeInfoData;
import com.wktx.www.emperor.apiresult.main.home.JobTypeInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/15.
 * 首页界面
 */

public interface IHomeView extends IView<HomeInfoData> {

    /**
     * 获取顶部横向职业类型成功的回调
     */
    void onGetJobTypeSuccessResult(List<JobTypeInfoData> result);

    /**
     * 获取顶部横向职业类型失败的回调
     */
    void onGetJobTypeFailureResult(String result);

}
