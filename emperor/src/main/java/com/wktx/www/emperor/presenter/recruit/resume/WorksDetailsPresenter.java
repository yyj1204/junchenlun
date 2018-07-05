package com.wktx.www.emperor.presenter.recruit.resume;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.resume.WorksDetailsData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/24.
 * 作品详情界面
 */

public class WorksDetailsPresenter extends ABasePresenter<IView> {

    public WorksDetailsPresenter() {
    }

    //获取作品详情
    public void onGetWorksInfo(String worksId){
        LogUtil.error("获取作品详情","json===worksId:"+worksId);

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_WORKS_INFO)
                .params("id", worksId)
                .execute(new CallBackProxy<CustomApiResult<WorksDetailsData>, WorksDetailsData>
                        (new ProgressDialogCallBack<WorksDetailsData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取作品详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(WorksDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取作品详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取作品详情成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取作品详情失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
