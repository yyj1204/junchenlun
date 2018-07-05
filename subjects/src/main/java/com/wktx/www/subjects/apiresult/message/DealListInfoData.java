package com.wktx.www.subjects.apiresult.message;

/**
 * Created by yyj on 2018/6/9.
 * 交易消息列表内容
 * "id": "8","hire_id": "232","amount": "4000.00","add_time": "2018-05-17 16:38:34","remark": "支付薪资"
 */

public class DealListInfoData {
    private String id;//薪资id
    private String hire_id;//雇佣订单id
    private String amount;//薪资
    private String add_time;//时间
    private String remark;//备注

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
        return "DealListInfoData{" +
                "id='" + id + '\'' +
                ", hire_id='" + hire_id + '\'' +
                ", amount='" + amount + '\'' +
                ", add_time='" + add_time + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
