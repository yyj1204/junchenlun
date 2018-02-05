package com.wktx.www.emperor.apiresult.recruit.retrievalcondition;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yyj on 2018/1/24.
 * 下面多条件筛选菜单
 */

public class BottomBean implements Serializable{
    private List<Bean> bgat;//擅长类目
    private List<Bean> bgap;//擅长平台
    private List<Bean> cust_service_type;//客服类型
    private List<Bean> working_years;//工作经验
    private List<Bean> sex;//性别

    public List<Bean> getBgat() {
        return bgat;
    }

    public void setBgat(List<Bean> bgat) {
        this.bgat = bgat;
    }

    public List<Bean> getBgap() {
        return bgap;
    }

    public void setBgap(List<Bean> bgap) {
        this.bgap = bgap;
    }

    public List<Bean> getCust_service_type() {
        return cust_service_type;
    }

    public void setCust_service_type(List<Bean> cust_service_type) {
        this.cust_service_type = cust_service_type;
    }

    public List<Bean> getWorking_years() {
        return working_years;
    }

    public void setWorking_years(List<Bean> working_years) {
        this.working_years = working_years;
    }

    public List<Bean> getSex() {
        return sex;
    }

    public void setSex(List<Bean> sex) {
        this.sex = sex;
    }


    @Override
    public String toString() {
        return "BottomBean{" +
                "bgat=" + bgat +
                ", bgap=" + bgap +
                ", cust_service_type=" + cust_service_type +
                ", working_years=" + working_years +
                ", sex=" + sex +
                '}';
    }
}
