package com.wktx.www.emperor.view.main;

import com.wktx.www.emperor.apiresult.recruit.recruitlist.RecruitListInfoData;
import com.wktx.www.emperor.view.IView;

import java.util.List;

/**
 * Created by yyj on 2018/1/15.
 * 搜索界面
 */

public interface ISearchView extends IView<List<RecruitListInfoData>> {

    /**
     * 获取输入搜索关键字
     */
    String getKeyStrStr();

}
