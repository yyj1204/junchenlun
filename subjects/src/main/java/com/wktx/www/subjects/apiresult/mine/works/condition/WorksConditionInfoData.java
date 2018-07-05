package com.wktx.www.subjects.apiresult.mine.works.condition;


import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;

import java.util.ArrayList;

/**
 * Created by 369 on 2018/6/1.
 * 我的作品-参数（类目、设计类型）内容
 */

public class WorksConditionInfoData {
    private ArrayList<ConditionBean> bgatList;//擅长类目
    private ArrayList<ConditionBean> designPatternList;//设计类型


    public ArrayList<ConditionBean> getBgatList() {
        return bgatList;
    }

    public void setBgatList(ArrayList<ConditionBean> bgatList) {
        this.bgatList = bgatList;
    }

    public ArrayList<ConditionBean> getDesignPatternList() {
        return designPatternList;
    }

    public void setDesignPatternList(ArrayList<ConditionBean> designPatternList) {
        this.designPatternList = designPatternList;
    }

    @Override
    public String toString() {
        return "WorksConditionInfoData{" +
                "designPatternList=" + designPatternList +
                ", bgatList=" + bgatList +
                '}';
    }
}
