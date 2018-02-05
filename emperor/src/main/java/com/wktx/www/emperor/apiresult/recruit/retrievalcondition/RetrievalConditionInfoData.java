package com.wktx.www.emperor.apiresult.recruit.retrievalcondition;

import java.io.Serializable;

/**
 * Created by yyj on 2018/1/24.
 * 检索条件信息内容
 */

public class RetrievalConditionInfoData implements Serializable{
    private TopBean top;//顶部水平菜单
    private BottomBean bottom;//下面多条件筛选菜单

    public TopBean getTop() {
        return top;
    }

    public void setTop(TopBean top) {
        this.top = top;
    }

    public BottomBean getBottom() {
        return bottom;
    }

    public void setBottom(BottomBean bottom) {
        this.bottom = bottom;
    }


    @Override
    public String toString() {
        return "RetrievalConditionInfoData{" +
                "top=" + top +
                ", bottom=" + bottom +
                '}';
    }
}

