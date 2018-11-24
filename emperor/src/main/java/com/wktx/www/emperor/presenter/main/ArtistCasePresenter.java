package com.wktx.www.emperor.presenter.main;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.main.artistcase.WorksConditionData;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksListData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.main.IArtistCaseView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 美工案例界面
 */

public class ArtistCasePresenter extends ABasePresenter<IArtistCaseView> {

    public ArtistCasePresenter() {
    }

    //获取案例检索条件
    public void onGetCondition(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_ARTISTCASE_CONDITION)
                .execute(new CallBackProxy<CustomApiResult<WorksConditionData>, WorksConditionData>
                        (new ProgressDialogCallBack<WorksConditionData>(mProgressDialog){
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取案例检索条件","e=="+e.getMessage());

                                if ("无法解析该域名".equals(e.getMessage())){
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetConditionFailureResult(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(WorksConditionData result) {
                                if (result != null) {
                                    LogUtil.error("获取案例检索条件","result=="+result.toString());

                                    if (result.getCode()==0){//获取案例检索条件 成功
                                        getmMvpView().onGetConditionSuccessResult(result.getInfo());
                                    }else {//获取案例检索条件 失败
                                        getmMvpView().onGetConditionFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取案例列表
    public void onGetWorksList(int page){
        HttpParams httpParams = new HttpParams();
        if (getmMvpView().getUserInfo()!=null){
            httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
            httpParams.put("token", getmMvpView().getUserInfo().getToken());
        }
        httpParams.put("dp", getmMvpView().getDesignTypeId());
        httpParams.put("bgat", getmMvpView().getCategoryId());
        httpParams.put("browse", getmMvpView().getBrowseId());
        httpParams.put("liked", getmMvpView().getLikedId());
        httpParams.put("page",page+"");
        httpParams.put("limit", "10");

        LogUtil.error("获取案例列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_ARTISTCASE_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<WorksListData>, WorksListData>
                        (new SimpleCallBack<WorksListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取案例列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(WorksListData result) {
                                if (result != null) {
                                    LogUtil.error("获取案例列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取案例列表 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取案例列表 失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取案例列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
