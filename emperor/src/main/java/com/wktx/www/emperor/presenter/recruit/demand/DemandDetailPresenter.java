package com.wktx.www.emperor.presenter.recruit.demand;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.demand.DemandDetailData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/24.
 * 需求详情界面
 */

public class DemandDetailPresenter extends ABasePresenter<IView> {

    public DemandDetailPresenter() {
    }

    //获取需求详情
    public void onGetDemandInfo(String demandId){
        LogUtil.error("获取需求详情","json===user_id:"+getmMvpView().getUserInfo().getUser_id()
                +"\ntoken:"+getmMvpView().getUserInfo().getToken()+"\nid:"+demandId);

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_DEMAND_INFO)
                .params("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()))
                .params("token", getmMvpView().getUserInfo().getToken())
                .params("id", demandId)
                .execute(new CallBackProxy<CustomApiResult<DemandDetailData>, DemandDetailData>
                        (new ProgressDialogCallBack<DemandDetailData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取需求详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(DemandDetailData result) {
                                if (result != null) {
                                    LogUtil.error("获取需求详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取需求详情成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取需求详情失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
