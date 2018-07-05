package com.wktx.www.emperor.apiresult.recruit.demand;

import java.util.List;

/**
 * Created by yyj on 2018/1/26.
 * 需求详情内容
 * id : 1
 * title : 淘宝首页设计
 * content : 淘宝首页设计淘宝首页设计淘宝首页设计
 * design_pattern : 其他设计
 * budget : 200.00
 * add_time : 1516930645
 * end_time : 1521388799
 * status_log : [{"remark":"您创建了需求","create_time":"1516930645","user_id":"2649"}]
 * status : 0
 */

public class DemandDetailsInfoData {
    private String id;
    private String title;
    private String content;
    private String design_pattern;
    private String budget;
    private String add_time;
    private String end_time;
    private String status;//响应状态 0：未响应，1：已响应
    private List<StatusLogBean> status_log;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesign_pattern() {
        return design_pattern;
    }

    public void setDesign_pattern(String design_pattern) {
        this.design_pattern = design_pattern;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StatusLogBean> getStatus_log() {
        return status_log;
    }

    public void setStatus_log(List<StatusLogBean> status_log) {
        this.status_log = status_log;
    }

    public static class StatusLogBean {
        /**
         * remark : 您创建了需求
         * create_time : 1516930645
         * user_id : 2649
         */

        private String remark;
        private String create_time;
        private String user_id;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        @Override
        public String toString() {
            return "StatusLogBean{" +
                    "remark='" + remark + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", user_id='" + user_id + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "DemandDetailsInfoData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", design_pattern='" + design_pattern + '\'' +
                ", budget='" + budget + '\'' +
                ", add_time='" + add_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", status='" + status + '\'' +
                ", status_log=" + status_log +
                '}';
    }
}
