package com.wktx.www.subjects.presenter.main;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.main.demand.BannerData;
import com.wktx.www.subjects.apiresult.main.demand.DemandListData;
import com.wktx.www.subjects.apiresult.main.condition.ConditionData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.main.IMainView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 职位片段
 */

public class MainPresenter extends ABasePresenter<IMainView> {

    public MainPresenter() {

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

    //获取轮播图
    public void getBannerInfo(){
        HttpParams httpParams = new HttpParams();
        if (getmMvpView().getUserInfo()!=null){
            httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
            httpParams.put("token", getmMvpView().getUserInfo().getToken());
        }
        LogUtil.error("获取轮播图","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_POSITION_BANNER)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<BannerData>, BannerData>
                        (new SimpleCallBack<BannerData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取轮播图","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetBannerFailureResult(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onGetBannerFailureResult(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(BannerData result) {
                                if (result != null) {
                                    LogUtil.error("获取轮播图","result=="+result.toString());

                                    if (result.getCode()==0){//请求 成功
                                        if (result.getInfo().getTop_ad().size()!=0){//获取轮播图 成功
                                            getmMvpView().onGetBannerSuccessResult(result.getInfo());
                                        }else {//获取轮播图 失败(无数据)
                                            getmMvpView().onGetBannerFailureResult("");
                                        }
                                    }else {//获取轮播图 失败
                                        getmMvpView().onGetBannerFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetBannerFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取职位招聘列表
    public void getInfo(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("tow", getmMvpView().getPositionId());
        httpParams.put("bgat", getmMvpView().getCategoryId());
        httpParams.put("bgap", getmMvpView().getPlatformId());
        httpParams.put("working_years", getmMvpView().getExperienceId());
        if (!getmMvpView().getMinSalary().equals("")){
            httpParams.put("minBudget", getmMvpView().getMinSalary());
        }
        if (!getmMvpView().getMaxSalary().equals("")){
            httpParams.put("maxBudget", getmMvpView().getMaxSalary());
        }
        httpParams.put("page", page+"");
        httpParams.put("pageSize", "10");

        LogUtil.error("获取职位招聘列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_POSITION_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<DemandListData>, DemandListData>
                        (new SimpleCallBack<DemandListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取职位招聘列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(DemandListData result) {
                                if (result != null) {
                                    LogUtil.error("获取职位招聘列表","result=="+result.toString());

                                    if (result.getCode()==0){//请求 成功
                                        if (result.getInfo().size()!=0){//获取职位招聘列表 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                        }else {//获取职位招聘列表 失败(无数据)
                                            getmMvpView().onRequestFailure("");
                                        }
                                    }else {//获取职位招聘列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
