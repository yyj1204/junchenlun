package com.wktx.www.subjects.presenter.mine.works;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.ImgUploadData;
import com.wktx.www.subjects.apiresult.mine.works.WorksDetailsData;
import com.wktx.www.subjects.apiresult.mine.works.condition.WorksConditionData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.resume.IWorksDetailsView;
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
 * 我的作品---作品详情界面
 */

public class WorksDetailsPresenter extends ABasePresenter<IWorksDetailsView> {

    public WorksDetailsPresenter() {
    }

    //获取作品详情
    public void getInfo(String worksId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("wid", worksId);

        LogUtil.error("获取作品详情","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORKS_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<WorksDetailsData>, WorksDetailsData>
                        (new ProgressDialogCallBack<WorksDetailsData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取作品详情","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(WorksDetailsData result) {
                                if (result != null) {
                                    LogUtil.error("获取作品详情","result=="+result.toString());

                                    if (result.getCode()==0){//获取作品详情 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取作品详情 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取参数信息（类目、设计类型）
    public void getConditionInfo(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORKS_CONDITION)
                .execute(new CallBackProxy<CustomApiResult<WorksConditionData>, WorksConditionData>
                        (new ProgressDialogCallBack<WorksConditionData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取参数信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetConditionFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetConditionFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(WorksConditionData result) {
                                if (result != null) {
                                    LogUtil.error("获取参数信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取参数信息 成功
                                        getmMvpView().onGetConditionSuccess(result.getInfo());
                                    }else {//获取参数信息 失败
                                        getmMvpView().onGetConditionFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetConditionFailure(ConstantUtil.TOAST_ERROR);
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
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_RESUME_BASE64)
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

    //编辑作品（增改）
    public void changeWorks(String resumeId,String worksId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("rid", resumeId);
        //作品id不为空，说明是编辑请求，则需传递作品id
        if (!worksId.equals("")){
            httpParams.put("wid", worksId);
        }
        httpParams.put("title", getmMvpView().getWorksTitle());
        httpParams.put("bgats", getmMvpView().getCategoryIds());
        httpParams.put("design_pattern", getmMvpView().getDesignPatternId());
        httpParams.put("image", getmMvpView().getCoverImgUrl());
        httpParams.put("brief_intro", getmMvpView().getIntro());
        //多张作品图片
        Gson gson = new GsonBuilder().create();
        String worksImgUrlStrs = gson.toJson(getmMvpView().getWorksImgUrls());
        httpParams.put("content", worksImgUrlStrs);

        LogUtil.error("编辑作品","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORKS_EDITINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("编辑作品","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onChangeWorksResult(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onChangeWorksResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("编辑作品","result=="+result.toString());

                                    if (result.getCode()==0){//编辑作品 成功
                                        getmMvpView().onChangeWorksResult(true,result.getMsg());
                                    }else {//编辑作品 失败
                                        getmMvpView().onChangeWorksResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onChangeWorksResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }


    //删除作品
    public void deleteWorks(String resumeId,String worksId){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("rid", resumeId);
        httpParams.put("wid", worksId);

        LogUtil.error("删除作品","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_WORKS_DELETE)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("删除作品","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onChangeWorksResult(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onChangeWorksResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("删除作品","result=="+result.toString());

                                    if (result.getCode()==0){//删除作品 成功
                                        getmMvpView().onChangeWorksResult(true,result.getMsg());
                                    }else {//删除作品 失败
                                        getmMvpView().onChangeWorksResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onChangeWorksResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
