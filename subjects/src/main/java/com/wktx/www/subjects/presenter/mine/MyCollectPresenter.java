package com.wktx.www.subjects.presenter.mine;

import com.wktx.www.subjects.apiresult.main.condition.ConditionData;
import com.wktx.www.subjects.apiresult.main.demand.DemandListData;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.IMyCollectView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 我的收藏界面
 */

public class MyCollectPresenter extends ABasePresenter<IMyCollectView> {

    public MyCollectPresenter() {
    }

    //获取参数信息（平台、风格、类目、职位、工作经验）
    public void getConditionInfo(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_POSITION_CONDITION)
                .execute(new CallBackProxy<CustomApiResult<ConditionData>, ConditionData>
                        (new SimpleCallBack<ConditionData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取参数信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetConditionFailureResult(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(ConditionData result) {
                                if (result != null) {
                                    LogUtil.error("获取参数信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取参数信息 成功
                                        getmMvpView().onGetConditionSuccessResult(result.getInfo());
                                    }else {//获取参数信息 失败
                                        getmMvpView().onGetConditionFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取收藏列表
    public void getInfo(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("tow", getmMvpView().getJobType());
        httpParams.put("page",page+"");
        httpParams.put("pageSize", "10");

        LogUtil.error("获取收藏列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_COLLECT_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<DemandListData>, DemandListData>
                        (new ProgressDialogCallBack<DemandListData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取收藏列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(DemandListData result) {
                                if (result != null) {
                                    LogUtil.error("获取收藏列表","result=="+result.toString());

                                    if (result.getCode()==0){//请求成功
                                        if (result.getInfo().size()!=0){//获取收藏列表 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                        }else {//获取收藏列表 失败(无数据)
                                            getmMvpView().onRequestFailure("");
                                        }
                                    }else {//获取收藏列表失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //取消收藏简历
    public void changeCollectState(String demandId,String isCollect){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("demand_id", demandId);
        httpParams.put("collect", isCollect);//0:取消 1:收藏

        LogUtil.error("取消收藏简历","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_POSITION_COLLECT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("取消收藏简历","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onCancelCollectResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onCancelCollectResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("取消收藏简历","result=="+result.toString());

                                    if (result.getCode()==0){//取消收藏简历 成功
                                        getmMvpView().onCancelCollectResult(true,result.getMsg());
                                    }else {//取消收藏简历 失败
                                        getmMvpView().onCancelCollectResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onCancelCollectResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
