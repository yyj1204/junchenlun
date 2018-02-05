package com.wktx.www.subjects.presenter.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.login.login8register.RegisterData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.wktx.www.subjects.utils.Md5Util;
import com.wktx.www.subjects.view.login.ILoginView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

/**
 * Created by yyj on 2018/1/15.
 * 登录界面
 */

public class LoginPresenter extends ABasePresenter<ILoginView> {
    private Context mContext;

    public LoginPresenter(Context mContext) {
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

    //登录
    public void onLogin(){
        LogUtil.error("登录","json===phone:"+getmMvpView().getPhoneStr()
                +"\npassword:"+ Md5Util.md5(getmMvpView().getPwdStr()));
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_LOGIN)
                .params("phone",getmMvpView().getPhoneStr())
                .params("password", Md5Util.md5(getmMvpView().getPwdStr()))
                .params("ph_expires_in","10")
                .params("type","1")
                .execute(new CallBackProxy<CustomApiResult<RegisterData>, RegisterData>(new ProgressDialogCallBack<RegisterData>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        LogUtil.error("登录","e=="+e.getMessage());

                        getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                    }

                    @Override
                    public void onSuccess(RegisterData result) {
                        if (result != null) {
                            LogUtil.error("登录","result=="+result.toString());

                            if (result.getCode()==0){//登录成功
                                getmMvpView().onRequestSuccess(result);
                            }else {//登录失败
                                getmMvpView().onRequestFailure(result.getMsg());
                            }
                        }
                    }
                }) {
                });
    }
}
