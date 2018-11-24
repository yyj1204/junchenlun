package com.wktx.www.subjects.apiresult.manage.report;

/**
 * Created by yyj on 2018/3/6.
 * 我的工作---管理工作---安排工作---报告列表内容
 * "id": "27","add_time": "2018-06-26 11:16:01","is_evaluate": 0
 */

public class ReportListInfoData {
    private String id;//工作报告id
    private String add_time;//工作报告时间
    private String is_evaluate;//是否评价 0：未评价 1：已评价

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getIs_evaluate() {
        return is_evaluate;
    }

    public void setIs_evaluate(String is_evaluate) {
        this.is_evaluate = is_evaluate;
    }

    @Override
    public String toString() {
        return "ReportListInfoData{" +
                "id='" + id + '\'' +
                ", add_time='" + add_time + '\'' +
                ", is_evaluate=" + is_evaluate +
                '}';
    }
}
