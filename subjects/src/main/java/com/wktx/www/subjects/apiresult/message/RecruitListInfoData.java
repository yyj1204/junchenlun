package com.wktx.www.subjects.apiresult.message;
/**
 * Created by yyj on 2018/6/9.
 * 对我感兴趣公司---公司招聘列表内容
 * id : 15
 * title : 1234455664
 * content : whxhxhxhxb
 * bgap_name : 京东
 * bgat_name : 服装内衣
 * budget : 1111111.00
 * shop_name : 测试2
 * shop_logo : http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png
 */

public class RecruitListInfoData {
    private String id;//招聘id
    private String title;//招聘标题
    private String content;//招聘内容
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
        return "RecruitListInfoData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", bgap_name='" + bgap_name + '\'' +
                ", bgat_name='" + bgat_name + '\'' +
                ", budget='" + budget + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                '}';
    }
}
