package com.wktx.www.subjects.ui.view.mine;
import com.wktx.www.subjects.ui.view.IView;

/**
 * Created by yyj on 2018/2/13.
 * 账户认证---个人认证（详情）界面
 */

public interface ICertificationView extends IView<String> {

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

    /**
     * 获取图片路径是否成功的回调
     */
    void onGetImgUrlResult(boolean isSuccess, String result);
}
