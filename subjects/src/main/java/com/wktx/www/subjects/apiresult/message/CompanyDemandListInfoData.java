package com.wktx.www.subjects.apiresult.message;
/**
 * Created by yyj on 2018/6/9.
 * 对我感兴趣公司---公司招聘需求列表内容
 * id : 15
 * title : 1234455664
 * content : whxhxhxhxb
 * tow: 美工
 * working_years: 四年
 * end_time: 2020-09-13
 * hire_type: 不限
 * design_pattern: "海报BANNERE设计
 * bgap_name : 京东
 * bgat_name : 服装内衣
 * budget : 1111111.00
 * shop_name : 测试2
 * shop_logo : http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png
 */

public class CompanyDemandListInfoData {
    private String id;//招聘id
    private String title;//招聘标题
    private String content;//招聘内容
    private String tow;//工作类型
    private String working_years;//工作经验
    private String end_time;//截止时间
    private String hire_type;//雇佣方式
    private String design_pattern;//设计名称
    private String bgap_name;//平台名称
    private String bgat_name;//类目名称
    private String budget;//薪资
    private String shop_name;//商铺名称
    private String shop_logo;//商铺logo

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

    public String getDesign_pattern() {
        return design_pattern;
    }

    public void setDesign_pattern(String design_pattern) {
        this.design_pattern = design_pattern;
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

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    @Override
    public String toString() {
        return "CompanyDemandListInfoData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tow='" + tow + '\'' +
                ", working_years='" + working_years + '\'' +
                ", end_time='" + end_time + '\'' +
                ", hire_type='" + hire_type + '\'' +
                ", design_pattern='" + design_pattern + '\'' +
                ", bgap_name='" + bgap_name + '\'' +
                ", bgat_name='" + bgat_name + '\'' +
                ", budget='" + budget + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                '}';
    }
}
