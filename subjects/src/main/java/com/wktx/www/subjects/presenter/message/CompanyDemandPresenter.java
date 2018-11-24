package com.wktx.www.subjects.presenter.message;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.message.CompanyDemandListData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 对我感兴趣公司---公司招聘需求列表
 */

public class CompanyDemandPresenter extends ABasePresenter<IView> {

    public CompanyDemandPresenter() {
    }

    //获取公司招聘需求列表
    public void getInfo(String companyId, int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("company_id", companyId);
        httpParams.put("page", page+"");
        httpParams.put("pageSize", "10");
        LogUtil.error("获取公司招聘需求列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_COMPANY_DEMANDLIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CompanyDemandListData>, CompanyDemandListData>
                        (new SimpleCallBack<CompanyDemandListData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取公司招聘需求列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CompanyDemandListData result) {
                                if (result != null) {
                                    LogUtil.error("获取公司招聘需求列表","result=="+result.toString());

                                    if (result.getCode()==0){//请求成功
                                        if (result.getInfo().size()!=0){//获取公司招聘需求列表 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                        }else {//获取公司招聘需求列表 失败（没数据）
                                            getmMvpView().onRequestFailure("");
                                        }
                                    }else {//获取公司招聘需求列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
