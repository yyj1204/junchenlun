package com.wktx.www.subjects.presenter.mine;
import com.wktx.www.subjects.apiresult.mine.tradingrecord.TradingRecordData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 交易记录界面
 */

public class TradingRecordPresenter extends ABasePresenter<IView> {

    public TradingRecordPresenter() {
    }

    //获取交易记录
    public void getTradingRecord(int page,String type){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("type", type);
        httpParams.put("page",page+"");
        httpParams.put("pageSize", "10");

        LogUtil.error("获取交易记录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_TRADING_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<TradingRecordData>, TradingRecordData>
                        (new ProgressDialogCallBack<TradingRecordData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取交易记录","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(TradingRecordData result) {
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
