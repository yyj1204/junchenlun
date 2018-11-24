package com.wktx.www.emperor.ui.view.main;

import com.wktx.www.emperor.apiresult.main.message.MessageListInfoData;
import com.wktx.www.emperor.ui.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/15.
 * 系统消息界面
 */

public interface IMessageView extends IView<List<MessageListInfoData>> {

    /**
     * 获取开始时间
     */
    String getBeginTime();

    /**
     * 获取结束时间
     */
    String getEndTime();

}
