package com.wktx.www.emperor.apiresult.mine.transactionrecord;

import java.util.List;

/**
 * Created by yyj on 2018/3/8.
 * 交易记录内容
 * "transa_amount": "7000.00","trusteeship_amount": "63351.89",
 * "list": [{"id": "151", "hire_id": "410","add_time": "1520049697",
 *           "amount": "0.01","remark": "" ,"name": "","status": "5","tow": "客服"}]
 */

public class TransactionRecordInfoData {
    private String transa_amount;//已支付总金额
    private String trusteeship_amount;//托管总金额
    private List<TransactionBean> list;//交易内容


    public String getTransa_amount() {
        return transa_amount;
    }

    public void setTransa_amount(String transa_amount) {
        this.transa_amount = transa_amount;
    }

    public String getTrusteeship_amount() {
        return trusteeship_amount;
    }

    public void setTrusteeship_amount(String trusteeship_amount) {
        this.trusteeship_amount = trusteeship_amount;
    }

    public List<TransactionBean> getList() {
        return list;
    }

    public void setList(List<TransactionBean> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return "TransactionRecordInfoData{" +
                "transa_amount='" + transa_amount + '\'' +
                ", trusteeship_amount='" + trusteeship_amount + '\'' +
                ", list=" + list +
                '}';
    }
}
