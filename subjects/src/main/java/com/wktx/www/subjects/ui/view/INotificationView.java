package com.wktx.www.subjects.ui.view;

import com.wktx.www.subjects.apiresult.main.notification.MessageListInfoData;

import java.util.List;

/**
 * Created by yyj on 2018/1/15.
 * 系统消息界面
 */

public interface INotificationView extends IView<List<MessageListInfoData>> {

    /**
     * 获取开始时间
     */
    String getBeginTime();

    /**
     * 获取结束时间
     */
    String getEndTime();

}
