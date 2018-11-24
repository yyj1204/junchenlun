package com.wktx.www.subjects.apiresult.mine.resume;

import java.io.Serializable;

/**
 * Created by 369 on 2018/6/1.
 * 我的简历---工作经历
 *
 * work_date : {"start_date":"2018/05","end_date":"2018/06"}
 * company : 尚仁
 * bgap : "0"
 * bgat : "0"
 * store : http://www.sr35.com/
 * position : 客服性格介绍性格介绍性格介绍
 * introduction : 尚仁尚仁尚仁尚仁性格介绍性格介绍性格介绍
 * monthly_money : 0
 */

public class WorkExperienceBean implements Serializable{
    private WorkDateBean work_date;//工作时间
    private String company;//公司
    private String bgap;//	擅长平台
    private String bgat;//	擅长类目
    private String store;//	网址
    private String position;//工作类型
    private String introduction;//介绍
    private String monthly_money;//薪资

    public WorkDateBean getWork_date() {
        return work_date;
    }

    public void setWork_date(WorkDateBean work_date) {
        this.work_date = work_date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBgap() {
        return bgap;
    }

    public void setBgap(String bgap) {
        this.bgap = bgap;
    }

    public String getBgat() {
        return bgat;
    }

    public void setBgat(String bgat) {
        this.bgat = bgat;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMonthly_money() {
        return monthly_money;
    }

    public void setMonthly_money(String monthly_money) {
        this.monthly_money = monthly_money;
    }

    @Override
    public String toString() {
        return "WorkExperienceBean{" +
                "work_date=" + work_date +
                ", company='" + company + '\'' +
                ", bgap='" + bgap + '\'' +
                ", bgat='" + bgat + '\'' +
                ", store='" + store + '\'' +
                ", position='" + position + '\'' +
                ", introduction='" + introduction + '\'' +
                ", monthly_money='" + monthly_money + '\'' +
                '}';
    }

    public static class WorkDateBean implements Serializable{
        /**
         * start_date : 2018/05
         * end_date : 2018/06
         */

        private String start_date;
        private String end_date;

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }


        @Override
        public String toString() {
            return "WorkDateBean{" +
                    "start_date='" + start_date + '\'' +
                    ", end_date='" + end_date + '\'' +
                    '}';
        }
    }
}