package com.wktx.www.subjects.ui.view.mine;

import com.wktx.www.subjects.apiresult.mine.personinfo.PersonConditionInfoData;
import com.wktx.www.subjects.apiresult.mine.personinfo.PersonInfoData;
import com.wktx.www.subjects.ui.view.IView;

/**
 * Created by yyj on 2018/1/18.
 * 个人信息界面
 */

public interface IPersonInfoView extends IView<PersonInfoData> {
    /**
     * 获取头像图片
     */
    String getHeadPic();
    /**
     * 获取昵称
     */
    String getNickName();
    /**
     * 获取性别
     */
    String getSex();
    /**
     * 获取最高学历
     */
    String getHightEducation();
    /**
     * 获取出生年月
     */
    String getBirth();
    /**
     * 获取所在城市
     */
    String getAddress();
    /**
     * 获取电话
     */
    String getPhone();
    /**
     * 获取qq
     */
    String getQQ();
    /**
     * 获取微信
     */
    String getWechat();
    /**
     * 获取性格
     */
    String getCharacter();

    /**
     * 获取列表检索条件成功的回调
     */
    void onGetConditionSuccessResult(PersonConditionInfoData result);

    /**
     * 获取列表检索条件失败的回调
     */
    void onGetConditionFailureResult(String result);

    /**
     * 获取图片路径是否成功的回调
     */
    void onGetImgUrlResult(boolean isSuccess, String result);

    /**
     * 获取编辑个人信息是否成功的回调
     */
    void onChangePersonInfoResult(boolean isSuccess, String result);
}
