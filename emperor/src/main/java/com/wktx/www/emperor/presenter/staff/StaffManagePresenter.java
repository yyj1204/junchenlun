package com.wktx.www.emperor.presenter.staff;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.staff.manage.StaffManageData;
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
 * 员工管理界面
 */

public class StaffManagePresenter extends ABasePresenter<IView> {

    public StaffManagePresenter() {
    }

    //获取我的员工(雇佣记录)信息
    public void onGetStaffInfo(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);

        LogUtil.error("获取我的员工信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HIRE_RECORD_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StaffManageData>, StaffManageData>
                        (new ProgressDialogCallBack<StaffManageData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取我的员工信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(StaffManageData result) {
                                if (result != null) {
                                    LogUtil.error("获取我的员工信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取我的员工信息成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取我的员工信息失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
