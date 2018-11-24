package com.wktx.www.subjects.apiresult.main.demand;


/**
 * Created by yyj on 2018/2/8.
 * 职位招聘需求列表内容 \ 浏览记录内容 \ 我的收藏内容 \ 面试记录内容
 * "id": "17","demand_id": "10","title": "Add","content": "Add","bgap_name": "天猫","bgat_name": "汽车配件",
 * "design_pattern": "0","end_time": "2018-08-07 00:00:00","hire_type": "定制","budget": "123.00",
 * "shop_name": "555","head_pic": null,"is_dated": "0", "tow":"美工","working_years": "四年",
 * "nickname": "尚仁网络旗舰店6.29","address_from": "福建省-泉州市-丰泽区",("time": "2018-06-06 14:21:07")
 */

public class DemandListInfoData {
    private String id;//收藏id、面试id、招聘id（职位招聘、浏览记录）
    private String demand_id;//招聘id（面试、收藏）
    private String time;//面试时间（面试）
    private String is_dated;//是否过期，1：过期，2：未过期(收藏)
    private String title;//招聘标题
    private String content;//招聘内容
    private String tow;//招聘职位
    private String working_years;//工作经验
    private String nickname;//公司名称
    private String address_from;//公司地址
    private String bgap_name;//平台名称
    private String bgat_name;//类目名称
    private String design_pattern;//设计模式名称
    private String end_time;//截止时间
    private String hire_type;//雇佣方式
    private String budget;//薪资
    private String shop_name;//店铺名称
    private String head_pic;//公司logo

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIs_dated() {
        return is_dated;
    }

    public void setIs_dated(String is_dated) {
        this.is_dated = is_dated;
    }

    public String getDemand_id() {
        return demand_id;
    }

    public void setDemand_id(String demand_id) {
        this.demand_id = demand_id;
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

    public String getTow() {
        return tow;
    }

    public void setTow(String tow) {
        this.tow = tow;
    }

    public String getWorking_years() {
        return working_years;
    }

    public void setWorking_years(String working_years) {
        this.working_years = working_years;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress_from() {
        return address_from;
    }

    public void setAddress_from(String address_from) {
        this.address_from = address_from;
    }

    public String getBgap_name() {
        return bgap_name;
    }

    public void setBgap_name(String bgap_name) {
        this.bgap_name = bgap_name;
    }

    public String getBgat_name() {
        return bgat_name;
    }

    public void setBgat_name(String bgat_name) {
        this.bgat_name = bgat_name;
    }

    public String getDesign_pattern() {
        return design_pattern;
    }

    public void setDesign_pattern(String design_pattern) {
        this.design_pattern = design_pattern;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getHire_type() {
        return hire_type;
    }

    public void setHire_type(String hire_type) {
        this.hire_type = hire_type;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    @Override
    public String toString() {
        return "DemandListInfoData{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", is_dated='" + is_dated + '\'' +
                ", demand_id='" + demand_id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tow=" + tow +
                ", working_years='" + working_years + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address_from='" + address_from + '\'' +
                ", bgap_name='" + bgap_name + '\'' +
                ", bgat_name='" + bgat_name + '\'' +
                ", design_pattern='" + design_pattern + '\'' +
                ", end_time='" + end_time + '\'' +
                ", hire_type='" + hire_type + '\'' +
                ", budget='" + budget + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", head_pic='" + head_pic + '\'' +
                '}';
    }
}
