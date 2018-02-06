package com.wktx.www.emperor.apiresult.recruit.resume;

import java.util.List;

/**
 * Created by yyj on 2018/2/2.
 * 简历详情内容
 * "id":"1","tow":"1","name":"胡图图","picture":"","highest_education":"本科","working_years":"一年","sex":"2",
 * "date_of_birth":"1992.05","residential_city":"浙江-杭州","character_introduction":"脾气好、只做一家","resume_content":"",
 * "monthly_money":"3500.00","wechat":"","qq":"","phone":"0","bgat":"服装内衣/母婴玩具/精品鞋包","bgas":"日韩小清新/中国风/时尚简约",
 * "is_collection":0,"evaluate_num":0,"service_attitude":"0","ability":"0","response_speed":"0",
 * "work_experience":[{"work_date":{"start_date":"2014/07","end_date":"2016/08"},"company":"福建网客天下科技有限公司",
 * "bgat":"服装内衣","position":"视觉传达设计师","introduction":"负责整个店铺页面装修，活动页面制作，商品详情"}]
 */

public class ResumeInfoData {
    private String id;
    private String tow;
    private String name;
    private String picture;
    private String highest_education;//最高学历
    private String working_years;//工龄
    private String sex;
    private String date_of_birth;
    private String residential_city;//所在城市
    private String character_introduction;//性格介绍
    private String resume_content;//个性简历
    private String monthly_money;//工作经历
    private String wechat;
    private String qq;
    private String phone;
    private String bgat;//擅长类目
    private String bgas;//擅长风格
    private int is_collection;//是否收藏 0:否 1:是
    private int evaluate_num;//评价数量
    private String service_attitude;//服务态度星级
    private String ability;//能力星级
    private String response_speed;//相应速度星级
    private List<WorkExperienceBean> work_experience;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTow() {
        return tow;
    }
    public void setTow(String tow) {
        this.tow = tow;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getHighest_education() {
        return highest_education;
    }
    public void setHighest_education(String highest_education) {
        this.highest_education = highest_education;
    }
    public String getWorking_years() {
        return working_years;
    }
    public void setWorking_years(String working_years) {
        this.working_years = working_years;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getDate_of_birth() {
        return date_of_birth;
    }
    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
    public String getResidential_city() {
        return residential_city;
    }
    public void setResidential_city(String residential_city) {
        this.residential_city = residential_city;
    }
    public String getCharacter_introduction() {
        return character_introduction;
    }
    public void setCharacter_introduction(String character_introduction) {
        this.character_introduction = character_introduction;
    }
    public String getResume_content() {
        return resume_content;
    }
    public void setResume_content(String resume_content) {
        this.resume_content = resume_content;
    }
    public String getMonthly_money() {
        return monthly_money;
    }
    public void setMonthly_money(String monthly_money) {
        this.monthly_money = monthly_money;
    }
    public String getWechat() {
        return wechat;
    }
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
    public String getQq() {
        return qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getBgat() {
        return bgat;
    }
    public void setBgat(String bgat) {
        this.bgat = bgat;
    }
    public String getBgas() {
        return bgas;
    }
    public void setBgas(String bgas) {
        this.bgas = bgas;
    }
    public int getIs_collection() {
        return is_collection;
    }
    public void setIs_collection(int is_collection) {
        this.is_collection = is_collection;
    }
    public int getEvaluate_num() {
        return evaluate_num;
    }
    public void setEvaluate_num(int evaluate_num) {
        this.evaluate_num = evaluate_num;
    }
    public String getService_attitude() {
        return service_attitude;
    }
    public void setService_attitude(String service_attitude) {
        this.service_attitude = service_attitude;
    }
    public String getAbility() {
        return ability;
    }
    public void setAbility(String ability) {
        this.ability = ability;
    }
    public String getResponse_speed() {
        return response_speed;
    }
    public void setResponse_speed(String response_speed) {
        this.response_speed = response_speed;
    }
    public List<WorkExperienceBean> getWork_experience() {
        return work_experience;
    }
    public void setWork_experience(List<WorkExperienceBean> work_experience) {
        this.work_experience = work_experience;
    }


    @Override
    public String toString() {
        return "InfoBean{" +
                "id='" + id + '\'' +
                ", tow='" + tow + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", highest_education='" + highest_education + '\'' +
                ", working_years='" + working_years + '\'' +
                ", sex='" + sex + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", residential_city='" + residential_city + '\'' +
                ", character_introduction='" + character_introduction + '\'' +
                ", resume_content='" + resume_content + '\'' +
                ", monthly_money='" + monthly_money + '\'' +
                ", wechat='" + wechat + '\'' +
                ", qq='" + qq + '\'' +
                ", phone='" + phone + '\'' +
                ", bgat='" + bgat + '\'' +
                ", bgas='" + bgas + '\'' +
                ", is_collection=" + is_collection +
                ", evaluate_num=" + evaluate_num +
                ", service_attitude='" + service_attitude + '\'' +
                ", ability='" + ability + '\'' +
                ", response_speed='" + response_speed + '\'' +
                ", work_experience=" + work_experience +
                '}';
    }

    public static class WorkExperienceBean {
        private WorkDateBean work_date;//工作日期
        private String company;//公司
        private String bgat;//	擅长类目
        private String position;//工作类型
        private String introduction;//介绍

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
        public String getBgat() {
            return bgat;
        }
        public void setBgat(String bgat) {
            this.bgat = bgat;
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

        @Override
        public String toString() {
            return "WorkExperienceBean{" +
                    "work_date=" + work_date +
                    ", company='" + company + '\'' +
                    ", bgat='" + bgat + '\'' +
                    ", position='" + position + '\'' +
                    ", introduction='" + introduction + '\'' +
                    '}';
        }

        public static class WorkDateBean {
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
}