package com.wktx.www.emperor.ui.view.mine.authent;

import com.wktx.www.emperor.apiresult.mine.authent.AuthentPersonalInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/2/13.
 * 账户认证---个人认证界面
 */

public interface IAuthentPersonalView extends IView<String> {

    /**
     * 获取输入真实姓名
     */
    String getNameStr();

    /**
     * 获取输入身份证号
     */
    String getIdNumberStr();

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
     * 获取手持身份证正面照片
     */
    String getPositivePhotoStr();

    /**
     * 获取手持身份证反面照片
     */
    String getReversePhotoStr();

    /**
     * 获取个人认证详情成功的回调
     */
    void onGetAuthentInfoSuccessResult(AuthentPersonalInfoData result);

    /**
     * 获取个人认证详情失败的回调
     */
    void onGetAuthentInfoFailureResult(String result);
}
