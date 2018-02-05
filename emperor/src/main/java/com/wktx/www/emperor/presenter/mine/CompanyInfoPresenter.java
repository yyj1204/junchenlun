package com.wktx.www.emperor.presenter.mine;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.mine.ICompanyInfoView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/15.
 * 公司信息界面界面
 */

public class CompanyInfoPresenter extends ABasePresenter<ICompanyInfoView> {

    public CompanyInfoPresenter() {
    }

    //退出登录
    public void onLogout(){
        LogUtil.error("退出登录","json===user_id:"+getmMvpView().getUserInfo().getUser_id()
                +"\ntoken:"+getmMvpView().getUserInfo().getToken());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_LOGOUT)
                .params("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()))
                .params("token", getmMvpView().getUserInfo().getToken())
                .execute(new CallBackProxy<CustomApiResult<String>, String>
                        (new ProgressDialogCallBack<String>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("退出登录","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onLogout(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onLogout(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(String result) {
                                LogUtil.error("退出登录","result=="+result.toString());

                                getmMvpView().onLogout(true,"已退出登录！");
                            }
                        }) {});
    }
}
