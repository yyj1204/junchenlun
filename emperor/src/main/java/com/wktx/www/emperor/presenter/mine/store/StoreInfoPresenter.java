package com.wktx.www.emperor.presenter.mine.store;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.store.StoreListData;
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
 * 店铺信息界面
 */

public class StoreInfoPresenter extends ABasePresenter<IView> {

    public StoreInfoPresenter() {
    }

    //获取店铺信息列表
    public void onGetStoreList(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("page", page+"");
        httpParams.put("limit", "10");

        LogUtil.error("获取店铺信息列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_STORE_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StoreListData>, StoreListData>
                        (new SimpleCallBack<StoreListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取店铺信息列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(StoreListData result) {
                                if (result != null) {
                                    LogUtil.error("获取店铺信息列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取店铺信息列表成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取店铺信息列表失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取店铺信息列表失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
