package com.wktx.www.emperor.ui.view.staff;

import com.wktx.www.emperor.apiresult.mine.store.StoreListInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/26.
 * 管理我的员工---安排工作界面
 */

public interface IStaffArrangeWorkView extends IView<List<StoreListInfoData>> {

    /**
     * 获取输入需求标题
     */
    String getDemandTitle();

    /**
     * 获取输入需求内容
     */
    String getDemandContent();

    /**
     * 获取选中店铺id
     */
    String getStoreId();

    /**
     * 获取截止日期
     */
    String getEndTime();

    /**
     * 获取安排工作是否成功的回调
     */
    void onArrangeWorkResult(boolean isSuccess, String msg);
}
