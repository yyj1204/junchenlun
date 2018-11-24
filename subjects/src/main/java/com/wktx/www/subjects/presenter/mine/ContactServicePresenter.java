package com.wktx.www.subjects.presenter.mine;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.mine.service.ServiceData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.IContactServiceView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 联系客服界面
 */

public class ContactServicePresenter extends ABasePresenter<IContactServiceView> {

    public ContactServicePresenter() {
    }

    //获取客服电话、客服qq
    public void onGetServiceInfo(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .baseUrl(ApiURL.GLOBAL_URL_JZ)//君主API
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CONTACT_SERVICE)
                .execute(new CallBackProxy<CustomApiResult<ServiceData>, ServiceData>
                        (new ProgressDialogCallBack<ServiceData>(mProgressDialog){
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取客服电话、客服qq","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(ServiceData result) {
                                if (result != null) {
                                    LogUtil.error("获取客服电话、客服qq","result=="+result.toString());

                                    if (result.getCode()==0){//获取客服电话、客服qq成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取客服电话、客服qq失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //我要留言
    public void onMessage(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("content", getmMvpView().getMessageContent());
        httpParams.put("contact", getmMvpView().getContactWay());

        LogUtil.error("我要留言","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CONTACT_MESSAGE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("我要留言","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onMessageResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onMessageResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("我要留言","result=="+result.toString());

                                    if (result.getCode()==0){//我要留言 成功
                                        getmMvpView().onMessageResult(true,"您的留言已提交！");
                                    }else {//我要留言 失败
                                        getmMvpView().onMessageResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onMessageResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
