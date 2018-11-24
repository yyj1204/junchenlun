package com.wktx.www.emperor.presenter.mine;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.mine.companyinfo.CompanyData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.ui.view.mine.ICompanyInfoView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 公司信息界面
 */

public class CompanyInfoPresenter extends ABasePresenter<ICompanyInfoView> {

    public CompanyInfoPresenter() {
    }

    //获取公司信息
    public void onGetCompanyInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());

        LogUtil.error("获取公司信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_COMPANY_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CompanyData>, CompanyData>
                        (new ProgressDialogCallBack<CompanyData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取公司信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CompanyData result) {
                                if (result != null) {
                                    LogUtil.error("获取公司信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取公司信息 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取公司信息 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //修改公司信息
    public void onEditCompanyInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        if (!getmMvpView().getHeadPic().equals("")){
            httpParams.put("head_pic", getmMvpView().getHeadPic());
        }
        httpParams.put("nickname", getmMvpView().getNickName());
        httpParams.put("address", getmMvpView().getAddress());
        httpParams.put("phone", getmMvpView().getPhone());
        httpParams.put("qq", getmMvpView().getQQ());
        httpParams.put("weixin", getmMvpView().getWechat());
        httpParams.put("remark", getmMvpView().getIntroduce());
//        httpParams.put("sex", getmMvpView().getUserInfo().getToken());

        LogUtil.error("修改公司信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_COMPANY_EDITINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CompanyData>, CompanyData>
                        (new ProgressDialogCallBack<CompanyData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("修改公司信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CompanyData result) {
                                if (result != null) {
                                    LogUtil.error("修改公司信息","result=="+result.toString());

                                    if (result.getCode()==0){//修改公司信息 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//修改公司信息 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
