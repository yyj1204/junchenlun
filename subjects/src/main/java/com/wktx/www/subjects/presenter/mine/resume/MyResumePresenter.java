package com.wktx.www.subjects.presenter.mine.resume;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.mine.resume.ResumeData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.resume.IMyResumeView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 我的简历界面
 */

public class MyResumePresenter extends ABasePresenter<IMyResumeView> {

    public MyResumePresenter() {
    }

    //获取我的简历信息
    public void getInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());

        LogUtil.error("获取我的简历信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ResumeData>, ResumeData>
                        (new ProgressDialogCallBack<ResumeData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取我的简历信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(ResumeData result) {
                                if (result != null) {
                                    LogUtil.error("获取我的简历信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取我的简历信息 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取我的简历信息 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //修改找工作状态
    public void changeHuntingState(String resumeId,String huntingState){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", resumeId);
        httpParams.put("is_job_hunting", huntingState);

        LogUtil.error("修改找工作状态","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_JOBHUNTING)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("修改找工作状态","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onChangeHuntingResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onChangeHuntingResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("修改找工作状态","result=="+result.toString());

                                    if (result.getCode()==0){//修改找工作状态 成功
                                        getmMvpView().onChangeHuntingResult(true,result.getMsg());
                                    }else {//修改找工作状态 失败
                                        getmMvpView().onChangeHuntingResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onChangeHuntingResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
