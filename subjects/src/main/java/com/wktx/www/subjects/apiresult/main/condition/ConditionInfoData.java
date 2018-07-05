package com.wktx.www.subjects.apiresult.main.condition;


import java.util.ArrayList;

/**
 * Created by 369 on 2018/6/1.
 * 参数（平台、风格、类目、职位、工作经验）内容
 */

public class ConditionInfoData {
    private ArrayList<ConditionBean> towList;//职位类型
    private ArrayList<ConditionBean> bgatList;//擅长类目
    private ArrayList<ConditionBean> bgapList;//擅长平台
    private ArrayList<ConditionBean> bgasList;//擅长风格
    private ArrayList<ConditionBean> workingYearsList;//工作经验

    public ArrayList<ConditionBean> getTowList() {
        return towList;
    }

    public void setTowList(ArrayList<ConditionBean> towList) {
        this.towList = towList;
    }

    public ArrayList<ConditionBean> getBgatList() {
        return bgatList;
    }

    public void setBgatList(ArrayList<ConditionBean> bgatList) {
        this.bgatList = bgatList;
    }

    public ArrayList<ConditionBean> getBgapList() {
        return bgapList;
    }

    public void setBgapList(ArrayList<ConditionBean> bgapList) {
        this.bgapList = bgapList;
    }

    public ArrayList<ConditionBean> getBgasList() {
        return bgasList;
    }

    public void setBgasList(ArrayList<ConditionBean> bgasList) {
        this.bgasList = bgasList;
    }

    public ArrayList<ConditionBean> getWorkingYearsList() {
        return workingYearsList;
    }

    public void setWorkingYearsList(ArrayList<ConditionBean> workingYearsList) {
        workingYearsList = workingYearsList;
    }

    @Override
    public String toString() {
        return "WorksConditionInfoData{" +
                "towList=" + towList +
                ", bgatList=" + bgatList +
                ", bgapList=" + bgapList +
                ", bgasList=" + bgasList +
                ", WorkingYearsList=" + workingYearsList +
                '}';
    }
}
