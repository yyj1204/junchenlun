package com.wktx.www.emperor.presenter.recruit.recruit;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.recruitlist.RecruitListData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.recruit.IRecruitListView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/24.
 * 招聘片段---职位检索结果
 */

public class RecruitListPresenter extends ABasePresenter<IRecruitListView> {

    public RecruitListPresenter() {
    }

    //获取招聘职位检索结果列表
    public void onGetRecruitList(int page){
        LogUtil.error("获取招聘列表","json===tow:"+getmMvpView().getJobTypeId() +"\nbgat:"+getmMvpView().getCategoryId()
                +"\nbgap:"+getmMvpView().getPlatformId()+"\ncust_service_type:"+getmMvpView().getServiceId()
                +"\nworking_years:"+getmMvpView().getExperienceId() +"\nsex:"+getmMvpView().getSexId()+"\npage:"+page);

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RECRUIT_LIST)
                .params("keyword","")
                .params("tow",getmMvpView().getJobTypeId())
                .params("bgat",getmMvpView().getCategoryId())
                .params("bgap",getmMvpView().getPlatformId())
                .params("cust_service_type",getmMvpView().getServiceId())
                .params("working_years",getmMvpView().getExperienceId())
                .params("sex",getmMvpView().getSexId())
                .params("page",page+"")
                .params("limit","10")
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
