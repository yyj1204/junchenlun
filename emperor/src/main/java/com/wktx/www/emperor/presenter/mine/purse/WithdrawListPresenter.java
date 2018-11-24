package com.wktx.www.emperor.presenter.mine.purse;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.withdraw.WithdrawListData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.ui.view.IView;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 钱包提现记录界面
 */

public class WithdrawListPresenter extends ABasePresenter<IView> {

    public WithdrawListPresenter() {
    }


    //获取提现记录
    public void onGetWithdrawList(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("page",page+"");
        httpParams.put("limit", "10");

        LogUtil.error("获取提现记录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WITHDRAW_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<WithdrawListData>, WithdrawListData>
                        (new ProgressDialogCallBack<WithdrawListData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取提现记录","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(WithdrawListData result) {
                                if (result != null) {
                                    LogUtil.error("获取提现记录","result=="+result.toString());

                                    if (result.getCode()==0){//获取提现记录 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取提现记录 失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取提现记录 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
