package com.wktx.www.emperor.presenter.staff;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.staff.complaint.ComplaintData;
import com.wktx.www.emperor.apiresult.staff.complaint.ComplaintInfoData;
import com.wktx.www.emperor.apiresult.staff.pause.PauseData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.staff.IStaffPauseWorkView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 发起投诉界面
 */

public class StaffComplaintPresenter extends ABasePresenter<IStaffPauseWorkView> {

    public StaffComplaintPresenter() {
    }

    //获取投诉内容
    public void getComplainInfo(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);

        LogUtil.error("获取投诉内容","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_COMPLAIN_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ComplaintData>, ComplaintData>
                        (new ProgressDialogCallBack<ComplaintData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取投诉内容","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetComplaintFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetComplaintFailureResult(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(ComplaintData result) {
                                if (result != null) {
                                    LogUtil.error("获取投诉内容","result=="+result.toString());

                                    if (result.getCode()==0){//获取投诉内容 成功
                                        getmMvpView().onGetComplaintSuccessResult(result.getInfo());
                                    }else {//获取投诉内容 失败
                                        getmMvpView().onGetComplaintFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetComplaintFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //撤销投诉
    public void onUnComplaint(String complaintId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", complaintId);

        LogUtil.error("撤销投诉","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_UNCOMPLAIN)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("撤销投诉","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("撤销投诉","result=="+result.toString());

                                    if (result.getCode()==0){//撤销投诉 成功
                                        getmMvpView().onRequestSuccess("您的投诉已撤销！");
                                    }else {//撤销投诉 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //发起投诉
    public void onComplaint(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("title", getmMvpView().getPauseCause());
        httpParams.put("content", getmMvpView().getPauseTime());

        LogUtil.error("发起投诉","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_COMPLAIN)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("发起投诉","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("发起投诉","result=="+result.toString());

                                    if (result.getCode()==0){//发起投诉 成功
                                        getmMvpView().onRequestSuccess("您的投诉已发布！");
                                    }else {//发起投诉 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
