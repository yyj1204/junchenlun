package com.wktx.www.emperor.apiresult.staff.salary;

import java.util.List;

/**
 * Created by yyj on 2018/3/21.
 * 我的员工---管理员工---员工工资内容
 * "total_expenditure": "0.01","trusteeship_amount": "0.01","already_amount": "0.00",
 * "trusteeship_list": [{"start_time": "1521388800","end_time": "1524067199","trusteeship_wages": "0.01"}],
 * "already_hair": [{"paid_time": "1519920000","amount": "3500.00"}]
 */

public class StaffSalaryInfoData {
    private String total_expenditure;//累计支出总额
    private String trusteeship_amount;//托管金额
    private String already_amount;//已发工资
    private List<SalaryTrusteeshipBean> trusteeship_list;//托管的工资列表
    private List<SalaryPayBean> already_hire;//支付薪资记录列表

    public String getTotal_expenditure() {
        return total_expenditure;
    }

    public void setTotal_expenditure(String total_expenditure) {
        this.total_expenditure = total_expenditure;
    }

    public String getTrusteeship_amount() {
        return trusteeship_amount;
    }

    public void setTrusteeship_amount(String trusteeship_amount) {
        this.trusteeship_amount = trusteeship_amount;
    }

    public String getAlready_amount() {
        return already_amount;
    }

    public void setAlready_amount(String already_amount) {
        this.already_amount = already_amount;
    }

    public List<SalaryTrusteeshipBean> getTrusteeship_list() {
        return trusteeship_list;
    }

    public void setTrusteeship_list(List<SalaryTrusteeshipBean> trusteeship_list) {
        this.trusteeship_list = trusteeship_list;
    }

    public List<SalaryPayBean> getAlready_hire() {
        return already_hire;
    }

    public void setAlready_hire(List<SalaryPayBean> already_hire) {
        this.already_hire = already_hire;
    }

    @Override
    public String toString() {
        return "StaffSalaryInfoData{" +
                "total_expenditure='" + total_expenditure + '\'' +
                ", trusteeship_amount='" + trusteeship_amount + '\'' +
                ", already_amount='" + already_amount + '\'' +
                ", trusteeship_list=" + trusteeship_list +
                ", already_hire=" + already_hire +
                '}';
    }
}
