package com.wktx.www.subjects.presenter.mine.resume;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.main.condition.ConditionData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.resume.IResumePositionView;
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
 * 我的简历---应聘职位界面
 */

public class ResumePositionPresenter extends ABasePresenter<IResumePositionView> {

    public ResumePositionPresenter() {
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


    //编辑应聘职位（增改）
    public void changeApplyPosition(String resumeId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", resumeId);
        httpParams.put("tow", getmMvpView().getPositionId());
        httpParams.put("bgat", getmMvpView().getCategoryIds());
        httpParams.put("bgap", getmMvpView().getPlatformId());
        httpParams.put("working_years", getmMvpView().getExperienceId());
        httpParams.put("monthly_money", getmMvpView().getSalary());

        //美工与客服，需要再传风格与打字速度
        if (getmMvpView().getPositionId().equals("1")){//美工
            httpParams.put("bgas", getmMvpView().getStyleIds());
        }else if (getmMvpView().getPositionId().equals("2")){//客服
            httpParams.put("typing_speed", getmMvpView().getSpeed());
        }

        LogUtil.error("编辑应聘职位","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_POSITION)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("编辑应聘职位","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onChangeApplyPositionResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onChangeApplyPositionResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("编辑应聘职位","result=="+result.toString());

                                    if (result.getCode()==0){//编辑应聘职位 成功
                                        getmMvpView().onChangeApplyPositionResult(true,result.getMsg());
                                    }else {//编辑应聘职位 失败
                                        getmMvpView().onChangeApplyPositionResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onChangeApplyPositionResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //删除应聘职位
    public void deleteApplyPosition(String resumeId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", resumeId);

        LogUtil.error("删除应聘职位","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_POSITION_DELETE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("删除应聘职位","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onChangeApplyPositionResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onChangeApplyPositionResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("删除应聘职位","result=="+result.toString());

                                    if (result.getCode()==0){//删除应聘职位 成功
                                        getmMvpView().onChangeApplyPositionResult(true,result.getMsg());
                                    }else {//删除应聘职位 失败
                                        getmMvpView().onChangeApplyPositionResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onChangeApplyPositionResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
