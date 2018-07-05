package com.wktx.www.emperor.presenter.staff;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.staff.salary.StaffSalaryData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 员工工资界面
 */

public class StaffSalaryPresenter extends ABasePresenter<IView> {

    public StaffSalaryPresenter() {
    }

    //获取员工工资
    public void onGetStaffSalaryInfo(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);

        LogUtil.error("获取员工工资","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_SALARY)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StaffSalaryData>, StaffSalaryData>
                        (new SimpleCallBack<StaffSalaryData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取员工工资","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(StaffSalaryData result) {
                                if (result != null) {
                                    LogUtil.error("获取员工工资","result=="+result.toString());

                                    if (result.getCode()==0){//获取员工工资 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取员工工资 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
