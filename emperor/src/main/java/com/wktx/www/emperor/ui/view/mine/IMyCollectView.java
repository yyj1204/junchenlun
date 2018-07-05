package com.wktx.www.emperor.ui.view.mine;

import com.wktx.www.emperor.apiresult.mine.browsingrecord.BrowsingRecordInfoData;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/15.
 * 我的收藏界面 \ 浏览记录界面
 */

public interface IMyCollectView extends IView<List<BrowsingRecordInfoData>> {

    /**
     * 获取工作类型
     */
    String getJobType();

    /**
     * 获取列表检索条件成功的回调
     */
    void onGetConditionSuccessResult(ConditionInfoData result);

    /**
     * 获取列表检索条件失败的回调
     */
    void onGetConditionFailureResult(String result);

    /**
     * 获取取消收藏简历是否成功的回调
     */
    void onCancelCollectResumeResult(boolean isSuccess,String result);
}
