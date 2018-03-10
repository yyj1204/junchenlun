package com.wktx.www.emperor.apiresult.mine.transactionrecord;

/**
 * Created by yyj on 2018/3/8.
 * 交易记录内容
 * "id": "2","add_time": "1519920000","amount": "3500.00","remark": "支付美工薪资"
 */

public class TransactionBean {
    private String id;//表id
    private String add_time;//添加时间
    private String amount;//金额
    private String remark;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TransactionRecordInfoData{" +
                "id='" + id + '\'' +
                ", add_time='" + add_time + '\'' +
                ", amount='" + amount + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
