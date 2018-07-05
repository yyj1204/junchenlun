package com.wktx.www.emperor.presenter.staff;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.hire.HireData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.staff.IStaffRenewalView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/2/2.
 *员工管理 --- 续签界面
 */

public class StaffRenewalPresenter extends ABasePresenter<IStaffRenewalView> {

    public StaffRenewalPresenter() {
    }

    //获取续签信息
    public void onGetHireInfo(String hireId,boolean isService){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("hire_time", getmMvpView().getRenewalTime());
        if (isService){
            httpParams.put("commission", getmMvpView().getPushMoney());
        }

        LogUtil.error("获取续签信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_RENEWAL)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<HireData>, HireData>
                        (new ProgressDialogCallBack<HireData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取续签信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(HireData result) {
                                if (result != null) {
                                    LogUtil.error("获取续签信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取续签信息成功
                                        getmMvpView().onRequestSuccess( result.getInfo());
                                    }else {//获取续签信息失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
