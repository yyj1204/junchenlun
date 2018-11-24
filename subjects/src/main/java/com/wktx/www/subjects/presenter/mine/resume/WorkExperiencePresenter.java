package com.wktx.www.subjects.presenter.mine.resume;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.mine.resume.WorkExperienceBean;
import com.wktx.www.subjects.apiresult.main.condition.ConditionData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.resume.IWorkExperienceView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

import java.util.ArrayList;

/**
 * Created by yyj on 2018/1/15.
 * 我的简历---工作经历界面
 */

public class WorkExperiencePresenter extends ABasePresenter<IWorkExperienceView> {

    public WorkExperiencePresenter() {
    }

    //获取参数信息（平台、风格、类目、职位、工作经验）
    public void getInfo(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_POSITION_CONDITION)
                .execute(new CallBackProxy<CustomApiResult<ConditionData>, ConditionData>
                        (new ProgressDialogCallBack<ConditionData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取参数信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(ConditionData result) {
                                if (result != null) {
                                    LogUtil.error("获取参数信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取参数信息 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取参数信息 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //编辑工作经历（增删改）
    public void changeWorkExperience(String resumeId,ArrayList<WorkExperienceBean> workExperienceBeans){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", resumeId);
        //工作经历
        Gson gson = new GsonBuilder().create();
        String workExperienceStrs = gson.toJson(workExperienceBeans);
        httpParams.put("work_experience", workExperienceStrs);

        LogUtil.error("编辑工作经历","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_EXPERIENCE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("编辑工作经历","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onChangeWorkExperienceResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onChangeWorkExperienceResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("编辑工作经历","result=="+result.toString());

                                    if (result.getCode()==0){//编辑工作经历 成功
                                        getmMvpView().onChangeWorkExperienceResult(true,result.getMsg());
                                    }else {//编辑工作经历 失败
                                        getmMvpView().onChangeWorkExperienceResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onChangeWorkExperienceResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
