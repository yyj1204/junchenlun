package com.wktx.www.emperor.ui.view.recruit;

import com.wktx.www.emperor.apiresult.mine.store.StoreListInfoData;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandReleaseConditionInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/26.
 * 需求发布界面
 */

public interface IDemandReleaseView extends IView<DemandReleaseConditionInfoData> {

    /**
     * 获取输入需求标题
     */
    String getDemandTitle();

    /**
     * 获取输入需求内容
     */
    String getDemandContent();

    /**
     * 获取输入需求预算
     */
    String getDemandBudget();

    /**
     * 获取截止时间
     */
    String getEndTime();

    /**
     * 获取店铺检索成功的回调
     */
    void onGetStoreConditionSuccessResult(List<StoreListInfoData> result);

    /**
     * 获取店铺检索失败的回调
     */
    void onGetStoreConditionFailureResult(String result);

    /**
     * 获取需求发布是否成功的回调
     */
    void onDemandReleaseResult(boolean isSuccess,String msg);
}
