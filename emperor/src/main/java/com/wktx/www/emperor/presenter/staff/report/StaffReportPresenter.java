package com.wktx.www.emperor.presenter.staff.report;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.store.StoreListData;
import com.wktx.www.emperor.apiresult.staff.report.StaffReportData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.staff.report.IStaffReportView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 工作报告(任务)界面
 */

public class StaffReportPresenter extends ABasePresenter<IStaffReportView> {

    public StaffReportPresenter() {
    }

    //获取店铺检索条件
    public void onGetCondition(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_STORE_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StoreListData>, StoreListData>
                        (new ProgressDialogCallBack<StoreListData>(mProgressDialog){
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取店铺检索条件","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetConditionFailureResult(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(StoreListData result) {
                                if (result != null) {
                                    LogUtil.error("获取店铺列表检索条件","result=="+result.toString());

                                    if (result.getCode()==0){//获取店铺检索条件成功
                                        getmMvpView().onGetConditionSuccessResult(result.getInfo());
                                    }else {//获取店铺检索条件失败
                                        getmMvpView().onGetConditionFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取工作报告（任务）列表
    public void onGetReportTaskList(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("sid", getmMvpView().getStoreId());

        LogUtil.error("获取工作报告（任务）列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_REPORT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StaffReportData>, StaffReportData>
                        (new SimpleCallBack<StaffReportData>() {
                            @Override
                            public void onError(ApiException e){
                                LogUtil.error("获取工作报告（任务）列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(StaffReportData result) {
                                if (result != null) {
                                    LogUtil.error("获取工作报告（任务）列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取工作报告（任务）列表 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取工作报告（任务）列表 失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取工作报告（任务）列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
