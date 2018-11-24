package com.wktx.www.emperor.presenter.mine;

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
 * 面试记录界面
 */

public class InterviewRecordPresenter extends ABasePresenter<IMyCollectView> {

    public InterviewRecordPresenter() {
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

    //获取面试记录
    public void onGetInterviewRecord(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("tow", getmMvpView().getJobType());
        httpParams.put("page",page+"");
        httpParams.put("limit", "10");

        LogUtil.error("获取面试记录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_INTERVIEW_RECORD)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<BrowsingRecordData>, BrowsingRecordData>
                        (new SimpleCallBack<BrowsingRecordData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取面试记录","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(BrowsingRecordData result) {
                                if (result != null) {
                                    LogUtil.error("获取面试记录","result=="+result.toString());

                                    if (result.getCode()==0){//获取面试记录 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取面试记录 失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取面试记录 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
