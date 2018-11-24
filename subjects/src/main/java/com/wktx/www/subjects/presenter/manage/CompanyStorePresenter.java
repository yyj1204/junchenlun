package com.wktx.www.subjects.presenter.manage;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.manage.companystore.StoreListData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.IView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 公司店铺列表界面
 */

public class CompanyStorePresenter extends ABasePresenter<IView> {

    public CompanyStorePresenter() {
    }

    //获取公司店铺列表
    public void getInfo(String companyId,int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("company_id", companyId);
        httpParams.put("page", page+"");
        httpParams.put("pageSize", "10");

        LogUtil.error("获取公司店铺列表","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_COMPANY_STORELIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<StoreListData>, StoreListData>
                        (new ProgressDialogCallBack<StoreListData>(mProgressDialog){
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取公司店铺列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(StoreListData result) {
                                if (result != null) {
                                    LogUtil.error("获取公司店铺列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取公司店铺列表 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else if (result.getCode()==1){//获取公司店铺列表 失败(无数据)
                                        getmMvpView().onRequestFailure("");
                                    }else {//获取公司店铺列表 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
