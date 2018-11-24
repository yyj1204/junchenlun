package com.wktx.www.subjects.ui.view.manage;
import com.wktx.www.subjects.apiresult.manage.leave.LeaveListInfoData;
import com.wktx.www.subjects.ui.view.IView;
import java.util.List;


/**
 * Created by yyj on 2018/1/15.
 * 请假
 */

public interface ILeaveView extends IView<List<LeaveListInfoData>> {

    /**
     * 获取请假原因
     */
    String getReason();

    /**
     * 获取请假开始时间
     */
    String getBeginTime();

    /**
     * 获取请假结束时间
     */
    String getEndTime();

    /**
     * 获取请假图片
     */
    String getLeaveImgUrl();

    /**
     * 获取图片路径是否成功的回调
     */
    void onGetImgUrlResult(boolean isSuccess, String result);

    /**
     * 获取请假是否成功的回调
     */
    void onLeaveResult(boolean isSuccess, String msg);
}
