package com.wktx.www.subjects.ui.view.message;
import com.wktx.www.subjects.apiresult.message.InviteDetailsInfoData;
import com.wktx.www.subjects.ui.view.IView;

/**
 * Created by yyj on 2018/5/8.
 * 公司邀请-邀请详情
 */

public interface IInviteDetailsView extends IView<InviteDetailsInfoData> {

    /**
     * 接受邀请（拒绝邀请）是否成功的回调
     */
    void onInviteResult(boolean isSuccess, String result);
}
