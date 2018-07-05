package com.wktx.www.emperor.apiresult.mine.condition;

import java.util.List;
/**
 * Created by yyj on 2018/2/8.
 * 个人中心---各个列表界面的检索条件
 *"tow": [{"id": "0","name": "全部"}]
 *"type": [{"id": "0","name": "全部"}]
 */

public class ConditionInfoData {
    private List<ConditionBean> tow;//工作类型
    private List<ConditionBean> type;//雇佣状态

    public List<ConditionBean> getTow() {
        return tow;
    }

    public void setTow(List<ConditionBean> tow) {
        this.tow = tow;
    }

    public List<ConditionBean> getType() {
        return type;
    }

    public void setType(List<ConditionBean> type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "WorksConditionInfoData{" +
                "tow=" + tow +
                ", type=" + type +
                '}';
    }
}
