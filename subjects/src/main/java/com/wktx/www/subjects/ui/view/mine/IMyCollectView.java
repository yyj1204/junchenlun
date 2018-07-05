package com.wktx.www.subjects.ui.view.mine;

import com.wktx.www.subjects.apiresult.main.position.PositionListInfoData;
import com.wktx.www.subjects.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/15.
 * 我的收藏界面 \ 面试记录界面
 */

public interface IMyCollectView extends IView<List<PositionListInfoData>> {

    /**
     * 获取取消、收藏是否成功的回调
     */
    void onCancelCollectResult(boolean isSuccess, String result);
}
