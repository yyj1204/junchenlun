package com.wktx.www.emperor.presenter.mine.authent;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.authent.AuthentAlipayData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.ui.view.mine.authent.IAuthentAlipayView;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 支付宝认证
 */

public class AuthentAlipayPresenter extends ABasePresenter<IAuthentAlipayView> {

    public AuthentAlipayPresenter() {
    }

    //支付宝认证
    public void onCertification(String authentId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("alipay", getmMvpView().getAlipayNum());
        httpParams.put("name", getmMvpView().getAlipayName());
        //如果认证id不为空，即为重新认证
        if (!authentId.equals("0")){
            httpParams.put("id", authentId);
        }

        LogUtil.error("支付宝认证","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_ALIPAY_AUTHENT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("支付宝认证","e=="+e.getMessage());

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
                                    LogUtil.error("支付宝认证）","result=="+result.toString());

                                    if (result.getCode()==0){//支付宝认证 成功
                                        getmMvpView().onRequestSuccess(result.getMsg());
                                    }else {//支付宝认证 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取支付宝认证详情
    public void getCertificationInfo(String authentId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", authentId);

        LogUtil.error("获取支付宝认证详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_ALIPAY_AUTHENT_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<AuthentAlipayData>, AuthentAlipayData>
                        (new ProgressDialogCallBack<AuthentAlipayData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取支付宝认证详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetAuthentInfoFailureResult(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onGetAuthentInfoFailureResult(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(AuthentAlipayData result) {
                                if (result != null) {
                                    LogUtil.error("获取支付宝认证详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取支付宝认证详情 成功
                                        getmMvpView().onGetAuthentInfoSuccessResult(result.getInfo());
                                    }else {//获取支付宝认证详情 失败
                                        getmMvpView().onGetAuthentInfoFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetAuthentInfoFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
