package com.wktx.www.subjects.presenter.manage;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.ImgUploadData;
import com.wktx.www.subjects.apiresult.manage.leave.LeaveListData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.manage.ILeaveView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.DateUtil;
import com.wktx.www.subjects.utils.LogUtil;
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
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        LogUtil.error("获取请假记录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORK_LEAVELIST)
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

    //上传图片Base64
    public void getImgUrl(String base64Str){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("base64", base64Str);

        LogUtil.error("上传图片Base64","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_LEAVE_BASE64)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ImgUploadData>, ImgUploadData>
                        (new ProgressDialogCallBack<ImgUploadData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("上传图片Base64","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetImgUrlResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onGetImgUrlResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(ImgUploadData result) {
                                if (result != null) {
                                    LogUtil.error("上传图片Base64","result=="+result.toString());

                                    if (result.getCode()==0){//上传图片Base64 成功
                                        getmMvpView().onGetImgUrlResult(true,result.getInfo());
                                    }else {//上传图片Base64 失败
                                        getmMvpView().onGetImgUrlResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetImgUrlResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //请假申请
    public void leaveApply(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("reason", getmMvpView().getReason());
        httpParams.put("image", getmMvpView().getLeaveImgUrl());
        httpParams.put("start_time", DateUtil.getCustomType2Timestamp(getmMvpView().getBeginTime(),"yyyy-MM-dd HH:mm"));
        httpParams.put("end_time", DateUtil.getCustomType2Timestamp(getmMvpView().getEndTime(),"yyyy-MM-dd HH:mm"));

        LogUtil.error("请假申请","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORK_LEAVE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("请假申请","e=="+e.getMessage());

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
                                    LogUtil.error("请假申请","result=="+result.toString());

                                    if (result.getCode()==0){//请假申请 成功
                                        getmMvpView().onLeaveResult(true,result.getMsg());
                                    }else {//请假申请 失败
                                        getmMvpView().onLeaveResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onLeaveResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

}
