package com.wktx.www.emperor.presenter.staff;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wktx.www.emperor.apiresult.CommonSimpleData;
import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.ImgUploadData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.ui.view.staff.IStaffEvaluateView;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/15.
 * 管理我的员工---评价员工界面
 */

public class StaffEvaluatePresenter extends ABasePresenter<IStaffEvaluateView> {

    public StaffEvaluatePresenter() {
    }

    //上传图片Base64
    public void getImgUrl(String base64Str){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("base64", base64Str);

        LogUtil.error("上传图片Base64","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_EVALUATE_BASE64)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ImgUploadData>, ImgUploadData>
                        (new ProgressDialogCallBack<ImgUploadData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("上传图片Base64","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetImgUrlResult(false, ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
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

    //评价员工
    public void onEvaluateStaff(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hire_id", hireId);
        httpParams.put("service_attitude", getmMvpView().getAttitude());
        httpParams.put("ability", getmMvpView().getAbility());
        httpParams.put("response_speed", getmMvpView().getSpeed());
        httpParams.put("evaluation_content", getmMvpView().getEvaluateContent());
        //多张评价图片
        Gson gson = new GsonBuilder().create();
        String dataImgUrlStrs = gson.toJson(getmMvpView().getEvaluateImgUrls());
        httpParams.put("images", dataImgUrlStrs);

        LogUtil.error("评价员工","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_MANAGE_EVALUATE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("评价员工","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("评价员工","result=="+result.toString());

                                    if (result.getCode()==0){//评价员工 成功
                                        getmMvpView().onRequestSuccess(result.getMsg());
                                    }else {//评价员工 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
