package com.wktx.www.subjects.ui.view.main;
import com.wktx.www.subjects.apiresult.main.PositionDetailsInfoData;
import com.wktx.www.subjects.ui.view.IView;

/**
 * Created by yyj on 2018/5/8.
 * 职位详情
 */

public interface IPositionDetailsView extends IView<PositionDetailsInfoData> {

    /**
     * 获取取消、收藏是否成功的回调
     */
    void onCancelCollectResult(boolean isSuccess, String result);

    /**
     * 获取投递简历是否成功的回调
     */
    void onSendResumeResult(boolean isSuccess, String result);
}
