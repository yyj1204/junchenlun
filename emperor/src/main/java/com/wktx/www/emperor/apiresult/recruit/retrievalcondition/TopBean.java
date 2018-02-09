package com.wktx.www.emperor.apiresult.recruit.retrievalcondition;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yyj on 2018/1/24.
 * 顶部水平菜单
 */

public class TopBean implements Serializable{
    private List<Bean> tow;//工作类型 1:美工2:客服3:运营

    public List<Bean> getTow() {
        return tow;
    }

    public void setTow(List<Bean> tow) {
        this.tow = tow;
    }


    @Override
    public String toString() {
        return "TopBean{" +
                "tow=" + tow +
                '}';
    }
}
