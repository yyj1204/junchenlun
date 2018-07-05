package com.wktx.www.subjects.apiresult.manage.salary;

/**
 * Created by 369 on 2018/3/21.
 * 支付列表
 * id : 40
 * amount : 1.00
 * add_time : 2018-06-26 17:10:18
 * remark : 同意解雇申请，雇主支付薪资
 *
 */

public class SalaryPayBean {
    private String id;//支付id
    private String amount;//支付金额
    private String add_time;//支付时间
    private String remark;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SalaryPayBean{" +
                "id='" + id + '\'' +
                ", amount='" + amount + '\'' +
                ", add_time='" + add_time + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
