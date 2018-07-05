package com.wktx.www.subjects.ui.view.manage;
import com.wktx.www.subjects.ui.view.IView;

import java.util.ArrayList;


/**
 * Created by yyj on 2018/1/15.
 * 发布报告
 */

public interface IReportReleaseView extends IView<String> {

    /**
     * 获取今日工作内容
     */
    String getWorkContent();

    /**
     * 获取店铺目前现状
     */
    String getStoreState();

    /**
     * 获取往后运营计划
     */
    String getOperationPlan();

    /**
     * 获取需要帮助
     */
    String getNeedHelp();

    /**
     * 获取多张数据表现图片
     */
    ArrayList<String> getDataImgUrls();

    /**
     * 获取图片路径是否成功的回调
     */
    void onGetImgUrlResult(boolean isSuccess, String result);
}
