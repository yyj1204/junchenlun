package com.wktx.www.emperor.presenter.staff.report;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.staff.report.ReportListData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 工作报告列表界面
 */

public class ReportListPresenter extends ABasePresenter<IView> {

    public ReportListPresenter() {
    }

    //获取工作报告列表
    public void onGetReportList(String hireId,String taskId,String type){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("aw_id", taskId);
        httpParams.put("type", type);

        LogUtil.error("获取工作报告列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_REPORT_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ReportListData>, ReportListData>
                        (new SimpleCallBack<ReportListData>() {
                            @Override
                            public void onError(ApiException e){
                                LogUtil.error("获取工作报告列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(ReportListData result) {
                                if (result != null) {
                                    LogUtil.error("获取工作报告列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取工作报告列表 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取工作报告列表 失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取工作报告列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
