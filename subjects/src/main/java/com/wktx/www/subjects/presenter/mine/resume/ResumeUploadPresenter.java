package com.wktx.www.subjects.presenter.mine.resume;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.ImgUploadData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.resume.IResumeUploadView;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.utils.LogUtil;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/6/6.
 * 我的简历---上传个性简历界面
 */

public class ResumeUploadPresenter extends ABasePresenter<IResumeUploadView> {

    public ResumeUploadPresenter() {
    }

    //上传图片Base64
    public void getInfo(String base64Str){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("base64", base64Str);

        LogUtil.error("上传图片Base64","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_BASE64)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<ImgUploadData>, ImgUploadData>
                        (new ProgressDialogCallBack<ImgUploadData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("上传图片Base64","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(ImgUploadData result) {
                                if (result != null) {
                                    LogUtil.error("上传图片Base64","result=="+result.toString());

                                    if (result.getCode()==0){//上传图片Base64 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//上传图片Base64 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //修改个性简历图片
    public void changeImgUrl(String resumeId,String imgUrl){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id",  getmMvpView().getUserInfo().getUser_id());
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("id", resumeId);
        httpParams.put("resume_content", imgUrl);

        LogUtil.error("修改个性简历图片","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_RESUMEIMG)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("修改个性简历图片","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onChangeResumeImgResult(false,ConstantUtil.TOAST_NONET);
                                }else if (e.getMessage().equals("非法请求：登录信息过期")||e.getMessage().equals("非法请求：未登录")){
                                    getmMvpView().onLoginFailure(e.getMessage());
                                }else {
                                    getmMvpView().onChangeResumeImgResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("修改个性简历图片","result=="+result.toString());

                                    if (result.getCode()==0){//修改个性简历图片 成功
                                        getmMvpView().onChangeResumeImgResult(true,result.getMsg());
                                    }else {//修改个性简历图片 失败
                                        getmMvpView().onChangeResumeImgResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onChangeResumeImgResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
