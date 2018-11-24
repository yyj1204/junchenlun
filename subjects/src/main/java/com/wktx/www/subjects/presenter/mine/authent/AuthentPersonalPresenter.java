package com.wktx.www.subjects.presenter.mine.authent;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.mine.authent.AuthentPersonalData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.authent.IAuthentPersonalView;
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
 * 账户认证（个人）
 */

public class AuthentPersonalPresenter extends ABasePresenter<IAuthentPersonalView> {

    public AuthentPersonalPresenter() {
    }

    //账户认证（个人）
    public void onCertification(String authentId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        //如果认证id不为空，即为重新认证
        if (!authentId.equals("")){
            httpParams.put("id", authentId);
        }
        httpParams.put("type", "1");//1:个人认证 2:工作室认证
        httpParams.put("name", getmMvpView().getNameStr());
        httpParams.put("id_card", getmMvpView().getIdNumberStr());
        httpParams.put("sp_name", getmMvpView().getOnlineStoreNameStr());
        httpParams.put("sp_url", getmMvpView().getOnlineStoreLinkStr());
        httpParams.put("tb_asct_id", getmMvpView().getTaobaoIDStr());
        httpParams.put("front_pic", getmMvpView().getPositivePhotoStr());
        httpParams.put("back_pic", getmMvpView().getReversePhotoStr());
        LogUtil.error("账户认证（个人)","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CERTIFICATION)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("账户认证（个人）","e=="+e.getMessage());

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
                                    LogUtil.error("账户认证（个人）","result=="+result.toString());

                                    if (result.getCode()==0){//账户认证（个人）成功
                                        getmMvpView().onRequestSuccess("提交成功！请等待审核...");
                                    }else {//账户认证（个人）失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //获取认证详情（个人）
    public void getCertificationInfo(String authentId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("type", "1");
        httpParams.put("id", authentId);

        LogUtil.error("获取认证详情（个人)","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_CERTIFICATION_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<AuthentPersonalData>, AuthentPersonalData>
                        (new ProgressDialogCallBack<AuthentPersonalData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取认证详情（个人）","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetAuthentInfoFailureResult(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onGetAuthentInfoFailureResult(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(AuthentPersonalData result) {
                                if (result != null) {
                                    LogUtil.error("获取认证详情（个人）","result=="+result.toString());

                                    if (result.getCode()==0){//获取认证详情（个人）成功
                                        getmMvpView().onGetAuthentInfoSuccessResult(result.getInfo());
                                    }else {//获取认证详情（个人）失败
                                        getmMvpView().onGetAuthentInfoFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetAuthentInfoFailureResult(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

}
