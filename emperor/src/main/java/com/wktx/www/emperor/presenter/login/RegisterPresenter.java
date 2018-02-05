package com.wktx.www.emperor.presenter.login;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.login.SendCodeData;
import com.wktx.www.emperor.apiresult.login.login8register.RegisterData;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.Md5Util;
import com.wktx.www.emperor.view.login.IRegisterView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/15.
 * 注册界面
 * 注册&获取验证码
 */

public class RegisterPresenter extends ABasePresenter<IRegisterView> {

    public RegisterPresenter() {
    }

    //获取验证码
    public void onGetCode(){
        LogUtil.error("获取验证码","json===phone:"+getmMvpView().getPhoneStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_SENCODE)
                .params("phone",getmMvpView().getPhoneStr())
                .execute(new CallBackProxy<CustomApiResult<SendCodeData>, SendCodeData>
                        (new ProgressDialogCallBack<SendCodeData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取验证码","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onSendCodeResult(false, ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onSendCodeResult(false, e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(SendCodeData result) {
                                if (result != null) {
                                    LogUtil.error("获取验证码","result=="+result.toString());

                                    if (result.getCode()==0){//发送成功
                                        getmMvpView().onSendCodeResult(true,"验证码获取成功！");
                                    }else {//发送失败
                                        getmMvpView().onSendCodeResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onSendCodeResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //注册
    public void onRegister(){
        LogUtil.error("注册","json===phone:"+getmMvpView().getPhoneStr()
                +"\npassword:"+Md5Util.md5(getmMvpView().getPwd1Str())
                +"\ncode:"+getmMvpView().getCodeStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_LOGIN)
                .params("phone",getmMvpView().getPhoneStr())
                .params("password", Md5Util.md5(getmMvpView().getPwd1Str()))
                .params("ph_expires_in","5")
                .params("type","0")
                .params("code",getmMvpView().getCodeStr())
                .execute(new CallBackProxy<CustomApiResult<RegisterData>, RegisterData>
                        (new ProgressDialogCallBack<RegisterData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("注册","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(RegisterData result) {
                                if (result != null) {
                                    LogUtil.error("注册","result=="+result.toString());

                                    if (result.getCode()==0){//注册成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//注册失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
