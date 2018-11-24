package com.wktx.www.subjects.presenter.manage;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.manage.attendance.AttendanceRecordData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.manage.IAttendanceView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/6/26.
 * 考勤记录界面
 */

public class AttendancePresenter extends ABasePresenter<IAttendanceView> {

    public AttendancePresenter() {
    }

    //获取考勤记录信息
    public void getInfo(String hireId,String time){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("time", time);

        LogUtil.error("获取考勤记录信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORK_ATTENDANCE)
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


    //签到
    public void onSignIn(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);

        LogUtil.error("签到","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORK_SIGNIN)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e){
                                super.onError(e);
                                LogUtil.error("签到","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onSingInResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onSingInResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("签到","result=="+result.toString());

                                    if (result.getCode()==0){//签到 成功
                                        getmMvpView().onSingInResult(true,result.getMsg());
                                    }else {//签到 失败
                                        getmMvpView().onSingInResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onSingInResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
