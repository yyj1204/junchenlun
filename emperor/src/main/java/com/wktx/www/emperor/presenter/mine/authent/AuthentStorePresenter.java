package com.wktx.www.emperor.presenter.mine.authent;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.authent.AuthentStoreData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.ui.view.mine.authent.IAuthentStoreView;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 账户认证（店铺）
 */

public class AuthentStorePresenter extends ABasePresenter<IAuthentStoreView> {

    public AuthentStorePresenter() {
    }

    //账户认证（店铺）
    public void onCertification(String authentId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        //如果认证id不为空，即为重新认证
        if (!authentId.equals("")){
            httpParams.put("id", authentId);
        }
        httpParams.put("type", "2");
        httpParams.put("cp_name", getmMvpView().getCompanyNameStr());
        httpParams.put("cd_code", getmMvpView().getCreditCodeStr());
        httpParams.put("sp_name", getmMvpView().getOnlineStoreNameStr());
        httpParams.put("sp_url", getmMvpView().getOnlineStoreLinkStr());
        httpParams.put("tb_asct_id", getmMvpView().getTaobaoIDStr());
        httpParams.put("front_pic", getmMvpView().getPositivePhotoStr());
        httpParams.put("lics_pic", getmMvpView().getBusinessLicensePhotoStr());
        LogUtil.error("账户认证（店铺)","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CERTIFICATION)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("账户认证（店铺）","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("账户认证（店铺）","result=="+result.toString());

                                    if (result.getCode()==0){//账户认证（店铺）成功
                                        getmMvpView().onRequestSuccess(result.getMsg());
                                    }else {//账户认证（店铺）失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取认证详情（店铺）
    public void getCertificationInfo(String authentId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("type", "2");
        httpParams.put("id", authentId);

        LogUtil.error("获取认证详情（店铺)","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CERTIFICATION_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<AuthentStoreData>, AuthentStoreData>
                        (new ProgressDialogCallBack<AuthentStoreData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取认证详情（店铺）","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetAuthentInfoFailureResult(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onGetAuthentInfoFailureResult(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(AuthentStoreData result) {
                                if (result != null) {
                                    LogUtil.error("获取认证详情（店铺）","result=="+result.toString());

                                    if (result.getCode()==0){//获取认证详情（店铺）成功
                                        getmMvpView().onGetAuthentInfoSuccessResult(result.getInfo());
                                    }else {//获取认证详情（店铺）失败
                                        getmMvpView().onGetAuthentInfoFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetAuthentInfoFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
