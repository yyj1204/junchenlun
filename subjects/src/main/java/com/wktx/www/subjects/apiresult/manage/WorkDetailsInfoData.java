package com.wktx.www.subjects.apiresult.manage;

import com.wktx.www.subjects.apiresult.message.TaskListInfoData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yyj on 2018/6/25.
 * 我的工作详情内容
 * "hire_id": "410","nickname": "尚仁网络旗舰店","head_pic": "/public/upload/head_pic/2018/05-21/201805211507309664.jpeg",
 * "commission": "0","sw_id": "26","sales_id": "0",* "project_start_time": "2018-06-23 00:00:00",
 * "project_end_time": "2018-07-22 23:59:59",dismissal_id": "28",tow:"客服",
 * "paid_wages": "0.00","status": "1",,"signIn": "1","reportNum": "0",
 * "arrangementWork": [{
 *         "id": "4","report_id":"18","demand_title": "测试安排工作标题","demand_content": "测试安排工内容","shop_name": "安时代的",
 *         "shop_logo": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png",
 *         "end_time": "2018-03-15 17:07:29"}]
 */

public class WorkDetailsInfoData implements Serializable{
    private String hire_id;//雇佣id
    private String nickname;//姓名
    private String head_pic;//照片
    private String commission;//提成
    private String sw_id;//暂停工作id
    private String dismissal_id;//解雇id
    private String tow;//工作类型
    private String project_start_time;//项目开始时间
    private String project_end_time;//项目结束时间
    private String paid_wages;//已发工资
    private String status;//状态 0:未开始 1:合作中 2:请假中 3:暂停中 4:投诉中 5:被解雇 6:已完结 7:已退款 8:已取消 9:续约中
    private String signIn;//总签到次数
    private String reportNum;//总工作报告数量
    private String sales_id;//提交销售额id
    private List<TaskListInfoData> arrangementWork;//安排工作列表

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getSw_id() {
        return sw_id;
    }

    public void setSw_id(String sw_id) {
        this.sw_id = sw_id;
    }

    public String getDismissal_id() {
        return dismissal_id;
    }

    public void setDismissal_id(String dismissal_id) {
        this.dismissal_id = dismissal_id;
    }

    public String getTow() {
        return tow;
    }

    public void setTow(String tow) {
        this.tow = tow;
    }

    public String getProject_start_time() {
        return project_start_time;
    }

    public void setProject_start_time(String project_start_time) {
        this.project_start_time = project_start_time;
    }

    public String getProject_end_time() {
        return project_end_time;
    }

    public void setProject_end_time(String project_end_time) {
        this.project_end_time = project_end_time;
    }

    public String getPaid_wages() {
        return paid_wages;
    }

    public void setPaid_wages(String paid_wages) {
        this.paid_wages = paid_wages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSignIn() {
        return signIn;
    }

    public void setSignIn(String signIn) {
        this.signIn = signIn;
    }

    public String getReportNum() {
        return reportNum;
    }

    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }

    public String getSales_id() {
        return sales_id;
    }

    public void setSales_id(String sales_id) {
        this.sales_id = sales_id;
    }

    public List<TaskListInfoData> getArrangementWork() {
        return arrangementWork;
    }

    public void setArrangementWork(List<TaskListInfoData> arrangementWork) {
        this.arrangementWork = arrangementWork;
    }

    @Override
    public String toString() {
        return "WorkDetailsInfoData{" +
                "hire_id='" + hire_id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", commission='" + commission + '\'' +
                ", sw_id='" + sw_id + '\'' +
                ", dismissal_id='" + dismissal_id + '\'' +
                ", tow='" + tow + '\'' +
                ", project_start_time='" + project_start_time + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", paid_wages='" + paid_wages + '\'' +
                ", status='" + status + '\'' +
                ", signIn='" + signIn + '\'' +
                ", reportNum='" + reportNum + '\'' +
                ", sales_id='" + sales_id + '\'' +
                ", arrangementWork=" + arrangementWork +
                '}';
    }
}
