package com.wktx.www.emperor.presenter.mine.purse;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.ui.view.mine.purse.IPurseWithdrawView;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.utils.Md5Util;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 钱包提现界面
 */

public class PurseWithdrawPresenter extends ABasePresenter<IPurseWithdrawView> {

    public PurseWithdrawPresenter() {
    }

    //获取验证码
    public void onGetCode(){
        LogUtil.error("获取验证码","json===phone:"+getmMvpView().getPhoneStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_SENCODE)
                .params("phone",getmMvpView().getPhoneStr())
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
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
                            public void onSuccess(CommonSimpleData result) {
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


    //钱包提现
    public void onWithdraw(boolean isPerson){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("code", getmMvpView().getCodeStr());
        httpParams.put("paypwd", Md5Util.md5(getmMvpView().getPayPwdStr()));
        httpParams.put("alipay", getmMvpView().getAlipayNumStr());
        httpParams.put("money", getmMvpView().getWithdrawalMoney());
        //如果是个人认证，即传身份证，店铺认证传信用代码
        if (isPerson){
            httpParams.put("id_card_no", getmMvpView().getIdNumStr());
        }else {
            httpParams.put("credit_code", getmMvpView().getIdNumStr());
        }

        LogUtil.error("钱包提现","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_PURSE_WITHDRAW)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("钱包提现","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("钱包提现","result=="+result.toString());

                                    if (result.getCode()==0){//钱包提现 成功
                                        getmMvpView().onRequestSuccess(result.getMsg());
                                    }else {//钱包提现 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
