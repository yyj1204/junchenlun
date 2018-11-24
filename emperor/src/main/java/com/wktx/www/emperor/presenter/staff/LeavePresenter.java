package com.wktx.www.emperor.presenter.staff;
import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.ImgUploadData;
import com.wktx.www.emperor.apiresult.staff.leave.LeaveListData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.ui.view.staff.ILeaveView;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 请假记录
 */

public class LeavePresenter extends ABasePresenter<ILeaveView> {

    public LeavePresenter() {
    }


    //获取请假记录
    public void getInfo(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        LogUtil.error("获取请假记录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_LEAVE_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<LeaveListData>, LeaveListData>
                        (new ProgressDialogCallBack<LeaveListData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取请假记录","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(LeaveListData result) {
                                if (result != null) {
                                    LogUtil.error("获取请假记录","result=="+result.toString());

                                    if (result.getCode()==0) {//获取请假记录 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取请假记录 失败（没数据）
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取请假记录 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }



    //同意请假申请
    public void leaveAgree(String hireId,String leaveId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("id", leaveId);

        LogUtil.error("同意请假申请","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_LEAVE_AGREE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("同意请假申请","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onLeaveResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onLeaveResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("同意请假申请","result=="+result.toString());

                                    if (result.getCode()==0){//同意请假申请 成功
                                        getmMvpView().onLeaveResult(true,result.getMsg());
                                    }else {//同意请假申请 失败
                                        getmMvpView().onLeaveResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onLeaveResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //拒绝请假申请
    public void leaveRefuse(String hireId,String leaveId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("id", leaveId);

        LogUtil.error("拒绝请假申请","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_LEAVE_REFUSE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("拒绝请假申请","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onLeaveResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onLeaveResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("拒绝请假申请","result=="+result.toString());

                                    if (result.getCode()==0){//拒绝请假申请 成功
                                        getmMvpView().onLeaveResult(true,result.getMsg());
                                    }else {//拒绝请假申请 失败
                                        getmMvpView().onLeaveResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onLeaveResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

}
