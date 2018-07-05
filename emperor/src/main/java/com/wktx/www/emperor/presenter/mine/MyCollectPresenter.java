package com.wktx.www.emperor.presenter.mine;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.browsingrecord.BrowsingRecordData;
import com.wktx.www.emperor.apiresult.mine.condition.ConditionData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.mine.IMyCollectView;
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

    //获取列表检索条件
    public void onGetCondition(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_LIST_CONDITION)
                .execute(new CallBackProxy<CustomApiResult<ConditionData>, ConditionData>
                        (new ProgressDialogCallBack<ConditionData>(mProgressDialog){
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取列表检索条件","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetConditionFailureResult(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(ConditionData result) {
                                if (result != null) {
                                    LogUtil.error("获取列表检索条件","result=="+result.toString());

                                    if (result.getCode()==0){//获取列表检索条件成功
                                        getmMvpView().onGetConditionSuccessResult(result.getInfo());
                                    }else {//获取列表检索条件失败
                                        getmMvpView().onGetConditionFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取收藏列表
    public void onGetCollectList(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("tow", getmMvpView().getJobType());
        httpParams.put("page",page+"");
        httpParams.put("limit", "10");

        LogUtil.error("获取收藏列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_COLLECT_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<BrowsingRecordData>, BrowsingRecordData>
                        (new SimpleCallBack<BrowsingRecordData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取收藏列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(BrowsingRecordData result) {
                                if (result != null) {
                                    LogUtil.error("获取收藏列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取收藏列表成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取收藏列表失败(无数据)
                                        getmMvpView().onRequestFailure("");
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
    public void onCancelCollectResume(String resumeId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("rid", resumeId);

        LogUtil.error("取消收藏简历","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_COLLECT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("取消收藏简历","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onCancelCollectResumeResult(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onCancelCollectResumeResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("取消收藏简历","result=="+result.toString());

                                    if (result.getCode()==0){//取消收藏简历 成功
                                        getmMvpView().onCancelCollectResumeResult(true,result.getMsg());
                                    }else {//取消收藏简历 失败
                                        getmMvpView().onCancelCollectResumeResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onCancelCollectResumeResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
