package com.wktx.www.subjects.presenter.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.login.ForgetPwdData;
import com.wktx.www.subjects.apiresult.login.SendCodeData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.wktx.www.subjects.utils.Md5Util;
import com.wktx.www.subjects.view.login.IForgetPwdView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

/**
 * Created by yyj on 2018/1/15.
 * 忘记密码界面
 * 修改密码&获取验证码
 */

public class ForgetPwdPresenter extends ABasePresenter<IForgetPwdView> {
    private Context mContext;

    public ForgetPwdPresenter(Context mContext) {
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

    //重置密码
    public void onResetPwd(){
        LogUtil.error("忘记密码","json===phone:"+getmMvpView().getPhoneStr()
                +"\nnew_password:"+ Md5Util.md5(getmMvpView().getPwd1Str())
                +"\ncode:"+getmMvpView().getCodeStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_FORGET_PWD)
                .params("phone",getmMvpView().getPhoneStr())
                .params("new_password", Md5Util.md5(getmMvpView().getPwd1Str()))
                .params("code",getmMvpView().getCodeStr())
                .execute(new CallBackProxy<CustomApiResult<ForgetPwdData>, ForgetPwdData>(new ProgressDialogCallBack<ForgetPwdData>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        LogUtil.error("忘记密码","e=="+e.getMessage());

                        getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                    }

                    @Override
                    public void onSuccess(ForgetPwdData result) {
                        if (result != null) {
                            LogUtil.error("忘记密码","result=="+result.toString());

                            if (result.getCode()==0){//密码重置成功
                                getmMvpView().onRequestSuccess(result);
                            }else {//密码重置失败
                                getmMvpView().onRequestFailure(result.getMsg());
                            }
                        }
                    }
                }) {
                });
    }
}
