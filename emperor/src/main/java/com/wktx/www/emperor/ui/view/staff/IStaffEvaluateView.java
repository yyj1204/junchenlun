package com.wktx.www.emperor.ui.view.staff;
import com.wktx.www.emperor.ui.view.IView;

import java.util.ArrayList;

/**
 * Created by yyj on 2018/1/26.
 * 管理我的员工---评价员工界面
 */

public interface IStaffEvaluateView extends IView<String> {

    /**
     * 获取服务态度
     */
    String getAttitude();
    /**
     * 获取工作能力
     */
    String getAbility();
    /**
     * 获取响应速度
     */
    String getSpeed();
    /**
     * 获取评价内容
     */
    String getEvaluateContent();

    /**
     * 获取多张评价图片
     */
    ArrayList<String> getEvaluateImgUrls();

    /**
     * 获取图片路径是否成功的回调
     */
    void onGetImgUrlResult(boolean isSuccess, String result);
}
