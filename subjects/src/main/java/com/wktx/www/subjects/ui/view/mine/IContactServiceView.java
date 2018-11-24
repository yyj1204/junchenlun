package com.wktx.www.subjects.ui.view.mine;

import com.wktx.www.subjects.apiresult.mine.service.ServiceInfoData;
import com.wktx.www.subjects.ui.view.IView;

/**
 * Created by yyj on 2018/1/26.
 * 联系客服界面
 */

public interface IContactServiceView extends IView<ServiceInfoData> {

    /**
     * 获取留言内容
     */
    String getMessageContent();

    /**
     * 获取联系方式
     */
    String getContactWay();

    /**
     * 获取留言是否成功的回调
     */
    void onMessageResult(boolean isSuccess, String msg);

}
