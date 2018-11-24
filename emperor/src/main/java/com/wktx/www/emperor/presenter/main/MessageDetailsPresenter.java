package com.wktx.www.emperor.presenter.main;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.main.message.MessageDetailsData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 系统消息详情
 */

public class MessageDetailsPresenter extends ABasePresenter<IView> {

    public MessageDetailsPresenter() {
    }

    //获取消息通知详情
    public void onMessageInfo(String messageId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("rec_id", messageId);

        LogUtil.error("获取消息通知详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MESSAGE_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<MessageDetailsData>, MessageDetailsData>
                        (new ProgressDialogCallBack<MessageDetailsData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取消息通知详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(MessageDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取消息通知详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取消息通知详情成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取消息通知详情失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
