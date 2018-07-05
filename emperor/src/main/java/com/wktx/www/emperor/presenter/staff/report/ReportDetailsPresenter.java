package com.wktx.www.emperor.presenter.staff.report;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.staff.report.ReportDetailsData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.staff.report.IReportDetailsView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 工作报告---报告详情界面
 */

public class ReportDetailsPresenter extends ABasePresenter<IReportDetailsView> {

    public ReportDetailsPresenter() {
    }

    //获取工作报告详情
    public void onGetReportInfo(String reportId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", reportId);

        LogUtil.error("获取工作报告详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_REPORT_DETAILS)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ReportDetailsData>, ReportDetailsData>
                        (new ProgressDialogCallBack<ReportDetailsData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e){
                                LogUtil.error("获取工作报告详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(ReportDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取工作报告详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取工作报告详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取工作报告详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //评价工作报告
    public void onEvaluateReport(String reportId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", reportId);
        httpParams.put("attitude", getmMvpView().getAttitude());
        httpParams.put("ability", getmMvpView().getAbility());
        httpParams.put("speed", getmMvpView().getSpeed());
        httpParams.put("content", getmMvpView().getEvaluateContent());

        LogUtil.error("评价工作报告","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_REPORT_EVALUATE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e){
                                LogUtil.error("评价工作报告","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onEvaluateReportResult(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onEvaluateReportResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("评价工作报告","result=="+result.toString());

                                    if (result.getCode()==0){//评价工作报告 成功
                                        getmMvpView().onEvaluateReportResult(true,"您的报告评价已发布成功！");
                                    }else {//评价工作报告 失败
                                        getmMvpView().onEvaluateReportResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onEvaluateReportResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
