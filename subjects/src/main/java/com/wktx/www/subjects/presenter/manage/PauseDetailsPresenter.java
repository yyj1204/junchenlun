package com.wktx.www.subjects.presenter.manage;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.manage.PauseDetailsData;
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
 * 暂停工作详情界面
 */

public class PauseDetailsPresenter extends ABasePresenter<IView> {

    public PauseDetailsPresenter() {

    }

    //获取暂停工作详情
    public void getInfo(String hireId,String pauseId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id",hireId);
        httpParams.put("id",pauseId);

        LogUtil.error("获取暂停工作详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_PAUSE_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<PauseDetailsData>, PauseDetailsData>
                        (new ProgressDialogCallBack<PauseDetailsData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取暂停工作详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(PauseDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取暂停工作详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取暂停工作详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取暂停工作详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
