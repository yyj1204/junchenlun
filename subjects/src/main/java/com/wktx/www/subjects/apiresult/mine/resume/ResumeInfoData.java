package com.wktx.www.subjects.apiresult.mine.resume;

import com.wktx.www.subjects.apiresult.main.condition.ConditionBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 369 on 2018/6/1.
 * 我的简历内容
 *
 * id : 13
 * name : 啊发撒
 * sex : 0
 * picture : http://shop.jcl.55085.cn/public/upload/image/2018/05-19/201805190944059487.png
 * monthly_money : 3000.00
 * is_job_hunting : 1
 * typing_speed : 0字/分
 * resume_content : ""
 * tow : {"id":"2","name":"客服"}
 * bgap : {"id":"1","name":"淘宝"}
 * bgasList : [{"id":"1","name":"日韩小清新"},{"id":"2","name":"中国风"},{"id":"3","name":"时尚简约"}]
 * bgatList : [{"id":"1","name":"服装内衣"},{"id":"2","name":"母婴玩具"},{"id":"3","name":"精品鞋包"}]
 * working_years : {"id":"2","years":"一年"}
 * work_experience : [{
 *                     "work_date":{"start_date":"2018/05","end_date":"2018/06"},
 *                     "company":"尚仁","bgat":0,"store":"http://www.sr35.com/","position":"客服性格介绍性格介绍性格介绍",
 *                     "introduction":"尚仁尚仁尚仁尚仁性格介绍性格介绍性格介绍"
 *                     }]
 */

public class ResumeInfoData implements Serializable {
    private String id;//简历id
    private String name;//名字
    private String picture;//头像
    private String sex;//性别 0:未设置 1:男 2:女
    private String monthly_money;//包月金额
    private String typing_speed;//打字速度
    private String resume_content;//个性简历
    private String is_job_hunting;//是否找工作 0: 否 1:是
    private ConditionBean tow;//职位
    private ConditionBean bgap;//平台信息
    private ConditionBean working_years;//工作经验
    private List<ConditionBean> bgatList;//擅长类目
    private List<ConditionBean> bgasList;//风格
    private ArrayList<WorkExperienceBean> work_experience;//工作经历

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConditionBean getTow() {
        return tow;
    }

    public void setTow(ConditionBean tow) {
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

    public ConditionBean getBgap() {
        return bgap;
    }

    public void setBgap(ConditionBean bgap) {
        this.bgap = bgap;
    }

    public ConditionBean getWorking_years() {
        return working_years;
    }

    public void setWorking_years(ConditionBean working_years) {
        this.working_years = working_years;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMonthly_money() {
        return monthly_money;
    }

    public void setMonthly_money(String monthly_money) {
        this.monthly_money = monthly_money;
    }

    public String getTyping_speed() {
        return typing_speed;
    }

    public void setTyping_speed(String typing_speed) {
        this.typing_speed = typing_speed;
    }

    public String getResume_content() {
        return resume_content;
    }

    public void setResume_content(String resume_content) {
        this.resume_content = resume_content;
    }

    public String getIs_job_hunting() {
        return is_job_hunting;
    }

    public void setIs_job_hunting(String is_job_hunting) {
        this.is_job_hunting = is_job_hunting;
    }

    public ArrayList<WorkExperienceBean> getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(ArrayList<WorkExperienceBean> work_experience) {
        this.work_experience = work_experience;
    }

    public List<ConditionBean> getBgasList() {
        return bgasList;
    }

    public void setBgasList(List<ConditionBean> bgasList) {
        this.bgasList = bgasList;
    }

    public List<ConditionBean> getBgatList() {
        return bgatList;
    }

    public void setBgatList(List<ConditionBean> bgatList) {
        this.bgatList = bgatList;
    }

    @Override
    public String toString() {
        return "ResumeInfoData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", sex='" + sex + '\'' +
                ", monthly_money='" + monthly_money + '\'' +
                ", typing_speed='" + typing_speed + '\'' +
                ", resume_content='" + resume_content + '\'' +
                ", is_job_hunting='" + is_job_hunting + '\'' +
                ", tow=" + tow +
                ", bgap=" + bgap +
                ", bgatList=" + bgatList +
                ", bgasList=" + bgasList +
                ", working_years=" + working_years +
                ", work_experience=" + work_experience +
                '}';
    }
}
