package com.wktx.www.subjects.presenter.mine;
import com.wktx.www.subjects.apiresult.main.position.PositionListData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.IMyCollectView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/3/05.
 * 面试记录界面
 */

public class InterviewRecordPresenter extends ABasePresenter<IMyCollectView> {

    public InterviewRecordPresenter() {
    }

    //获取面试记录
    public void getInfo(int page){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("page",page+"");
        httpParams.put("limit", "10");

        LogUtil.error("获取面试记录","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_INTERVIEW_LIST)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<PositionListData>, PositionListData>
                        (new ProgressDialogCallBack<PositionListData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取面试记录","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(PositionListData result) {
                                if (result != null) {
                                    LogUtil.error("获取面试记录","result=="+result.toString());

                                    if (result.getCode()==0){//请求成功
                                        if (result.getInfo().size()!=0){//获取面试记录 成功
                                            getmMvpView().onRequestSuccess(result.getInfo());
                                        }else {//获取面试记录 失败(无数据)
                                            getmMvpView().onRequestFailure("");
                                        }
                                    }else {//获取面试记录 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
