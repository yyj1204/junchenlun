package com.wktx.www.emperor.presenter.mine.certification;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.CertificationData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.mine.certification.ICertificationStoreView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/24.
 * 账户认证（店铺）
 */

public class CertificationStorePresenter extends ABasePresenter<ICertificationStoreView> {

    public CertificationStorePresenter() {
    }

    //账户认证（店铺）
    public void onCertification(){
            LogUtil.error("账户认证（店铺）","json===user_id:"+getmMvpView().getUserInfo().getUser_id()
                    +"\ntoken:"+getmMvpView().getUserInfo().getToken()+"\ncp_name:"+getmMvpView().getCompanyNameStr()
                    +"\ncd_code:"+getmMvpView().getCreditCodeStr()+"\nfront_pic:"+getmMvpView().getPositivePhotoStr()
                    +"\nlics_pic:"+getmMvpView().getOnlineStoreLinkStr()+"\nsp_name:"+getmMvpView().getOnlineStoreNameStr()
                    +"\nsp_url:"+getmMvpView().getBusinessLicensePhotoStr()+"\ntb_asct_id:"+getmMvpView().getTaobaoIDStr());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CERTIFICATION)
                .params("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()))
                .params("token", getmMvpView().getUserInfo().getToken())
                .params("type", "2")
                .params("cp_name", getmMvpView().getCompanyNameStr())
                .params("cd_code", getmMvpView().getCreditCodeStr())
                .params("front_pic", getmMvpView().getPositivePhotoStr())
                .params("lics_pic", getmMvpView().getBusinessLicensePhotoStr())
                .params("sp_name", getmMvpView().getOnlineStoreNameStr())
                .params("sp_url", getmMvpView().getOnlineStoreLinkStr())
                .params("tb_asct_id", getmMvpView().getTaobaoIDStr())
                .execute(new CallBackProxy<CustomApiResult<CertificationData>, CertificationData>
                        (new ProgressDialogCallBack<CertificationData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("账户认证（店铺）","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CertificationData result) {
                                if (result != null) {
                                    LogUtil.error("账户认证（店铺）","result=="+result.toString());

                                    if (result.getCode()==0){//账户认证（店铺）成功
                                        getmMvpView().onRequestSuccess(result);
                                    }else {//账户认证（店铺）失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
