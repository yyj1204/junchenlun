package com.wktx.www.subjects.presenter.main;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.main.CompanyListData;
import com.wktx.www.subjects.apiresult.main.demand.DemandListData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.main.ISearchView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 搜索界面
 */

public class SearchPresenter extends ABasePresenter<ISearchView> {

    public SearchPresenter() {
    }

    //获取搜索职位招聘列表
    public void getPositionInfo(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("keyword",getmMvpView().getKeyStrStr());
        httpParams.put("page", page+"");
        httpParams.put("pageSize", "10");

        LogUtil.error("获取搜索职位招聘列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_POSITION_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<DemandListData>, DemandListData>
                        (new ProgressDialogCallBack<DemandListData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取搜索职位招聘列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(DemandListData result) {
                                if (result != null) {
                                    LogUtil.error("获取搜索职位招聘列表","result=="+result.toString());

                                    if (result.getCode()==0){//请求 成功
                                        if (result.getInfo().size()!=0){//获取职位招聘列表 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                        }else {//获取职位招聘列表 失败(无数据)
                                            getmMvpView().onRequestFailure("");
                                        }
                                    }else {//获取职位招聘列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
    //获取搜索公司列表
    public void getCompanyInfo(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("keyword",getmMvpView().getKeyStrStr());
        httpParams.put("page", page+"");
        httpParams.put("pageSize", "10");

        LogUtil.error("获取搜索公司列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_COMPANY_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CompanyListData>, CompanyListData>
                        (new ProgressDialogCallBack<CompanyListData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取搜索公司列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetCompanyFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetCompanyFailureResult(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CompanyListData result) {
                                if (result != null) {
                                    LogUtil.error("获取搜索公司列表","result=="+result.toString());

                                    if (result.getCode()==0){//请求 成功
                                        if (result.getInfo().size()!=0){//获取搜索公司列表 成功
                                            getmMvpView().onGetCompanySuccessResult(result.getInfo());
                                        }else {//获取搜索公司列表 失败(无数据)
                                            getmMvpView().onGetCompanyFailureResult("");
                                        }
                                    }else {//获取搜索公司列表 失败
                                        getmMvpView().onGetCompanyFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetCompanyFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
