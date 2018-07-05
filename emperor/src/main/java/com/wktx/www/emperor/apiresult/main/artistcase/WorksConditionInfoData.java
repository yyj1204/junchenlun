package com.wktx.www.emperor.apiresult.main.artistcase;

import java.util.List;

/**
 * Created by yyj on 2018/3/30.
 * 首页---美工案例列表界面的检索条件
 *"bgat": [{"id": "0","name": "全部"}]
 *"design_pattern": [{"id": "0","name": "全部"}]
 */

public class WorksConditionInfoData {
    private List<WorksConditionBean> bgat;//类目类型
    private List<WorksConditionBean> design_pattern;//设计类型

    public List<WorksConditionBean> getBgat() {
        return bgat;
    }

    public void setBgat(List<WorksConditionBean> bgat) {
        this.bgat = bgat;
    }

    public List<WorksConditionBean> getDesign_pattern() {
        return design_pattern;
    }

    public void setDesign_pattern(List<WorksConditionBean> design_pattern) {
        this.design_pattern = design_pattern;
    }


    @Override
    public String toString() {
        return "WorksConditionInfoData{" +
                "bgat=" + bgat +
                ", design_pattern=" + design_pattern +
                '}';
    }
}
