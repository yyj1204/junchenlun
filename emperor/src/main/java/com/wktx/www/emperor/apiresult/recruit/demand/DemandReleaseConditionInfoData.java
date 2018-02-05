package com.wktx.www.emperor.apiresult.recruit.demand;

import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.Bean;

import java.util.ArrayList;

/**
 * Created by yyj on 2018/1/26.
 * 需求发布需要的选择参数内容 （店铺、平台、类目、需求模式）
 */

public class DemandReleaseConditionInfoData {
    private ArrayList<Bean> bgap;//平台
    private ArrayList<Bean> bgat;//类目
    private ArrayList<Bean> design_pattern;//需求模式

    public ArrayList<Bean> getBgap() {
        return bgap;
    }

    public void setBgap(ArrayList<Bean> bgap) {
        this.bgap = bgap;
    }

    public ArrayList<Bean> getBgat() {
        return bgat;
    }

    public void setBgat(ArrayList<Bean> bgat) {
        this.bgat = bgat;
    }

    public ArrayList<Bean> getDesign_pattern() {
        return design_pattern;
    }

    public void setDesign_pattern(ArrayList<Bean> design_pattern) {
        this.design_pattern = design_pattern;
    }

    @Override
    public String toString() {
        return "DemandReleaseConditionInfoData{" +
                "bgap=" + bgap +
                ", bgat=" + bgat +
                ", design_pattern=" + design_pattern +
                '}';
    }
}
