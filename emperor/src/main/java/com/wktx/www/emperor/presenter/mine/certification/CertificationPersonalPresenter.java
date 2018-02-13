package com.wktx.www.emperor.presenter.mine.certification;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.CertificationData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.mine.certification.ICertificationPersonalView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/24.
 * 账户认证（个人）
 */

public class CertificationPersonalPresenter extends ABasePresenter<ICertificationPersonalView> {

    public CertificationPersonalPresenter() {
    }

    //账户认证（个人）
    public void onCertification(){
            LogUtil.error("账户认证（个人）","json===user_id:"+getmMvpView().getUserInfo().getUser_id()
                    +"\ntoken:"+getmMvpView().getUserInfo().getToken()+"\nname:"+getmMvpView().getNameStr()
                    +"\nid_card:"+getmMvpView().getIdNumberStr()+"\nfront_pic:"+getmMvpView().getPositivePhotoStr()
                    +"\nback_pic:"+getmMvpView().getReversePhotoStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CERTIFICATION)
                .params("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()))
                .params("token", getmMvpView().getUserInfo().getToken())
                .params("type", "1")
                .params("name", getmMvpView().getNameStr())
                .params("id_card", getmMvpView().getIdNumberStr())
                .params("front_pic", getmMvpView().getPositivePhotoStr())
                .params("back_pic", getmMvpView().getReversePhotoStr())
                .execute(new CallBackProxy<CustomApiResult<CertificationData>, CertificationData>
                        (new ProgressDialogCallBack<CertificationData>(mProgressDialog) {
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
                            public void onSuccess(CertificationData result) {
                                if (result != null) {
                                    LogUtil.error("账户认证（个人）","result=="+result.toString());

                                    if (result.getCode()==0){//账户认证（个人）成功
                                        getmMvpView().onRequestSuccess(result);
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
