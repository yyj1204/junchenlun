package com.wktx.www.subjects.presenter.manage;
import com.wktx.www.subjects.apiresult.manage.salary.SalaryData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 我的工资界面
 */

public class SalaryPresenter extends ABasePresenter<IView> {

    public SalaryPresenter() {
    }

    //获取我的工资
    public void getInfo(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hid", hireId);

        LogUtil.error("获取我的工资","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORK_SALARY)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<SalaryData>, SalaryData>
                        (new SimpleCallBack<SalaryData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取我的工资","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(SalaryData result) {
                                if (result != null) {
                                    LogUtil.error("获取我的工资","result=="+result.toString());

                                    if (result.getCode()==0){//获取我的工资 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取我的工资 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
