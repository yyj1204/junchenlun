package com.wktx.www.subjects.presenter.mine;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.mine.UserData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.wktx.www.subjects.view.mine.IMineView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

/**
 * Created by yyj on 2018/1/15.
 * 我的账户界面
 */

public class MinePresenter extends ABasePresenter<IMineView> {
    private Context mContext;

    public MinePresenter(Context mContext) {
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

    //获取账户信息
    public void onGetAccountInfo(){
        LogUtil.error("获取账户信息","json===user_id:"+getmMvpView().getUserInfo().getUser_id()
                +"\ntoken:"+getmMvpView().getUserInfo().getToken());
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_USER_INFO)
                .params("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()))
                .params("token", getmMvpView().getUserInfo().getToken())
                .execute(new CallBackProxy<CustomApiResult<UserData>, UserData>(new ProgressDialogCallBack<UserData>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        LogUtil.error("获取账户信息","e=="+e.getMessage());

                        getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                    }

                    @Override
                    public void onSuccess(UserData result) {
                        if (result != null) {
                            LogUtil.error("获取账户信息","result=="+result.toString());

                            if (result.getCode()==0){//获取用户信息成功
                                getmMvpView().onRequestSuccess(result);
                            }else {//获取用户信息失败
                                getmMvpView().onRequestFailure(result.getMsg());
                            }
                        }
                    }
                }) {
                });
    }

    //退出登录
    public void onLogout(){
        LogUtil.error("退出登录","json===user_id:"+getmMvpView().getUserInfo().getUser_id()
                +"\ntoken:"+getmMvpView().getUserInfo().getToken());
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_LOGOUT)
                .params("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()))
                .params("token", getmMvpView().getUserInfo().getToken())
                .execute(new CallBackProxy<CustomApiResult<String>, String>(new ProgressDialogCallBack<String>(mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        LogUtil.error("退出登录","e=="+e.getMessage());

                        getmMvpView().onLogout(false,ConstantUtil.TOAST_NONET);
                    }

                    @Override
                    public void onSuccess(String result) {
                        LogUtil.error("退出登录","result=="+result.toString());

                        getmMvpView().onLogout(true,"已退出登录！");
                    }
                }) {
                });
    }
}
