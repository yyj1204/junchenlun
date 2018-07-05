package com.wktx.www.emperor.presenter.login;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.login.login8register.RegisterData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.Md5Util;
import com.wktx.www.emperor.ui.view.login.ILoginView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 登录界面
 */

public class LoginPresenter extends ABasePresenter<ILoginView> {

    public LoginPresenter() {
    }

    //登录
    public void onLogin(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("phone",getmMvpView().getPhoneStr());
        httpParams.put("password", Md5Util.md5(getmMvpView().getPwdStr()));
        httpParams.put("ph_expires_in","10");
        httpParams.put("type","1");
        LogUtil.error("登录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_LOGIN)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<RegisterData>, RegisterData>
                        (new ProgressDialogCallBack<RegisterData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("登录","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(RegisterData result) {
                                if (result != null) {
                                    LogUtil.error("登录","result=="+result.toString());

                                    if (result.getCode()==0){//登录成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//登录失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
