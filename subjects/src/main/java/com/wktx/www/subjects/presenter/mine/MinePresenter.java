package com.wktx.www.subjects.presenter.mine;

import com.wktx.www.subjects.apiresult.mine.authent.AuthentPersonalData;
import com.wktx.www.subjects.apiresult.mine.center.CenterData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.IMineView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 我的账户界面
 */

public class MinePresenter extends ABasePresenter<IMineView> {

    public MinePresenter() {
    }

    //获取个人中心信息
    public void getInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());

        LogUtil.error("获取个人中心信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CENTER)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CenterData>, CenterData>
                        (new SimpleCallBack<CenterData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取个人中心信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CenterData result) {
                                if (result != null) {
                                    LogUtil.error("获取个人中心信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取个人中心信息 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取个人中心信息 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //退出登录
    public void onLogout(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());

        LogUtil.error("退出登录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_LOGOUT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<String>, String>
                        (new ProgressDialogCallBack<String>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("退出登录","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onLogout(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onLogout(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(String result) {
                                LogUtil.error("退出登录","result=="+result.toString());

                                getmMvpView().onLogout(true,"已退出登录！");
                            }
                        }) {});
    }
}
