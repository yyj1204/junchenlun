package com.wktx.www.subjects.ui.view.mine.resume;
import com.wktx.www.subjects.apiresult.mine.resume.ResumeInfoData;
import com.wktx.www.subjects.ui.view.IView;


/**
 * Created by yyj on 2018/1/15.
 * 我的简历
 */

public interface IMyResumeView extends IView<ResumeInfoData> {

    /**
     * 获取修改找工作状态是否成功的回调
     */
    void onChangeHuntingResult(boolean isSuccess, String result);
}
