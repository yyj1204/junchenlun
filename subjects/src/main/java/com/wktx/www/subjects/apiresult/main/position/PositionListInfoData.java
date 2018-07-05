package com.wktx.www.subjects.apiresult.main.position;
/**
 * Created by yyj on 2018/2/8.
 * 职位招聘列表内容 \ 我的收藏内容 \ 面试记录内容
 * "id": "17","demand_id": "10","title": "Add","content": "Add","bgap_name": "天猫","bgat_name": "汽车配件",
 * "budget": "123.00","shop_name": "555","shop_logo": null,"is_dated": "0"("time": "2018-06-06 14:21:07")
 *
 */

public class PositionListInfoData {
    private String id;//收藏id（面试id、招聘id）
    private String demand_id;//招聘id
    private String title;//招聘标题
    private String content;//招聘内容
    private String bgap_name;//平台名称
    private String bgat_name;//类目名称
    private String budget;//薪资
    private String shop_name;//店铺名称
    private String shop_logo;//店铺logo
    private String is_dated;//是否过期，1：过期，2：未过期(收藏)
    private String time;//面试时间（面试）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getIs_dated() {
        return is_dated;
    }

    public void setIs_dated(String is_dated) {
        this.is_dated = is_dated;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TaskListInfoData{" +
                "id='" + id + '\'' +
                ", demand_id='" + demand_id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", bgap_name='" + bgap_name + '\'' +
                ", bgat_name='" + bgat_name + '\'' +
                ", budget='" + budget + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_logo='" + shop_logo + '\'' +
                ", is_dated='" + is_dated + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
