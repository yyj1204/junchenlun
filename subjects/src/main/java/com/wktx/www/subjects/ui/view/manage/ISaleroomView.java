package com.wktx.www.subjects.ui.view.manage;
import com.wktx.www.subjects.apiresult.manage.SaleroomInfoData;
import com.wktx.www.subjects.ui.view.IView;


/**
 * Created by yyj on 2018/1/15.
 * 我的工作 --- 销售额详情(增改)
 */

public interface ISaleroomView extends IView<SaleroomInfoData> {

    /**
     * 获取累计销售总额
     */
    String getTotlaSales();

    /**
     * 获取上个月销售额
     */
    String getUltSales();

    /**
     * 获取销售额数据图片url
     */
    String getSalesImgUrl();

    /**
     * 获取图片路径是否成功的回调
     */
    void onGetImgUrlResult(boolean isSuccess, String result);

    /**
     * 获取编辑销售额是否成功的回调
     */
    void onChangeSalesResult(boolean isSuccess, String result);
}
