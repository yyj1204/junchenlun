package com.wktx.www.emperor.presenter.recruit.resume;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.resume.ResumeData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/2/2.
 *美工简历---简历（收藏、取消收藏）
 */

public class ResumePresenter extends ABasePresenter<IView> {

    public ResumePresenter() {
    }

    //获取简历详情
    public void onGetResumeInfo(String resumeId){
        HttpParams httpParams = new HttpParams();
        if (getmMvpView().getUserInfo()!=null){
            httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
            httpParams.put("token", getmMvpView().getUserInfo().getToken());
        }
        httpParams.put("id", resumeId);

        LogUtil.error("获取简历详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ResumeData>, ResumeData>
                        (new ProgressDialogCallBack<ResumeData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取简历详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(ResumeData result) {
                                if (result != null) {
                                    LogUtil.error("获取简历详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取简历详情成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取简历详情失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //收藏、取消收藏简历
    public void onCollectResume(String resumeId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("rid", resumeId);

        LogUtil.error("收藏简历","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_COLLECT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("收藏简历","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("收藏简历","result=="+result.toString());

                                    if (result.getCode()==0){//收藏（取消收藏）简历成功
                                        getmMvpView().onRequestSuccess(result.getMsg());
                                    }else {//收藏（取消收藏）简历失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
