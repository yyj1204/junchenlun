package com.wktx.www.subjects.presenter.main;
import com.wktx.www.subjects.apiresult.main.notification.MessageListData;
import com.wktx.www.subjects.apiresult.main.notification.MessageDetailsData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.INotificationView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 系统消息通知列表
 */

public class NotificationPresenter extends ABasePresenter<INotificationView> {

    public NotificationPresenter() {
    }

    //获取消息通知列表
    public void onMessageList(String type){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("type", type);//0:公告 2:提醒
        httpParams.put("start_time", DateUtil.getCustomType2Timestamp(getmMvpView().getBeginTime(),"yyyy-MM-dd"));
        httpParams.put("end_time", DateUtil.getCustomType2Timestamp(getmMvpView().getEndTime(),"yyyy-MM-dd"));

        LogUtil.error("获取消息通知列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MESSAGE_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<MessageListData>, MessageListData>
                        (new SimpleCallBack<MessageListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取消息通知列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(MessageListData result) {
                                if (result != null) {
                                    LogUtil.error("获取消息通知列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取消息通知列表 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取消息通知列表 失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取消息通知列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
