package com.wktx.www.subjects.ui.view.mine.authent;


import com.wktx.www.subjects.apiresult.mine.authent.AuthentStudioInfoData;
import com.wktx.www.subjects.ui.view.IView;

/**
 * Created by yyj on 2018/2/13.
 * 账户认证---工作室认证界面
 */

public interface IAuthentStudioView extends IView<String> {

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
     * 获取工作室认证详情成功的回调
     */
    void onGetAuthentInfoSuccessResult(AuthentStudioInfoData result);

    /**
     * 获取工作室认证详情失败的回调
     */
    void onGetAuthentInfoFailureResult(String result);
}
