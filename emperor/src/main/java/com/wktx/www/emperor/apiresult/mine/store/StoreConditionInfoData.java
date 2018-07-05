package com.wktx.www.emperor.apiresult.mine.store;

import java.util.ArrayList;
/**
 * Created by yyj on 2018/3/9.
 * 个人中心---需要的选择参数内容（平台、类目）
 *"bgat": [{"id": "0","name": "服装内衣"}]
 *"bgap": [{"id": "0","name": "美工"}]
 */

public class StoreConditionInfoData {
    private ArrayList<StoreConditionBean> bgat;
    private ArrayList<StoreConditionBean> bgap;

    public ArrayList<StoreConditionBean> getBgat() {
        return bgat;
    }

    public void setBgat(ArrayList<StoreConditionBean> bgat) {
        this.bgat = bgat;
    }

    public ArrayList<StoreConditionBean> getBgap() {
        return bgap;
    }

    public void setBgap(ArrayList<StoreConditionBean> bgap) {
        this.bgap = bgap;
    }

    @Override
    public String toString() {
        return "StoreConditionInfoData{" +
                "bgat=" + bgat +
                ", bgap=" + bgap +
                '}';
    }
}
