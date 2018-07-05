package com.wktx.www.subjects.apiresult.mine.tradingrecord;

/**
 * Created by yyj on 2018/6/9.
 * 交易记录列表内容
 * "id": "8","amount": "4000.00","time": "2018-05-17 16:38:34","remark": "支付薪资"
 */

public class TradingRecordInfoData {
    private String id;//交易id
    private String amount;//金额
    private String time;//时间
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
                ", amount='" + amount + '\'' +
                ", time='" + time + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
