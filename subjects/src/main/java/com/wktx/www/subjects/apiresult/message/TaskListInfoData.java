package com.wktx.www.subjects.apiresult.message;

import java.io.Serializable;

/**
 * Created by yyj on 2018/2/8.
 * 任务消息(管理我的工作---安排工作)列表内容
 * id : 4
 * report_id : 18
 * dismissal_id: 0
 * status: 0
 * demand_title : 测试安排工作标题
 * demand_content : 测试安排工内容
 * shop_name : 安时代的
 * shop_logo : http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png
 * end_time : 2018-03-15 17:07:29
 */

public class TaskListInfoData implements Serializable{
    private String id;//安排工作id
    private String report_id;//工作报告id
    private String dismissal_id;//解雇id(任务消息)
    private String status;//状态 0:未开始 1:合作中 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:已完结 7:已退款 8:已取消 9:续约中(任务消息)
    private String demand_title;//需求标题
    private String demand_content;//需求内容
    private String shop_name;//店铺名称
    private String shop_logo;//店铺logo
    private String end_time;//截止时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getDismissal_id() {
        return dismissal_id;
    }

    public void setDismissal_id(String dismissal_id) {
        this.dismissal_id = dismissal_id;
    }

    public String getDemand_title() {
        return demand_title;
    }

    public void setDemand_title(String demand_title) {
        this.demand_title = demand_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDemand_content() {
        return demand_content;
    }

    public void setDemand_content(String demand_content) {
        this.demand_content = demand_content;
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

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "TaskListInfoData{" +
                "id='" + id + '\'' +
                ", report_id='" + report_id + '\'' +
                ", dismissal_id='" + dismissal_id + '\'' +
                ", status='" + status + '\'' +
                ", demand_title='" + demand_title + '\'' +
                ", demand_content='" + demand_content + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }
}
