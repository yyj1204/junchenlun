package com.wktx.www.subjects.presenter.manage;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.ImgUploadData;
import com.wktx.www.subjects.apiresult.manage.SaleroomData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.manage.ISaleroomView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 我的工作---销售额界面
 */

public class SaleroomPresenter extends ABasePresenter<ISaleroomView> {

    public SaleroomPresenter() {
    }

    //获取销售额详情
    public void getInfo(String salesId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", salesId);

        LogUtil.error("获取销售额详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_SALEROOM_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<SaleroomData>, SaleroomData>
                        (new ProgressDialogCallBack<SaleroomData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取销售额详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(SaleroomData result) {
                                if (result != null) {
                                    LogUtil.error("获取销售额详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取销售额详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取销售额详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //上传图片Base64
    public void getImgUrl(String base64Str){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("base64", base64Str);

        LogUtil.error("上传图片Base64","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_SALEROOM_BASE64)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ImgUploadData>, ImgUploadData>
                        (new ProgressDialogCallBack<ImgUploadData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("上传图片Base64","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetImgUrlResult(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetImgUrlResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(ImgUploadData result) {
                                if (result != null) {
                                    LogUtil.error("上传图片Base64","result=="+result.toString());

                                    if (result.getCode()==0){//上传图片Base64 成功
                                        getmMvpView().onGetImgUrlResult(true,result.getInfo());
                                    }else {//上传图片Base64 失败
                                        getmMvpView().onGetImgUrlResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetImgUrlResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //编辑销售额（增改）
    public void changeSales(String hireId,String salesId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hid", hireId);
        //销售额id不为0，说明是编辑请求，则需传递销售额id
        if (!salesId.equals("0")){
            httpParams.put("id", salesId);
        }
        httpParams.put("total_sales", getmMvpView().getTotlaSales());
        httpParams.put("last_month_sales", getmMvpView().getUltSales());
        httpParams.put("last_sales_data", getmMvpView().getSalesImgUrl());

        LogUtil.error("编辑销售额","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_SALEROOM_EDITINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("编辑销售额","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onChangeSalesResult(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onChangeSalesResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("编辑销售额","result=="+result.toString());

                                    if (result.getCode()==0){//编辑销售额 成功
                                        getmMvpView().onChangeSalesResult(true,result.getMsg());
                                    }else {//编辑销售额 失败
                                        getmMvpView().onChangeSalesResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onChangeSalesResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
