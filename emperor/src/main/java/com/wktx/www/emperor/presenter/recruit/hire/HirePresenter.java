package com.wktx.www.emperor.presenter.recruit.hire;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.hire.HireData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.DateUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.recruit.hire.IHireView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/2/2.
 *美工简历---简历---雇佣
 */

public class HirePresenter extends ABasePresenter<IHireView> {

    public HirePresenter() {
    }

    //获取雇佣信息
    public void onGetHireInfo(String resumeId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("rid", resumeId);
        httpParams.put("hire_type", getmMvpView().getHireWay());//雇佣方式 1:包月,2:定制, 3:雇佣单人（班次）
        httpParams.put("demand", getmMvpView().getDemandContent());
        httpParams.put("qq", getmMvpView().getQQNumber());
        httpParams.put("wechat", getmMvpView().getWeChatNumber());
        httpParams.put("project_start_time", DateUtil.getCustomType2Timestamp(getmMvpView().getBeginTime(),"yyyy-MM-dd"));
        httpParams.put("commission", getmMvpView().getPushMoney());//提成（雇佣单人必填，建议1~15%）

        if (getmMvpView().getHireWay().equals("3")){//客服类型
            httpParams.put("hire_time", getmMvpView().getHireTime());//雇佣时间（月）（包月、雇佣单人必填）
            httpParams.put("work_shift_id", "1");//上班班次（id）（雇佣必填，填1）
            httpParams.put("work_shift", getmMvpView().getServiceShifts());
        }else {//其他职业类型
            if (getmMvpView().getHireWay().equals("1")){//包月
                httpParams.put("hire_time", getmMvpView().getHireTime());
            }else if (getmMvpView().getHireWay().equals("2")){//定制
                httpParams.put("project_end_time", DateUtil.getCustomType2Timestamp(getmMvpView().getEndTime(),"yyyy-MM-dd"));
                httpParams.put("customized_price", getmMvpView().getcustomPrice());//定制价格（定制必填）
            }
        }
        LogUtil.error("获取雇佣信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HIRE_HIREINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<HireData>, HireData>
                        (new ProgressDialogCallBack<HireData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取雇佣信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(HireData result) {
                                if (result != null) {
                                    LogUtil.error("获取雇佣信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取雇佣信息成功
                                        getmMvpView().onRequestSuccess( result.getInfo());
                                    }else {//获取雇佣信息失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
