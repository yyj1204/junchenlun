package com.wktx.www.emperor.presenter.recruit.recruit;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.RetrievalConditionData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/24.
 * 招聘界面
 */

public class RecruitPresenter extends ABasePresenter<IView> {

    public RecruitPresenter() {
    }

    //获取招聘检索条件信息
    public void onGetRetrievalConditionInfo(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RETRIEVAL_CONDITION)
                .execute(new CallBackProxy<CustomApiResult<RetrievalConditionData>, RetrievalConditionData>
                        (new SimpleCallBack<RetrievalConditionData>() {
                    @Override
                    public void onError(ApiException e) {
                        LogUtil.error("获取招聘检索条件信息","e=="+e.getMessage());

                        if (e.getMessage().equals("无法解析该域名")){
                            getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                        }else {
                            getmMvpView().onRequestFailure(e.getMessage());
                        }
                    }
                    @Override
                    public void onSuccess(RetrievalConditionData result) {
                        if (result != null) {
                            LogUtil.error("获取招聘检索条件信息","result=="+result.toString());

                            if (result.getCode()==0){//获取招聘检索条件信息成功
                                getmMvpView().onRequestSuccess(result.getInfo());
                            }else {//获取招聘检索条件信息失败
                                getmMvpView().onRequestFailure(result.getMsg());
                            }
                        }else {
                            getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                        }
                    }
                }) {});
    }
}
