package com.wktx.www.emperor.presenter.recruit.resume;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksListData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 美工简历---作品列表
 */

public class WorksListPresenter extends ABasePresenter<IView> {

    public WorksListPresenter() {
    }

    //获取作品列表
    public void onGetResumeWorksList(String resumeId,int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("id", resumeId);
        httpParams.put("page",page+"");
        httpParams.put("limit","10");

        LogUtil.error("获取作品列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_WORKS)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<WorksListData>, WorksListData>
                        (new SimpleCallBack<WorksListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取作品列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(WorksListData result) {
                                if (result != null) {
                                    LogUtil.error("获取作品列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取作品列表成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取作品列表失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取作品列表失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
