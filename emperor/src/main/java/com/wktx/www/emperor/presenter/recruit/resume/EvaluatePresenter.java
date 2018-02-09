package com.wktx.www.emperor.presenter.recruit.resume;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.recruit.resume.EvaluateData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

/**
 * Created by yyj on 2018/1/24.
 * 美工简历---评价列表
 */

public class EvaluatePresenter extends ABasePresenter<IView> {

    public EvaluatePresenter() {
    }

    //获取评价列表
    public void onGetRevaluateList(String resumeId,int page){
        LogUtil.error("获取评价列表","json===resumeId:"+resumeId +"\npage:"+page);

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_EVALUATE)
                .params("id", resumeId)
                .params("page", page+"")
                .params("limit", "10")
                .execute(new CallBackProxy<CustomApiResult<EvaluateData>, EvaluateData>
                        (new SimpleCallBack<EvaluateData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取评价列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(EvaluateData result) {
                                if (result != null) {
                                    LogUtil.error("获取评价列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取评价列表成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取评价列表失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取评价列表失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
