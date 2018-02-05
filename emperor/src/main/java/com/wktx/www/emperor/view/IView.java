package com.wktx.www.emperor.view;

import com.wktx.www.emperor.apiresult.login.AccountInfoData;
import com.wktx.www.emperor.basemvp.IBaseView;
/**
 * Created by yyj on 2018/1/15.
 * 将重复操作抽取出来，解决MVP代码冗余
 *
 * RecruitFragment
 * DemandActivity
 * DemandDetailActivity
 * ArtistResumeActivity
 */

public interface IView<T> extends IBaseView {

    /**
     * 网络请求前加载progress
     */
//    void showProgress();

    /**
     * 网络请求结束隐藏progress
     */
//    void disimissProgress();

    /**
     * 获取用户的token与user_id
     */
    AccountInfoData getUserInfo();

    /**
     * 请求数据成功
     */
    void onRequestSuccess(T tData);

    /**
     * 请求数据失败
     */
    void onRequestFailure(String result);

    /**
     * 请求数据出错
     */
//    void onRequestError(String result);
}
