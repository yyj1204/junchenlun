package com.wktx.www.subjects.presenter.main;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.main.demand.DemandDetailsData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.main.IPositionDetailsView;
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
 * 职位招聘详情界面
 */

public class PositionDetailsPresenter extends ABasePresenter<IPositionDetailsView> {

    public PositionDetailsPresenter() {

    }

    //获取职位招聘详情
    public void getInfo(String demandId){
        HttpParams httpParams = new HttpParams();
        if (getmMvpView().getUserInfo()!=null){
            httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
            httpParams.put("token", getmMvpView().getUserInfo().getToken());
        }
        httpParams.put("id",demandId);

        LogUtil.error("获取职位招聘详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_POSITION_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<DemandDetailsData>, DemandDetailsData>
                        (new ProgressDialogCallBack<DemandDetailsData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取职位招聘详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(DemandDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取职位招聘详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取职位招聘详情 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取职位招聘详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //取消收藏简历
    public void changeCollectState(String demandId,String isCollect){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("demand_id", demandId);
        httpParams.put("collect", isCollect);//0:取消 1:收藏

        LogUtil.error("取消收藏简历","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_POSITION_COLLECT)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("取消收藏简历","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onCancelCollectResult(false, ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onCancelCollectResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("取消收藏简历","result=="+result.toString());

                                    if (result.getCode()==0){//取消收藏简历 成功
                                        getmMvpView().onCancelCollectResult(true,result.getMsg());
                                    }else {//取消收藏简历 失败
                                        getmMvpView().onCancelCollectResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onCancelCollectResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //投递简历
    public void sendResume(String bossId,String demandId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("uid", bossId);//雇主id
        httpParams.put("did", demandId);

        LogUtil.error("投递简历","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_POSITION_SENDRESUME)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("投递简历","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onSendResumeResult(false, ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onSendResumeResult(false,e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("投递简历","result=="+result.toString());

                                    if (result.getCode()==0){//投递简历 成功
                                        getmMvpView().onSendResumeResult(true,result.getMsg());
                                    }else {//投递简历 失败
                                        getmMvpView().onSendResumeResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onSendResumeResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
