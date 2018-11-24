package com.wktx.www.subjects.ui.view.manage;
import com.wktx.www.subjects.apiresult.manage.FireDetailsInfoData;
import com.wktx.www.subjects.ui.view.IView;

/**
 * Created by yyj on 2018/5/8.
 * 解雇详情
 */

public interface IFireDetailsView extends IView<FireDetailsInfoData> {

    /**
     * 接受解雇（拒绝解雇、申请介入）是否成功的回调
     */
    void onFireResult(boolean isSuccess, String result);
}
