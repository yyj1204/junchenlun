package com.wktx.www.emperor.apiresult.staff.report;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---工作报告（任务）---报告列表内容
 * "id": "1","add_time": "1520910453", "evaluate_time": "1531132249"
 */

public class StaffReportListInfoData {
    private String id;//报告id
    private String add_time;//报告发布时间
    private String evaluate_time;//评价时间

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

    public String getEvaluate_time() {
        return evaluate_time;
    }

    public void setEvaluate_time(String evaluate_time) {
        this.evaluate_time = evaluate_time;
    }

    @Override
    public String toString() {
        return "StaffReportListInfoData{" +
                "id='" + id + '\'' +
                ", add_time='" + add_time + '\'' +
                ", evaluate_time='" + evaluate_time + '\'' +
                '}';
    }
}
