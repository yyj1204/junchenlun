package com.wktx.www.emperor.presenter.staff.report;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.staff.report.ReportSaleroomData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 工作报告---销售额界面
 */

public class ReportSaleroomPresenter extends ABasePresenter<IView> {

    public ReportSaleroomPresenter() {
    }

    //获取销售额
    public void onGetSaleroomInfo(String hireId,String monthId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("id", monthId);

        LogUtil.error("获取销售额详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_REPORT_SALEROOM)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ReportSaleroomData>, ReportSaleroomData>
                        (new ProgressDialogCallBack<ReportSaleroomData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e){
                                LogUtil.error("获取销售额详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(ReportSaleroomData result) {
                                if (result != null) {
                                    LogUtil.error("获取销售额详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取销售额详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取销售额详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
