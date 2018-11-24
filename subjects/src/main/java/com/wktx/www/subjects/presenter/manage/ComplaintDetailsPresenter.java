package com.wktx.www.subjects.presenter.manage;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.manage.ComplaintDetailsData;
import com.wktx.www.subjects.apiresult.manage.FireDetailsData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 投诉详情界面
 */

public class ComplaintDetailsPresenter extends ABasePresenter<IView> {

    public ComplaintDetailsPresenter() {

    }

    //获取投诉详情
    public void getInfo(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id",hireId);

        LogUtil.error("获取投诉详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_COMPLAINT_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ComplaintDetailsData>, ComplaintDetailsData>
                        (new ProgressDialogCallBack<ComplaintDetailsData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取投诉详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(ComplaintDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取投诉详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取投诉详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取投诉详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
