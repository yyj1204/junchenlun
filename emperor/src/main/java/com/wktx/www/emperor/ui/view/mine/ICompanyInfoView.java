package com.wktx.www.emperor.ui.view.mine;

import com.wktx.www.emperor.apiresult.mine.companyinfo.CompanyInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/18.
 * 公司信息界面
 */

public interface ICompanyInfoView extends IView<CompanyInfoData> {
    /**
     * 获取头像图片
     */
    String getHeadPic();
    /**
     * 获取昵称
     */
    String getNickName();
    /**
     * 获取公司地址
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
     * 获取公司简介
     */
    String getIntroduce();

}
