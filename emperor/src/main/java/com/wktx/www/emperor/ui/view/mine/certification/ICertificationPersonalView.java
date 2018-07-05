package com.wktx.www.emperor.ui.view.mine.certification;

import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/2/13.
 * 账户认证---个人认证界面
 */

public interface ICertificationPersonalView extends IView<String> {

    /**
     * 获取输入真实姓名
     */
    String getNameStr();

    /**
     * 获取输入身份证号
     */
    String getIdNumberStr();

    /**
     * 获取手持身份证正面照片
     */
    String getPositivePhotoStr();

    /**
     * 获取手持身份证反面照片
     */
    String getReversePhotoStr();

}
