package com.wktx.www.subjects.ui.view.main;
import com.wktx.www.subjects.apiresult.main.CompanyListInfoData;
import com.wktx.www.subjects.apiresult.main.position.PositionListInfoData;
import com.wktx.www.subjects.ui.view.IView;

import java.util.List;


/**
 * Created by yyj on 2018/1/15.
 * 搜索界面
 */

public interface ISearchView extends IView<List<PositionListInfoData>> {

    /**
     * 获取输入搜索关键字
     */
    String getKeyStrStr();

    /**
     * 获取公司搜索列表成功的回调
     */
    void onGetCompanySuccessResult(List<CompanyListInfoData> result);

    /**
     * 获取公司搜索列表失败的回调
     */
    void onGetCompanyFailureResult(String result);
}
