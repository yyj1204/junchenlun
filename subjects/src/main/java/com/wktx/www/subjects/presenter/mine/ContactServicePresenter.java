package com.wktx.www.subjects.presenter.mine;
import com.wktx.www.subjects.apiresult.mine.service.ServiceData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/3/05.
 * 我的收藏界面
 */

public class ContactServicePresenter extends ABasePresenter<IView> {

    public ContactServicePresenter() {
    }

    //获取客服电话、客服qq
    public void onGetServiceInfo(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .baseUrl(ApiURL.GLOBAL_URL1)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CONTACT_SERVICE)
                .execute(new CallBackProxy<CustomApiResult<ServiceData>, ServiceData>
                        (new ProgressDialogCallBack<ServiceData>(mProgressDialog){
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取客服电话、客服qq","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(ServiceData result) {
                                if (result != null) {
                                    LogUtil.error("获取客服电话、客服qq","result=="+result.toString());

                                    if (result.getCode()==0){//获取客服电话、客服qq成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取客服电话、客服qq失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
