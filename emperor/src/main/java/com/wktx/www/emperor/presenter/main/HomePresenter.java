package com.wktx.www.emperor.presenter.main;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.main.home.HomeData;
import com.wktx.www.emperor.apiresult.main.home.JobTypeData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.main.IHomeView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 首页
 */

public class HomePresenter extends ABasePresenter<IHomeView> {

    public HomePresenter() {
    }

    //获取工作类型列表
    public void onGetTypeOfWorkList(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HOME_JOBTYPE)
                .execute(new CallBackProxy<CustomApiResult<JobTypeData>, JobTypeData>
                        (new SimpleCallBack<JobTypeData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取工作类型列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetJobTypeFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetJobTypeFailureResult(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(JobTypeData result) {
                                if (result != null) {
                                    LogUtil.error("获取工作类型列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取工作类型列表 成功
                                        getmMvpView().onGetJobTypeSuccessResult(result.getInfo());
                                    }else {//获取工作类型列表 失败
                                        getmMvpView().onGetJobTypeFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetJobTypeFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //获取首页职位列表(轮播图)
    public void onGetHomeInfo(int page){
        HttpParams httpParams = new HttpParams();
        if (getmMvpView().getUserInfo()!=null){
            httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
            httpParams.put("token", getmMvpView().getUserInfo().getToken());
        }
        httpParams.put("page", page+"");
        httpParams.put("limit", "10");
        LogUtil.error("获取首页职位列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HOME)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<HomeData>, HomeData>
                        (new SimpleCallBack<HomeData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取首页职位列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(HomeData result) {
                                if (result != null) {
                                    LogUtil.error("获取首页职位列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取首页职位列表成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取首页职位列表失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
