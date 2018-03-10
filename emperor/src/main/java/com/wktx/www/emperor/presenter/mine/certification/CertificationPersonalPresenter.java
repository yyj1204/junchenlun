package com.wktx.www.emperor.presenter.mine.certification;

import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.mine.certification.ICertificationPersonalView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 账户认证（个人）
 */

public class CertificationPersonalPresenter extends ABasePresenter<ICertificationPersonalView> {

    public CertificationPersonalPresenter() {
    }

    //账户认证（个人）
    public void onCertification(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("type", "1");
        httpParams.put("name", getmMvpView().getNameStr());
        httpParams.put("id_card", getmMvpView().getIdNumberStr());
        httpParams.put("front_pic", getmMvpView().getPositivePhotoStr());
        httpParams.put("back_pic", getmMvpView().getReversePhotoStr());
        LogUtil.error("账户认证（个人)","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CERTIFICATION)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("账户认证（个人）","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("账户认证（个人）","result=="+result.toString());

                                    if (result.getCode()==0){//账户认证（个人）成功
                                        getmMvpView().onRequestSuccess(result.getMsg());
                                    }else {//账户认证（个人）失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
