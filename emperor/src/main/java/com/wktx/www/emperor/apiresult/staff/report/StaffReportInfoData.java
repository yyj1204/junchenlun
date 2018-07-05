package com.wktx.www.emperor.apiresult.staff.report;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---工作报告（任务）内容
 * "id": "1","demand_title":"测试任务1","end_time": "1523375999","shop_name": "测试店铺1","shop_logo": ""
 */

public class StaffReportInfoData {
    private String id;//任务报告id
    private String demand_title;//需求标题
    private String end_time;//截止时间
    private String shop_name;//店铺名称
    private String shop_logo;//店铺头像

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemand_title() {
        return demand_title;
    }

    public void setDemand_title(String demand_title) {
        this.demand_title = demand_title;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    @Override
    public String toString() {
        return "StaffReportInfoData{" +
                "id='" + id + '\'' +
                ", demand_title='" + demand_title + '\'' +
                ", end_time='" + end_time + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                '}';
    }
}
