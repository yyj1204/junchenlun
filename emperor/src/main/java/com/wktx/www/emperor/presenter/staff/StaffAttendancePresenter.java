package com.wktx.www.emperor.presenter.staff;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.staff.attendance.AttendanceRecordData;
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
 * 考勤记录界面
 */

public class StaffAttendancePresenter extends ABasePresenter<IView> {

    public StaffAttendancePresenter() {
    }

    //获取考勤记录信息
    public void onGetAttendanceInfo(String hireId,String time){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("time", time);

        LogUtil.error("获取考勤记录信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_ATTENDANCE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<AttendanceRecordData>, AttendanceRecordData>
                        (new ProgressDialogCallBack<AttendanceRecordData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e){
                                super.onError(e);
                                LogUtil.error("获取考勤记录信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(AttendanceRecordData result) {
                                if (result != null) {
                                    LogUtil.error("获取考勤记录信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取考勤记录信息 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取考勤记录信息 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
