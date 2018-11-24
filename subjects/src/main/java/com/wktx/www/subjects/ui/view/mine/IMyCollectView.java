package com.wktx.www.subjects.ui.view.mine;

import com.wktx.www.subjects.apiresult.main.condition.ConditionInfoData;
import com.wktx.www.subjects.apiresult.main.demand.DemandListInfoData;
import com.wktx.www.subjects.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/15.
 * 我的收藏界面 | 面试记录界面 | 浏览记录
 */

public interface IMyCollectView extends IView<List<DemandListInfoData>> {

    /**
     * 获取工作类型
     */
    String getJobType();

    /**
     * 获取取消、收藏是否成功的回调
     */
    void onCancelCollectResult(boolean isSuccess, String result);

    /**
     * 获取列表检索条件成功的回调
     */
    void onGetConditionSuccessResult(ConditionInfoData result);

    /**
     * 获取列表检索条件失败的回调
     */
    void onGetConditionFailureResult(String result);
}
