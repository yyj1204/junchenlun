package com.wktx.www.emperor.apiresult.staff.salary;

/**
 * Created by 369 on 2018/3/21.
 * 已发工资
 */

public class SalaryPayBean {
    private String paid_time;
    private String amount;

    public String getPaid_time() {
        return paid_time;
    }

    public void setPaid_time(String paid_time) {
        this.paid_time = paid_time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "SalaryPayBean{" +
                "paid_time='" + paid_time + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
