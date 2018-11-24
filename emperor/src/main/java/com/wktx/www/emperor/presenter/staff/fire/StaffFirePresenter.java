package com.wktx.www.emperor.presenter.staff.fire;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.staff.fire.StaffFireData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.staff.fire.IStaffFireView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 发起解雇界面
 */

public class StaffFirePresenter extends ABasePresenter<IStaffFireView> {

    public StaffFirePresenter() {
    }

    //获取发起解雇信息
    public void onGetFireInfo(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);

        LogUtil.error("获取发起解雇信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_LAUNCH_FIREINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StaffFireData>, StaffFireData>
                        (new ProgressDialogCallBack<StaffFireData>(mProgressDialog){
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取发起解雇信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(StaffFireData result) {
                                if (result != null) {
                                    LogUtil.error("获取发起解雇信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取发起解雇信息 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取发起解雇信息 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //确认解雇
    public void onFire(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("amount", getmMvpView().getAmount());
        httpParams.put("reason", getmMvpView().getFireCause());

        LogUtil.error("确认解雇","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_FIRE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("确认解雇","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onFireResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onFireResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("确认解雇","result=="+result.toString());

                                    if (result.getCode()==0){//确认解雇 成功
                                        getmMvpView().onFireResult(true,"您的解雇请求已发布成功！");
                                    }else {//确认解雇 失败
                                        getmMvpView().onFireResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onFireResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
