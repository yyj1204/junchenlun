package com.wktx.www.emperor.presenter.mine;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.transactionrecord.TransactionRecordData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 交易记录界面
 */

public class TransactionRecordPresenter extends ABasePresenter<IView> {

    public TransactionRecordPresenter() {
    }

    //获取交易记录
    public void onGetTransactionRecord(int page,String type){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("type", type);
        httpParams.put("page",page+"");
        httpParams.put("limit", "10");

        LogUtil.error("获取交易记录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_TRANSACTION_RECORD)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<TransactionRecordData>, TransactionRecordData>
                        (new SimpleCallBack<TransactionRecordData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取交易记录","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(TransactionRecordData result) {
                                if (result != null) {
                                    LogUtil.error("获取交易记录","result=="+result.toString());

                                    if (result.getCode()==0){//获取交易记录 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取交易记录 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
