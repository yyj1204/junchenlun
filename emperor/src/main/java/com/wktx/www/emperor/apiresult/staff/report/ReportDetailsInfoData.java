package com.wktx.www.emperor.apiresult.staff.report;

import java.util.List;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---工作报告---报告详情内容
 * "id": "1","shop_name": "测试店铺1","today_work_content": "今日工作内容1","store_current_situation": "店铺目前现状1",
 * "future_operation_plan": "往后运营计划1","need_help": "需要帮助1","add_time": "1520910453","service_attitude": "0",
 * "working_ability": "0","response_speed": "0","evaluation_content": ""
 * "data_representation": [
 *          "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png",
 *          "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png",
 *          "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png"],
 */

public class ReportDetailsInfoData {
    private String id;//工作报告id
    private String shop_name;//店铺名称
    private String today_work_content;//今天的工作内容
    private String store_current_situation;//店铺目前现状
    private String future_operation_plan;//往后运营计划
    private String need_help;//需要帮助
    private String add_time;//创建时间
    private String service_attitude;//服务态度
    private String working_ability;//工作能力
    private String response_speed;//响应速度
    private String evaluation_content;//评价内容
    private List<String> data_representation;//数据表现

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToday_work_content() {
        return today_work_content;
    }

    public void setToday_work_content(String today_work_content) {
        this.today_work_content = today_work_content;
    }

    public String getStore_current_situation() {
        return store_current_situation;
    }

    public void setStore_current_situation(String store_current_situation) {
        this.store_current_situation = store_current_situation;
    }

    public String getFuture_operation_plan() {
        return future_operation_plan;
    }

    public void setFuture_operation_plan(String future_operation_plan) {
        this.future_operation_plan = future_operation_plan;
    }

    public String getNeed_help() {
        return need_help;
    }

    public void setNeed_help(String need_help) {
        this.need_help = need_help;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getService_attitude() {
        return service_attitude;
    }

    public void setService_attitude(String service_attitude) {
        this.service_attitude = service_attitude;
    }

    public String getWorking_ability() {
        return working_ability;
    }

    public void setWorking_ability(String working_ability) {
        this.working_ability = working_ability;
    }

    public String getResponse_speed() {
        return response_speed;
    }

    public void setResponse_speed(String response_speed) {
        this.response_speed = response_speed;
    }

    public String getEvaluation_content() {
        return evaluation_content;
    }

    public void setEvaluation_content(String evaluation_content) {
        this.evaluation_content = evaluation_content;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public List<String> getData_representation() {
        return data_representation;
    }

    public void setData_representation(List<String> data_representation) {
        this.data_representation = data_representation;
    }


    @Override
    public String toString() {
        return "ReportDetailsInfoData{" +
                "id='" + id + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", today_work_content='" + today_work_content + '\'' +
                ", store_current_situation='" + store_current_situation + '\'' +
                ", future_operation_plan='" + future_operation_plan + '\'' +
                ", need_help='" + need_help + '\'' +
                ", add_time='" + add_time + '\'' +
                ", service_attitude='" + service_attitude + '\'' +
                ", working_ability='" + working_ability + '\'' +
                ", response_speed='" + response_speed + '\'' +
                ", evaluation_content='" + evaluation_content + '\'' +
                ", data_representation=" + data_representation +
                '}';
    }
}

