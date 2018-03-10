package com.wktx.www.emperor.apiresult.main.home;

/**
 * Created by yyj on 2018/2/12.
 * 首页职位列表
 * {"id": "2", "tow": "1","name": "啊西歌","picture": "","working_years": "5","sex": "1","monthly_money": "4800.00",
 *  "is_job_hunting": "1","bgat": "母婴玩具/精品鞋包/生活百货","bgas": "中国风/时尚简约/质感炫酷","typing_speed":"95字/分"}
 */

public class ResumeListBean {
    private String id;//简历id
    private String tow;//职位类型 1:美工 2:客服 3:运营
    private String name;//姓名
    private String picture;//头像
    private String working_years;//工龄 0:未设置 1:一年以内 2:一年 3:二年 4:三年 5:四年 6:五年 7:五年以上
    private String sex;//性别 1:男 2:女
    private String monthly_money;//包月金额
    private String is_job_hunting;//找工作中 0:否 1:是
    private String bgat;//擅长类目
    private String bgas;//擅长风格
    private String typing_speed;//打字速度

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

    public String getMonthly_money() {
        return monthly_money;
    }

    public void setMonthly_money(String monthly_money) {
        this.monthly_money = monthly_money;
    }

    public String getIs_job_hunting() {
        return is_job_hunting;
    }

    public void setIs_job_hunting(String is_job_hunting) {
        this.is_job_hunting = is_job_hunting;
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


    @Override
    public String toString() {
        return "RecruitListInfoData{" +
                "id='" + id + '\'' +
                ", tow='" + tow + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", working_years='" + working_years + '\'' +
                ", sex='" + sex + '\'' +
                ", monthly_money='" + monthly_money + '\'' +
                ", is_job_hunting='" + is_job_hunting + '\'' +
                ", bgat='" + bgat + '\'' +
                ", bgas='" + bgas + '\'' +
                ", typing_speed='" + typing_speed +
                '}';
    }
}
