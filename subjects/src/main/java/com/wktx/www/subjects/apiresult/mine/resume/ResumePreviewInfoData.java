package com.wktx.www.subjects.apiresult.mine.resume;
import java.util.List;

/**
 * Created by yyj on 2018/2/2.
 * 预览简历内容
 *"id":"1","tow":"1","name":"胡图图","picture":"","highest_education":"本科","working_years":"一年","sex":"2",
 *"date_of_birth":"1992.05","residential_city":"浙江-杭州","character_introduction":"脾气好、只做一家","resume_content":"",
 *"monthly_money":"3500.00","wechat":"","qq":"","phone":"0","typing_speed": "0字/分","bgat":"服装内衣/母婴玩具/精品鞋包",
 *"bgas":"日韩小清新/中国风/时尚简约","tow_name": "美工",,"is_job_hunting":"1"
 *"work_experience":[{"work_date":{"start_date":"2014/07","end_date":"2016/08"},"company":"福建网客天下科技有限公司",
 *                "bgat":"服装内衣","position":"视觉传达设计师","introduction":"负责整个店铺页面装修，活动页面制作，商品详情"}]
 */

public class ResumePreviewInfoData {
    private String id;//简历id
    private String tow;//工作类型 1:美工2:客服3:运营
    private String tow_name;//工作类型名称 1:美工2:客服3:运营
    private String name;
    private String picture;
    private String highest_education;//最高学历
    private String working_years;//工龄
    private String sex;
    private String date_of_birth;
    private String residential_city;//所在城市
    private String character_introduction;//性格介绍
    private String resume_content;//个性简历
    private String monthly_money;//包月金额
    private String wechat;
    private String qq;
    private String phone;
    private String bgat;//擅长类目
    private String bgas;//擅长风格
    private String typing_speed;//打字速度
    private String is_job_hunting;//找工作中 0:否 1:是
    private List<WorkExperienceBean> work_experience;//工作经历

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
    public String getTow_name() {
        return tow_name;
    }
    public void setTow_name(String tow_name) {
        this.tow_name = tow_name;
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

    public String getTyping_speed() {
        return typing_speed;
    }

    public void setTyping_speed(String typing_speed) {
        this.typing_speed = typing_speed;
    }

    public String getIs_job_hunting() {
        return is_job_hunting;
    }

    public void setIs_job_hunting(String is_job_hunting) {
        this.is_job_hunting = is_job_hunting;
    }

    public List<WorkExperienceBean> getWork_experience() {
        return work_experience;
    }
    public void setWork_experience(List<WorkExperienceBean> work_experience) {
        this.work_experience = work_experience;
    }


    @Override
    public String toString() {
        return "ResumeInfoData{" +
                "id='" + id + '\'' +
                ", tow='" + tow + '\'' +
                ", tow_name='" + tow_name + '\'' +
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
                ", typing_speed='" + typing_speed + '\'' +
                ", is_job_hunting='" + is_job_hunting + '\'' +
                ", work_experience=" + work_experience +
                '}';
    }
}
