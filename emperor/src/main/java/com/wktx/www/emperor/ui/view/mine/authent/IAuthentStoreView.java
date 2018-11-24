package com.wktx.www.emperor.ui.view.mine.authent;

import com.wktx.www.emperor.apiresult.mine.authent.AuthentStoreInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/2/13.
 * 账户认证---店铺认证界面
 */

public interface IAuthentStoreView extends IView<String> {

    /**
     * 获取输入公司名称
     */
    String getCompanyNameStr();

    /**
     * 获取输入信用代码
     */
    String getCreditCodeStr();

    /**
     * 获取输入网店名称
     */
    String getOnlineStoreNameStr();

    /**
     * 获取输入网店地址
     */
    String getOnlineStoreLinkStr();

    /**
     * 获取输入淘宝主账号ID
     */
    String getTaobaoIDStr();

    /**
     * 获取法人手持身份证正面照片
     */
    String getPositivePhotoStr();

    /**
     * 获取营业执照副本照片
     */
    String getBusinessLicensePhotoStr();

    /**
     * 获取店铺认证详情成功的回调
     */
    void onGetAuthentInfoSuccessResult(AuthentStoreInfoData result);

    /**
     * 获取店铺认证详情失败的回调
     */
    void onGetAuthentInfoFailureResult(String result);
}
