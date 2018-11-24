package com.wktx.www.subjects.presenter.message;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.message.InviteListData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 公司邀请片段
 */

public class InvitePresenter extends ABasePresenter<IView> {

    public InvitePresenter() {
    }

    //获取公司邀请列表
    public void getInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        LogUtil.error("获取公司邀请列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_INVITE_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<InviteListData>, InviteListData>
                        (new SimpleCallBack<InviteListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取公司邀请列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(InviteListData result) {
                                if (result != null) {
                                    LogUtil.error("获取公司邀请列表","result=="+result.toString());

                                    if (result.getCode()==0){//请求成功
                                        if (result.getInfo().size()!=0){//获取公司邀请列表 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                        }else {//获取公司邀请列表 失败（没数据）
                                            getmMvpView().onRequestFailure("");
                                        }
                                    }else {//获取公司邀请列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
