package com.wktx.www.emperor.presenter.recruit.hire;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.pay.AliPayData;
import com.wktx.www.emperor.apiresult.mine.pay.WeChatPayData;
import com.wktx.www.emperor.apiresult.recruit.hire.TrusteeshipSalaryData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.recruit.hire.ITrusteeshipSalaryView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 托管工资界面
 * pay_method支付方式(wxpay:微信支付,alipay:支付宝支付,usermoneypay:余额支付)
 */

public class TrusteeshipSalaryPresenter extends ABasePresenter<ITrusteeshipSalaryView> {

    public TrusteeshipSalaryPresenter() {
    }

    //取消雇佣订单
    public void onCancelOrders(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", getmMvpView().getHireId());

        LogUtil.error("取消雇佣订单","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HIRE_CANCELORDERS)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("取消雇佣订单","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onCancelOrdersResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onCancelOrdersResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("取消雇佣订单","result=="+result.toString());

                                    if (result.getCode()==0){//取消雇佣订单成功
                                        getmMvpView().onCancelOrdersResult(true,result.getMsg());
                                    }else {//取消雇佣订单失败
                                        getmMvpView().onCancelOrdersResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onCancelOrdersResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //获取支付订单信息
    public void getHireOrdersInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", getmMvpView().getHireId());

        LogUtil.error("获取支付订单信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HIRE_PAYINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<TrusteeshipSalaryData>, TrusteeshipSalaryData>
                        (new ProgressDialogCallBack<TrusteeshipSalaryData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取支付订单信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(TrusteeshipSalaryData result) {
                                if (result != null) {
                                    LogUtil.error("获取支付订单信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取支付订单信息成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取支付订单信息失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //获取支付宝支付订单号金额
    public void getAliPayInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", getmMvpView().getHireId());
        httpParams.put("pay_method", "alipay");//支付方式

        LogUtil.error("获取支付宝支付订单号金额","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HIRE_PAYINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<AliPayData>, AliPayData>
                        (new ProgressDialogCallBack<AliPayData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取支付宝支付订单号金额","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onAlipayResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onAlipayResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(AliPayData result) {
                                if (result != null) {
                                    LogUtil.error("获取支付宝支付订单号金额","result=="+result.toString());

                                    if (result.getCode()==0){//获取支付宝支付订单号金额成功
                                        getmMvpView().onAlipayResult(true,result.getInfo());
                                    }else {//获取支付宝支付订单号金额失败
                                        getmMvpView().onAlipayResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onAlipayResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //获取微信支付订单号金额
    public void getWeChatPayInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", getmMvpView().getHireId());
        httpParams.put("pay_method", "wxpay");//支付方式

        LogUtil.error("获取微信支付订单号金额","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HIRE_PAYINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<WeChatPayData>, WeChatPayData>
                        (new ProgressDialogCallBack<WeChatPayData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取微信支付订单号金额","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onWechatFailureResult(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onWechatFailureResult(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(WeChatPayData result) {
                                if (result != null) {
                                    LogUtil.error("获取微信支付订单号金额","result=="+result.toString());

                                    if (result.getCode()==0){//获取微信支付订单号金额成功
                                        getmMvpView().onWechatSuccessResult(result.getInfo());
                                    }else {//获取微信支付订单号金额失败
                                        getmMvpView().onWechatFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onWechatFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //余额支付
    public void onBalancePay(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", getmMvpView().getHireId());
        httpParams.put("pay_method", "usermoneypay");//支付方式
        httpParams.put("pay_password", getmMvpView().getPayPwd());//余额支付密码

        LogUtil.error("余额支付","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HIRE_PAYINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("余额支付","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onBanlancePayResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onBanlancePayResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("余额支付","result=="+result.toString());

                                    if (result.getCode()==0){//余额支付成功
                                        getmMvpView().onBanlancePayResult(true,result.getMsg());
                                    }else {//余额支付失败
                                        getmMvpView().onBanlancePayResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onBanlancePayResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
