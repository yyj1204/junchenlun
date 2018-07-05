package com.wktx.www.subjects.ui.view.mine.resume;
import com.wktx.www.subjects.ui.view.IView;


/**
 * Created by yyj on 2018/1/15.
 * 我的简历---上传个性简历
 */

public interface IResumeUploadView extends IView<String> {

    /**
     * 获取修改个性简历是否成功的回调
     */
    void onChangeResumeImgResult(boolean isSuccess, String result);
}
