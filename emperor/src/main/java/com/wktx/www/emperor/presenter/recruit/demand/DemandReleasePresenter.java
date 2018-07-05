package com.wktx.www.emperor.presenter.recruit.demand;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.store.StoreListData;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandReleaseConditionData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.recruit.IDemandReleaseView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 需求发布界面
 */

public class DemandReleasePresenter extends ABasePresenter<IDemandReleaseView> {

    public DemandReleasePresenter() {
    }

    //获取店铺检索条件
    public void onGetStoreCondition(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_STORE_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StoreListData>, StoreListData>
                        (new ProgressDialogCallBack<StoreListData>(mProgressDialog){
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取店铺检索条件","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetStoreConditionFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetStoreConditionFailureResult(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(StoreListData result) {
                                if (result != null) {
                                    LogUtil.error("获取店铺列表检索条件","result=="+result.toString());

                                    if (result.getCode()==0){//获取店铺检索条件成功
                                        getmMvpView().onGetStoreConditionSuccessResult(result.getInfo());
                                    }else {//获取店铺检索条件失败
                                        getmMvpView().onGetStoreConditionFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetStoreConditionFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取发布需求需要的选择参数(平台、类目、需求模式)
    public void onGetDemandCondition(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_DEMAND_RELEASE_CONDITION)
                .execute(new CallBackProxy<CustomApiResult<DemandReleaseConditionData>, DemandReleaseConditionData>
                        (new SimpleCallBack<DemandReleaseConditionData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取需求发布选择参数","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(DemandReleaseConditionData result) {
                                if (result != null) {
                                    LogUtil.error("获取需求发布选择参数","result=="+result.toString());

                                    if (result.getCode()==0){//获取需求发布选择参数成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取需求发布选择参数失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //需求发布
    public void onDemandRelease(String platformId,String categoryId,String storeId,String patternId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("title", getmMvpView().getDemandTitle());
        httpParams.put("content", getmMvpView().getDemandContent());
        httpParams.put("bgap", platformId);
        httpParams.put("bgat", categoryId);
        httpParams.put("store_id", storeId);
        httpParams.put("design_pattern", patternId);
        httpParams.put("budget", getmMvpView().getDemandBudget());

        LogUtil.error("需求发布","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_DEMAND_RELEASE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("需求发布","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onDemandReleaseResult(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onDemandReleaseResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("需求发布","result=="+result.toString());

                                    if (result.getCode()==0){//需求发布成功
                                        getmMvpView().onDemandReleaseResult(true,"您的需求已发布成功！");
                                    }else {//需求发布失败
                                        getmMvpView().onDemandReleaseResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onDemandReleaseResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
