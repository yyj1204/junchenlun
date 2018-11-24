package com.wktx.www.subjects.presenter.mine;
import com.wktx.www.subjects.apiresult.main.condition.ConditionData;
import com.wktx.www.subjects.apiresult.main.demand.DemandListData;
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
 * 面试记录界面
 */

public class InterviewRecordPresenter extends ABasePresenter<IMyCollectView> {

    public InterviewRecordPresenter() {
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

    //获取面试记录
    public void getInfo(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("tow", getmMvpView().getJobType());
        httpParams.put("page",page+"");
        httpParams.put("pageSize", "10");

        LogUtil.error("获取面试记录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_INTERVIEW_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<DemandListData>, DemandListData>
                        (new ProgressDialogCallBack<DemandListData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
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
                            public void onSuccess(DemandListData result) {
                                if (result != null) {
                                    LogUtil.error("获取面试记录","result=="+result.toString());

                                    if (result.getCode()==0){//请求成功
                                        if (result.getInfo().size()!=0){//获取面试记录 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                        }else {//获取面试记录 失败(无数据)
                                            getmMvpView().onRequestFailure("");
                                        }
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
