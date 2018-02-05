package com.wktx.www.subjects.presenter.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.luck.picture.lib.tools.Constant;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.login.SendCodeData;
import com.wktx.www.subjects.apiresult.login.login8register.RegisterData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.wktx.www.subjects.utils.Md5Util;
import com.wktx.www.subjects.view.login.IRegisterView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

/**
 * Created by yyj on 2018/1/15.
 * 注册界面
 * 注册&获取验证码
 */

public class RegisterPresenter extends ABasePresenter<IRegisterView> {
    private Context mContext;

    public RegisterPresenter(Context mContext) {
        this.mContext = mContext;
    }

    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(mContext);
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };

    //获取验证码
    public void onGetCode(){
        LogUtil.error("获取验证码","json===phone:"+getmMvpView().getPhoneStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_SENCODE)
                .params("phone",getmMvpView().getPhoneStr())
                .execute(new CallBackProxy<CustomApiResult<SendCodeData>, SendCodeData>(new ProgressDialogCallBack<SendCodeData>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        LogUtil.error("获取验证码","e=="+e.getMessage());

                        getmMvpView().onSendCodeResult(false, ConstantUtil.TOAST_NONET);
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
                        }
                    }
                }) {
                });
    }

    //注册
    public void onRegister(){
        LogUtil.error("注册","json===phone:"+getmMvpView().getPhoneStr()
                +"\npassword:"+ Md5Util.md5(getmMvpView().getPwd1Str())
                +"\ncode:"+getmMvpView().getCodeStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_LOGIN)
                .params("phone",getmMvpView().getPhoneStr())
                .params("password", Md5Util.md5(getmMvpView().getPwd1Str()))
                .params("ph_expires_in","5")
                .params("type","0")
                .params("code",getmMvpView().getCodeStr())
                .execute(new CallBackProxy<CustomApiResult<RegisterData>, RegisterData>(new ProgressDialogCallBack<RegisterData>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        LogUtil.error("注册","e=="+e.getMessage());

                        getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                    }

                    @Override
                    public void onSuccess(RegisterData result) {
                        if (result != null) {
                            LogUtil.error("注册","result=="+result.toString());

                            if (result.getCode()==0){//注册成功
                                getmMvpView().onRequestSuccess(result);
                            }else {//注册失败
                                getmMvpView().onRequestFailure(result.getMsg());
                            }
                        }
                    }
                }) {
                });
    }
}
