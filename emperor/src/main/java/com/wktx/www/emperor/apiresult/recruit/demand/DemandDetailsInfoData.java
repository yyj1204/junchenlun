package com.wktx.www.emperor.apiresult.recruit.demand;

import com.wktx.www.emperor.apiresult.mine.store.StoreListInfoData;
import com.wktx.www.emperor.apiresult.recruit.retrievalcondition.Bean;

import java.util.List;

/**
 * Created by yyj on 2018/1/26.
 * 需求详情内容
 * id : 30
 * bgap : {"id":"4","name":"苏宁"}
 * bgat : {"id":"4","name":"生活百货"}
 * title : 装修房子
 * content : 给我揉揉孙洪涛VSOP瑞图你女聚聚提督这1838386881我可以盔子
 * design_pattern : {"id":"5","name":"海报BANNERE设计"}
 * budget : 2000.00
 * add_time : 1532577426
 * end_time : 1532966400
 * status_log : [{"remark":"您创建了需求","create_time":"1532577426","user_id":"2649"}]
 * status : 0
 * hire_type : 包月
 * tow : {"id":"1","name":"美工"}
 * working_years : 两年
 * store : {"id":"13","shop_name":"张公子茶叶店","shop_logo":"http://shop.junchenlun.com/public/upload/store_logo/2018/07-01/201807011803044062.jpg"}
 */

public class DemandDetailsInfoData {
    private String id;//需求id
    private String title;//标题
    private String content;//详情内容
    private StoreListInfoData store;//店铺信息
    private Bean tow;//工作类型
    private Bean bgap;//平台
    private Bean bgat;//类目
    private Bean design_pattern;//设计模式
    private String budget;//预算
    private String add_time;//发布时间
    private String end_time;//截止时间
    private String status;//响应状态
    private String hire_type;//雇佣方式：包月,定制
    private String working_years;//工作经验
    private List<StatusLogBean> status_log;//响应日志

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

    public StoreListInfoData getStore() {
        return store;
    }

    public void setStore(StoreListInfoData store) {
        this.store = store;
    }

    public Bean getTow() {
        return tow;
    }

    public void setTow(Bean tow) {
        this.tow = tow;
    }

    public Bean getBgap() {
        return bgap;
    }

    public void setBgap(Bean bgap) {
        this.bgap = bgap;
    }

    public Bean getBgat() {
        return bgat;
    }

    public void setBgat(Bean bgat) {
        this.bgat = bgat;
    }

    public Bean getDesign_pattern() {
        return design_pattern;
    }

    public void setDesign_pattern(Bean design_pattern) {
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

    public String getHire_type() {
        return hire_type;
    }

    public void setHire_type(String hire_type) {
        this.hire_type = hire_type;
    }

    public String getWorking_years() {
        return working_years;
    }

    public void setWorking_years(String working_years) {
        this.working_years = working_years;
    }

    public List<StatusLogBean> getStatus_log() {
        return status_log;
    }

    public void setStatus_log(List<StatusLogBean> status_log) {
        this.status_log = status_log;
    }


    @Override
    public String toString() {
        return "DemandDetailsInfoData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", store=" + store +
                ", tow=" + tow +
                ", bgap=" + bgap +
                ", bgat=" + bgat +
                ", design_pattern=" + design_pattern +
                ", budget='" + budget + '\'' +
                ", add_time='" + add_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", status='" + status + '\'' +
                ", hire_type='" + hire_type + '\'' +
                ", working_years='" + working_years + '\'' +
                ", status_log=" + status_log +
                '}';
    }

    public static class StatusLogBean {
        /**
         * remark : 您创建了需求
         * create_time : 1532577426
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
}
