package com.wktx.www.emperor.presenter.mine.store;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.store.StoreConditionData;
import com.wktx.www.emperor.apiresult.mine.store.StoreData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.mine.IStoreInfoEditView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 编辑（添加）店铺信息界面
 */

public class StoreInfoEditPresenter extends ABasePresenter<IStoreInfoEditView> {

    public StoreInfoEditPresenter() {
    }

    //获取店铺信息
    public void onGetStoreInfo(String storeId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", storeId);

        LogUtil.error("获取店铺信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_STORE_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StoreData>, StoreData>
                        (new ProgressDialogCallBack<StoreData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取店铺信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(StoreData result) {
                                if (result != null) {
                                    LogUtil.error("获取店铺信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取店铺信息成功
                                        getmMvpView().onRequestSuccess(result.getInfo());;
                                    }else {//获取店铺信息失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //获取编辑（添加）店铺需要的选择参数(平台、类目)
    public void onGetStoreCondition(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_STORE_EDIT_CONDITION)
                .execute(new CallBackProxy<CustomApiResult<StoreConditionData>, StoreConditionData>
                        (new SimpleCallBack<StoreConditionData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取编辑（添加）店铺选择参数","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onConditionFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(StoreConditionData result) {
                                if (result != null) {
                                    LogUtil.error("获取编辑（添加）店铺选择参数","result=="+result.toString());

                                    if (result.getCode()==0){//获取编辑（添加）店铺选择参数成功
                                        getmMvpView().onConditionSuccessResult(result.getInfo());
                                    }else {//获取编辑（添加）店铺选择参数失败
                                        getmMvpView().onConditionFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onConditionFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //编辑（添加）店铺
    public void onStoreEdit(final String storeId, String platformId, String categoryId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", storeId);
        if (!getmMvpView().getLogoPic().equals("")){
            httpParams.put("logo", getmMvpView().getLogoPic());
        }
        httpParams.put("bgap", platformId);
        httpParams.put("bgat", categoryId);
        httpParams.put("name", getmMvpView().getStoreName());
        httpParams.put("url", getmMvpView().getStoreUrl());

        LogUtil.error("编辑（添加）店铺","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_STORE_EDIT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("编辑（添加）店铺","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onStoreInfoEditResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onStoreInfoEditResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("编辑（添加）店铺","result=="+result.toString());

                                    if (result.getCode()==0){//编辑（添加）店铺成功
                                        if (storeId.equals("0")){
                                            getmMvpView().onStoreInfoEditResult(true,"您的店铺已添加成功！");
                                        }else {
                                            getmMvpView().onStoreInfoEditResult(true,"您的店铺已编辑成功！");
                                        }
                                    }else {//编辑（添加）店铺失败
                                        getmMvpView().onStoreInfoEditResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onStoreInfoEditResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
