package com.wktx.www.subjects.presenter.message;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.message.InviteDetailsData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.message.IInviteDetailsView;
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
 * 公司邀请详情界面
 */

public class InviteDetailsPresenter extends ABasePresenter<IInviteDetailsView> {

    public InviteDetailsPresenter() {

    }

    //获取邀请详情
    public void getInfo(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hid",hireId);

        LogUtil.error("获取邀请详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_INVITE_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<InviteDetailsData>, InviteDetailsData>
                        (new ProgressDialogCallBack<InviteDetailsData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取邀请详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(InviteDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取邀请详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取邀请详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取邀请详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //接受邀请
    public void acceptInvite(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hid", hireId);

        LogUtil.error("接受邀请","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_INVITE_ACCEPT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("接受邀请","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onInviteResult(false, ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onInviteResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("接受邀请","result=="+result.toString());

                                    if (result.getCode()==0){//接受邀请 成功
                                        getmMvpView().onInviteResult(true,result.getMsg());
                                    }else {//接受邀请 失败
                                        getmMvpView().onInviteResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onInviteResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //拒绝邀请
    public void refuseInvite(String hireId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("hid", hireId);

        LogUtil.error("拒绝邀请","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_INVITE_REFUSE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("拒绝邀请","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onInviteResult(false, ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onInviteResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("拒绝邀请","result=="+result.toString());

                                    if (result.getCode()==0){//拒绝邀请 成功
                                        getmMvpView().onInviteResult(true,result.getMsg());
                                    }else {//拒绝邀请 失败
                                        getmMvpView().onInviteResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onInviteResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
