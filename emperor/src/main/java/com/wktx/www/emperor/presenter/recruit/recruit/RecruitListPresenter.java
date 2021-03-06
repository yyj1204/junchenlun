package com.wktx.www.emperor.presenter.recruit.recruit;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.recruitlist.RecruitListData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.recruit.IRecruitListView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 招聘片段---职位检索结果
 */

public class RecruitListPresenter extends ABasePresenter<IRecruitListView> {

    public RecruitListPresenter() {
    }

    //获取招聘职位检索结果列表
    public void onGetRecruitList(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("keyword","");
        httpParams.put("tow",getmMvpView().getJobTypeId());
        httpParams.put("bgat",getmMvpView().getCategoryId());
        httpParams.put("bgap",getmMvpView().getPlatformId());
//        httpParams.put("cust_service_type",getmMvpView().getServiceId());
        httpParams.put("working_years",getmMvpView().getExperienceId());
        httpParams.put("sex",getmMvpView().getSexId());
        httpParams.put("page",page+"");
        httpParams.put("limit","10");

        LogUtil.error("获取招聘列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RECRUIT_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<RecruitListData>, RecruitListData>
                        (new SimpleCallBack<RecruitListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取招聘列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(RecruitListData result) {
                                if (result != null) {
                                    LogUtil.error("获取招聘列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取招聘列表成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取招聘列表失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取招聘列表失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
