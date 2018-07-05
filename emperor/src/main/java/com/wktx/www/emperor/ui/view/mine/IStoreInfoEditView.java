package com.wktx.www.emperor.ui.view.mine;

import com.wktx.www.emperor.apiresult.mine.store.StoreConditionInfoData;
import com.wktx.www.emperor.apiresult.mine.store.StoreInfoData;
import com.wktx.www.emperor.ui.view.IView;

/**
 * Created by yyj on 2018/1/26.
 * 编辑（添加）店铺信息界面
 */

public interface IStoreInfoEditView extends IView<StoreInfoData> {

    /**
     * 获取店铺头像图片
     */
    String getLogoPic();
    /**
     * 获取输入店铺名字
     */
    String getStoreName();

    /**
     * 获取输入店铺网址
     */
    String getStoreUrl();

    /**
     * 获取编辑店铺信息选择框信息成功回调
     */
    void onConditionSuccessResult(StoreConditionInfoData result);

    /**
     * 获取编辑店铺信息选择框信息失败回调
     */
    void onConditionFailureResult(String msg);

    /**
     * 获取店铺编辑（添加）是否成功的回调
     */
    void onStoreInfoEditResult(boolean isSuccess, String msg);

}
