package com.wktx.www.subjects.presenter.mine.resume;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
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

import java.util.ArrayList;

/**
 * Created by yyj on 2018/1/15.
 * 我的简历---个人标签界面
 */

public class EditTagsPresenter extends ABasePresenter<IView> {

    public EditTagsPresenter() {
    }

    //编辑个人标签（增删改）
    public void changeTags(String resumeId,ArrayList<String> tagsBeans){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", resumeId);
        //个人标签
        Gson gson = new GsonBuilder().create();
        String tagsStrs = gson.toJson(tagsBeans);
        httpParams.put("tags", tagsStrs);

        LogUtil.error("编辑个人标签","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_TAGS)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("编辑个人标签","e=="+e.getMessage());

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
                                    LogUtil.error("编辑个人标签","result=="+result.toString());

                                    if (result.getCode()==0){//编辑个人标签 成功
                                        getmMvpView().onRequestSuccess(result.getMsg());
                                    }else {//编辑个人标签 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
