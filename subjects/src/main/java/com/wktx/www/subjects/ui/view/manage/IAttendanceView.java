package com.wktx.www.subjects.ui.view.manage;
import com.wktx.www.subjects.apiresult.manage.attendance.AttendanceRecordInfoData;
import com.wktx.www.subjects.ui.view.IView;

/**
 * Created by yyj on 2018/5/8.
 * 管理工作---签到
 */

public interface IAttendanceView extends IView<AttendanceRecordInfoData> {

    /**
     * 签到是否成功的回调
     */
    void onSingInResult(boolean isSuccess, String result);
}
