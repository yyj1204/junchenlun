package com.wktx.www.subjects.presenter.manage;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.manage.FireDetailsData;
import com.wktx.www.subjects.apiresult.manage.FireDetailsInfoData;
import com.wktx.www.subjects.apiresult.message.InviteDetailsData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.manage.IFireDetailsView;
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
 * 公司解雇详情界面
 */

public class FireDetailsPresenter extends ABasePresenter<IFireDetailsView> {

    public FireDetailsPresenter() {

    }

    //获取解雇详情
    public void getInfo(String fireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id",fireId);

        LogUtil.error("获取解雇详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_FIRE_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<FireDetailsData>, FireDetailsData>
                        (new ProgressDialogCallBack<FireDetailsData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取解雇详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(FireDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取解雇详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取解雇详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取解雇详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //接受解雇
    public void acceptFire(String fireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", fireId);

        LogUtil.error("接受解雇","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_FIRE_ACCEPT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("接受解雇","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onFireResult(false, ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onFireResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("接受解雇","result=="+result.toString());

                                    if (result.getCode()==0){//接受解雇 成功
                                        getmMvpView().onFireResult(true,result.getMsg());
                                    }else {//接受解雇 失败
                                        getmMvpView().onFireResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onFireResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //拒绝解雇
    public void refuseFire(String fireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", fireId);

        LogUtil.error("拒绝解雇","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_FIRE_REFUSE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("拒绝解雇","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onFireResult(false, ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onFireResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("拒绝解雇","result=="+result.toString());

                                    if (result.getCode()==0){//拒绝解雇 成功
                                        getmMvpView().onFireResult(true,result.getMsg());
                                    }else {//拒绝解雇 失败
                                        getmMvpView().onFireResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onFireResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
