package com.wktx.www.subjects.presenter.manage;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.manage.WorkDetailsData;
import com.wktx.www.subjects.apiresult.manage.WorkListData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.IView;
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
 * 管理片段、管理我的工作界面
 */

public class ManagePresenter extends ABasePresenter<IView> {

    public ManagePresenter() {
    }

    //获取工作列表
    public void getInfo(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("page", page+"");
        httpParams.put("pageSize", "10");
        LogUtil.error("获取工作列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORK_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<WorkListData>, WorkListData>
                        (new SimpleCallBack<WorkListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取工作列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(WorkListData result) {
                                if (result != null) {
                                    LogUtil.error("获取工作列表","result=="+result.toString());

                                    if (result.getCode()==0){//请求成功
                                        if (result.getInfo().size()!=0){//获取工作列表 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                        }else {//获取工作列表 失败（没数据）
                                            getmMvpView().onRequestFailure("");
                                        }
                                    }else {//获取工作列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取工作详情
    public void getWorkInfo(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hid", hireId);
        LogUtil.error("获取工作详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORK_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<WorkDetailsData>, WorkDetailsData>
                        (new SimpleCallBack<WorkDetailsData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取工作详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(WorkDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取工作详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取工作详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取工作详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
