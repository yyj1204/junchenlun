package com.wktx.www.emperor.apiresult.staff.salary;

/**
 * Created by 369 on 2018/3/21.
 * 托管中工资
 */

public class SalaryTrusteeshipBean {
    private String start_time;
    private String end_time;
    private String trusteeship_wages;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTrusteeship_wages() {
        return trusteeship_wages;
    }

    public void setTrusteeship_wages(String trusteeship_wages) {
        this.trusteeship_wages = trusteeship_wages;
    }

    @Override
    public String toString() {
        return "SalaryTrusteeshipBean{" +
                "start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", trusteeship_wages='" + trusteeship_wages + '\'' +
                '}';
    }
}
