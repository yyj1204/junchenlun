package com.wktx.www.emperor.apiresult.staff.kpi;

import java.util.List;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---考核指标内容
 * id : 16
 * uid : 2684
 * name: 15750965106
 * sex: 2
 * picture : http://shop.junchenlun.com/public/upload/head_pic/2018/06-29/201806291044030265.jpg
 * working_years : 7
 * residential_city : 上海市-市辖区-黄浦区
 * qq : 123456
 * phone : 15750965106
 * project_end_time : 2018-07-27 23:59:59
 * work_days : 21
 * arrangement_work_count : 3
 * attendance_days : 2
 * bgat : [{"id":"5","name":"3C数码"},{"id":"6","name":"家电办公"},{"id":"7","name":"化妆美容"}]
 */

public class StaffKPIInfoData {
    private String id;//简历id
    private String uid;//员工id
    private String name;//员工名字
    private String sex;//性别 1：男  2：女
    private String picture;//头像
    private String working_years;//工作经验
    private String residential_city;//地址
    private String qq;//qq
    private String phone;//电话
    private String project_end_time;//项目结束时间
    private int work_days;//工作天数
    private String arrangement_work_count;//安排工作总数
    private String attendance_days;//签到天数
    private List<BgatBean> bgat;//擅长类目

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getResidential_city() {
        return residential_city;
    }

    public void setResidential_city(String residential_city) {
        this.residential_city = residential_city;
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

    public String getProject_end_time() {
        return project_end_time;
    }

    public void setProject_end_time(String project_end_time) {
        this.project_end_time = project_end_time;
    }

    public int getWork_days() {
        return work_days;
    }

    public void setWork_days(int work_days) {
        this.work_days = work_days;
    }

    public String getArrangement_work_count() {
        return arrangement_work_count;
    }

    public void setArrangement_work_count(String arrangement_work_count) {
        this.arrangement_work_count = arrangement_work_count;
    }

    public String getAttendance_days() {
        return attendance_days;
    }

    public void setAttendance_days(String attendance_days) {
        this.attendance_days = attendance_days;
    }

    public List<BgatBean> getBgat() {
        return bgat;
    }

    public void setBgat(List<BgatBean> bgat) {
        this.bgat = bgat;
    }


    @Override
    public String toString() {
        return "StaffKPIInfoData{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", picture='" + picture + '\'' +
                ", working_years='" + working_years + '\'' +
                ", residential_city='" + residential_city + '\'' +
                ", qq='" + qq + '\'' +
                ", phone='" + phone + '\'' +
                ", project_end_time='" + project_end_time + '\'' +
                ", work_days=" + work_days +
                ", arrangement_work_count='" + arrangement_work_count + '\'' +
                ", attendance_days='" + attendance_days + '\'' +
                ", bgat=" + bgat +
                '}';
    }

    public static class BgatBean {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "BgatBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
