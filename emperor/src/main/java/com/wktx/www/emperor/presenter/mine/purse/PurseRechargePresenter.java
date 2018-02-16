package com.wktx.www.emperor.presenter.mine.purse;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.pay.AliPayData;
import com.wktx.www.emperor.apiresult.mine.pay.WeChatPayData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.mine.purse.IPurseRechargeView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/15.
 * 钱包充值界面
 */

public class PurseRechargePresenter extends ABasePresenter<IPurseRechargeView> {

    public PurseRechargePresenter() {
    }

    //获取微信充值订单号金额
    public void getWeChatPayInfo(){
        LogUtil.error("获取微信充值订单号金额","json===user_id:"+getmMvpView().getUserInfo().getUser_id()
                +"\ntoken:"+getmMvpView().getUserInfo().getToken() +"\namount:"+getmMvpView().getMoneyStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_PURSE_RECHARGE)
                .params("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()))
                .params("token", getmMvpView().getUserInfo().getToken())
                .params("amount", getmMvpView().getMoneyStr())
                .params("pay_method", "wxpay")//支付方式
                .execute(new CallBackProxy<CustomApiResult<WeChatPayData>, WeChatPayData>
                        (new ProgressDialogCallBack<WeChatPayData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取微信充值订单号金额","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(WeChatPayData result) {
                                if (result != null) {
                                    LogUtil.error("获取微信充值订单号金额","result=="+result.toString());

                                    if (result.getCode()==0){//获取微信充值订单号金额成功
                                        getmMvpView().onRequestSuccess(result.getInfo().getPay_info());
                                    }else {//获取微信充值订单号金额失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //获取支付宝充值订单号金额
    public void getAliPayInfo(){
        LogUtil.error("获取支付宝充值订单号金额","json===user_id:"+getmMvpView().getUserInfo().getUser_id()
                +"\ntoken:"+getmMvpView().getUserInfo().getToken()+"\namount:"+getmMvpView().getMoneyStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_PURSE_RECHARGE)
                .params("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()))
                .params("token", getmMvpView().getUserInfo().getToken())
                .params("amount", getmMvpView().getMoneyStr())
                .params("pay_method", "alipay")//支付方式
                .execute(new CallBackProxy<CustomApiResult<AliPayData>, AliPayData>
                        (new ProgressDialogCallBack<AliPayData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取支付宝充值订单号金额","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onAlipaySuccessResult(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onAlipaySuccessResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(AliPayData result) {
                                if (result != null) {
                                    LogUtil.error("获取支付宝充值订单号金额","result=="+result.toString());

                                    if (result.getCode()==0){//获取支付宝充值订单号金额成功
                                        getmMvpView().onAlipaySuccessResult(true,result.getInfo().getPay_info());
                                    }else {//获取支付宝充值订单号金额失败
                                        getmMvpView().onAlipaySuccessResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onAlipaySuccessResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
