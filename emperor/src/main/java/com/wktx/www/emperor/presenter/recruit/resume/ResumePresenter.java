package com.wktx.www.emperor.presenter.recruit.resume;

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

/**
 * Created by yyj on 2018/2/2.
 *美工简历---简历
 */

public class ResumePresenter extends ABasePresenter<IView> {

    public ResumePresenter() {
    }

    //获取简历详情
    public void onGetResumeInfo(String resumeId){
        LogUtil.error("获取简历详情","json===id:"+resumeId);

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_INFO)
                .params("id", resumeId)
                .execute(new CallBackProxy<CustomApiResult<ResumeData>, ResumeData>
                        (new ProgressDialogCallBack<ResumeData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
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
}