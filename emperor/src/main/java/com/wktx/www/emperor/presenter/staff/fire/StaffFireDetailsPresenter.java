package com.wktx.www.emperor.presenter.staff.fire;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.staff.fire.StaffFireDetailsData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.ui.view.staff.fire.IStaffFireDetailsView;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 解雇详情界面
 */

public class StaffFireDetailsPresenter extends ABasePresenter<IStaffFireDetailsView> {

    public StaffFireDetailsPresenter() {
    }

    //获取解雇详情
    public void onGetFireInfo(String fireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", fireId);

        LogUtil.error("获取解雇详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_FIREINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StaffFireDetailsData>, StaffFireDetailsData>
                        (new ProgressDialogCallBack<StaffFireDetailsData>(mProgressDialog){
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取解雇详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(StaffFireDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取解雇详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取解雇详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取解雇详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //取消解雇
    public void onCancelFire(String fireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", fireId);

        LogUtil.error("取消解雇","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_FIRE_CANCEL)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("取消解雇","e=="+e.getMessage());

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
                                    LogUtil.error("取消解雇","result=="+result.toString());

                                    if (result.getCode()==0){//取消解雇 成功
                                        getmMvpView().onFireResult(true,"您的解雇请求已取消！");
                                    }else {//取消解雇 失败
                                        getmMvpView().onFireResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onFireResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //申请后台介入
    public void onApplyFire(String fireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", fireId);

        LogUtil.error("申请后台介入","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_FIRE_APPLY)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("申请后台介入","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onFireResult(false, ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onFireResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("申请后台介入","result=="+result.toString());

                                    if (result.getCode()==0){//申请后台介入 成功
                                        getmMvpView().onFireResult(true,result.getMsg());
                                    }else {//申请后台介入 失败
                                        getmMvpView().onFireResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onFireResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
