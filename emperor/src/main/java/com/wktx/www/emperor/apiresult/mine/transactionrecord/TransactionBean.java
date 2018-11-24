package com.wktx.www.emperor.apiresult.mine.transactionrecord;

/**
 * Created by yyj on 2018/3/8.
 * 交易记录内容
 * "id": "151", "hire_id": "410","add_time": "1520049697",
 * "amount": "0.01","remark": "" ,"name": "","status": "5","tow": "客服"
 */

public class TransactionBean {
    private String id;//表id
    private String hire_id;//雇佣id
    private String add_time;//添加时间
    private String amount;//金额
    private String remark;//备注
    private String name;//员工名字
    private String tow;//工作类型
    private String status;//雇佣状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHire_id() {
        return hire_id;
    }

    public void setHire_id(String hire_id) {
        this.hire_id = hire_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTow() {
        return tow;
    }

    public void setTow(String tow) {
        this.tow = tow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransactionBean{" +
                "id='" + id + '\'' +
                ", hire_id='" + hire_id + '\'' +
                ", add_time='" + add_time + '\'' +
                ", amount='" + amount + '\'' +
                ", remark='" + remark + '\'' +
                ", name='" + name + '\'' +
                ", tow='" + tow + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
