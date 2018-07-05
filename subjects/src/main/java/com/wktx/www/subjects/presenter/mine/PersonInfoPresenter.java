package com.wktx.www.subjects.presenter.mine;
import com.wktx.www.subjects.apiresult.CommonSimpleData;
import com.wktx.www.subjects.apiresult.CustomApiResult;
import com.wktx.www.subjects.apiresult.ImgUploadData;
import com.wktx.www.subjects.apiresult.mine.personinfo.PersonConditionData;
import com.wktx.www.subjects.apiresult.mine.personinfo.PersonData;
import com.wktx.www.subjects.basemvp.ABasePresenter;
import com.wktx.www.subjects.ui.view.mine.IPersonInfoView;
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
 * 个人信息界面
 */

public class PersonInfoPresenter extends ABasePresenter<IPersonInfoView> {

    public PersonInfoPresenter() {
    }

    //获取个人信息
    public void getInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());

        LogUtil.error("获取个人信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_PERSON_INFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<PersonData>, PersonData>
                        (new ProgressDialogCallBack<PersonData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取个人信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(PersonData result) {
                                if (result != null) {
                                    LogUtil.error("获取个人信息","result=="+result.toString());

                                    if (result.getCode()==0){//获取个人信息 成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取个人信息 失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }

    //获取个人信息参数(学历)
    public void getConditionInfo(){
        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_PERSON_CONDITION)
                .execute(new CallBackProxy<CustomApiResult<PersonConditionData>, PersonConditionData>
                        (new ProgressDialogCallBack<PersonConditionData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("获取个人信息参数","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onGetConditionFailureResult(e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(PersonConditionData result) {
                                if (result != null) {
                                    LogUtil.error("获取个人信息参数","result=="+result.toString());

                                    if (result.getCode()==0){//获取个人信息参数 成功
                                        getmMvpView().onGetConditionSuccessResult(result.getInfo());
                                    }else {//获取个人信息参数 失败
                                        getmMvpView().onGetConditionFailureResult(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onGetConditionFailureResult(ConstantUtil.TOAST_ERROR);
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
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HEAD_BASE64)
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

    //修改个人信息
    public void changePersonInfo(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
        httpParams.put("token", getmMvpView().getUserInfo().getToken());
        httpParams.put("picture", getmMvpView().getHeadPic());
        httpParams.put("name", getmMvpView().getNickName());
        httpParams.put("sex", getmMvpView().getSex());
        httpParams.put("highest_education", getmMvpView().getHightEducation());
        httpParams.put("date_of_birth", getmMvpView().getBirth());
        httpParams.put("residential_city", getmMvpView().getAddress());
        httpParams.put("phone", getmMvpView().getPhone());
        httpParams.put("qq", getmMvpView().getQQ());
        httpParams.put("wechat", getmMvpView().getWechat());
        httpParams.put("character_introduction", getmMvpView().getCharacter());

        LogUtil.error("修改个人信息","json==="+httpParams.toString());

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_PERSON_EDITINFO)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<CommonSimpleData>, CommonSimpleData>
                        (new ProgressDialogCallBack<CommonSimpleData>(mProgressDialog) {
                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                LogUtil.error("修改个人信息","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onChangePersonInfoResult(false,ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onChangePersonInfoResult(false,e.getMessage());
                                }
                            }

                            @Override
                            public void onSuccess(CommonSimpleData result) {
                                if (result != null) {
                                    LogUtil.error("修改个人信息","result=="+result.toString());

                                    if (result.getCode()==0){//修改个人信息 成功
                                        getmMvpView().onChangePersonInfoResult(true,result.getMsg());
                                    }else {//修改个人信息 失败
                                        getmMvpView().onChangePersonInfoResult(false,result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onChangePersonInfoResult(false,ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
