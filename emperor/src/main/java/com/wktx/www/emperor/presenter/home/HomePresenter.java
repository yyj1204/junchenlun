package com.wktx.www.emperor.presenter.home;

import com.wktx.www.emperor.apiresult.CustomApiResult;
import com.wktx.www.emperor.apiresult.home.home.HomeData;
import com.wktx.www.emperor.basemvp.ABasePresenter;
import com.wktx.www.emperor.utils.ApiURL;
import com.wktx.www.emperor.utils.ConstantUtil;
import com.wktx.www.emperor.utils.LogUtil;
import com.wktx.www.emperor.view.IView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;

/**
 * Created by yyj on 2018/1/24.
 * 首页
 */

public class HomePresenter extends ABasePresenter<IView> {

    public HomePresenter() {
    }

    //获取首页职位列表(轮播图)
    public void onGetHomeInfo(int page){
        HttpParams httpParams = new HttpParams();
        if (getmMvpView().getUserInfo()!=null){
            LogUtil.error("获取首页职位列表","json===user_id:"+getmMvpView().getUserInfo().getUser_id()
                    +"\ntoken:"+getmMvpView().getUserInfo().getToken()+"\npage:"+page);

            httpParams.put("user_id", String.valueOf(getmMvpView().getUserInfo().getUser_id()));
            httpParams.put("token", getmMvpView().getUserInfo().getToken());
        }else {
            LogUtil.error("获取首页职位列表","json===page:"+page);
        }

        httpParams.put("page", page+"");
        httpParams.put("limit", "10");

        EasyHttp.post(ApiURL.COMMON_URL)
                .params(ApiURL.PARAMS_KEY,ApiURL.PARAMS_HOME)
                .params(httpParams)
                .execute(new CallBackProxy<CustomApiResult<HomeData>, HomeData>
                        (new SimpleCallBack<HomeData>() {
                            @Override
                            public void onError(ApiException e) {
                                LogUtil.error("获取首页职位列表","e=="+e.getMessage());

                                if (e.getMessage().equals("无法解析该域名")){
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_NONET);
                                }else {
                                    getmMvpView().onRequestFailure(e.getMessage());
                                }
                            }
                            @Override
                            public void onSuccess(HomeData result) {
                                if (result != null) {
                                    LogUtil.error("获取首页职位列表","result=="+result.toString());

                                    if (result.getCode()==0){//获取首页职位列表成功
                                        getmMvpView().onRequestSuccess(result.getInfo());
                                    }else {//获取首页职位列表失败
                                        getmMvpView().onRequestFailure(result.getMsg());
                                    }
                                }else {
                                    getmMvpView().onRequestFailure(ConstantUtil.TOAST_ERROR);
                                }
                            }
                        }) {});
    }
}
