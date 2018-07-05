package com.wktx.www.emperor.presenter.recruit.hire;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.hire.CouponBalanceData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.recruit.hire.IOrdersInfoView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/2/2.
 *美工简历---简历---雇佣---确认订单信息
 */

public class OrdersInfoPresenter extends ABasePresenter<IOrdersInfoView> {

    public OrdersInfoPresenter() {
    }

    //取消雇佣订单
    public void onCancelOrders(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", hireId);

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


    //使用优惠券或者余额
    public void onUseCounpon(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("coupon_id", getmMvpView().getCouponId());
        httpParams.put("user_money", getmMvpView().getUsableBalance());

        LogUtil.error("使用优惠券或者余额","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HIRE_USEDISCOUNT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CouponBalanceData>, CouponBalanceData>
                        (new ProgressDialogCallBack<CouponBalanceData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("使用优惠券或者余额","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CouponBalanceData result) {
                                if (result != null) {
                                    LogUtil.error("使用优惠券或者余额","result=="+result.toString());

                                    if (result.getCode()==0){//使用优惠券或者余额成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//使用优惠券或者余额失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
