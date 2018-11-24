package com.wktx.www.emperor.apiresult.staff.salary;

import java.util.List;

/**
 * Created by yyj on 2018/3/21.
 * 我的员工---管理员工---员工工资内容
 * hire_price : 3.00
 * trusteeship_wages : 0.00
 * paid_wages : 0.00
 * payList : [{"id":"40","amount":"1.00","add_time":"2018-06-26 17:10:18","remark":"同意解雇申请，雇主支付薪资"}]
 */

public class StaffSalaryInfoData {
    private String hire_price;//雇佣薪资总额
    private String trusteeship_wages;//托管工资
    private String paid_wages;//已发工资
    private List<SalaryPayBean> payList;//薪资发放记录列表

    public String getHire_price() {
        return hire_price;
    }

    public void setHire_price(String hire_price) {
        this.hire_price = hire_price;
    }

    public String getTrusteeship_wages() {
        return trusteeship_wages;
    }

    public void setTrusteeship_wages(String trusteeship_wages) {
        this.trusteeship_wages = trusteeship_wages;
    }

    public String getPaid_wages() {
        return paid_wages;
    }

    public void setPaid_wages(String paid_wages) {
        this.paid_wages = paid_wages;
    }

    public List<SalaryPayBean> getPayList() {
        return payList;
    }

    public void setPayList(List<SalaryPayBean> payList) {
        this.payList = payList;
    }

    @Override
    public String toString() {
        return "StaffSalaryInfoData{" +
                "hire_price='" + hire_price + '\'' +
                ", trusteeship_wages='" + trusteeship_wages + '\'' +
                ", paid_wages='" + paid_wages + '\'' +
                ", payList=" + payList +
                '}';
    }
}
