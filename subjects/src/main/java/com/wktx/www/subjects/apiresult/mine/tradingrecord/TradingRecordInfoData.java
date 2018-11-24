package com.wktx.www.subjects.apiresult.mine.tradingrecord;

import java.util.List;

/**
 * Created by yyj on 2018/6/9.
 * 交易记录列表内容
 * "transa_amount": "7000.00","trusteeship_amount": "63351.89",
 * "list": [{"id": "151", "hire_id": "410","add_time": "1520049697",
 *           "amount": "0.01","remark": "" ,"name": "","status": "5","tow": "客服"}]
 */

public class TradingRecordInfoData {
    private String transa_amount;//已收工资总金额
    private String trusteeship_amount;//托管总金额
    private List<TradingBean> list;//交易内容

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

    public List<TradingBean> getList() {
        return list;
    }

    public void setList(List<TradingBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "TradingRecordInfoData{" +
                "transa_amount='" + transa_amount + '\'' +
                ", trusteeship_amount='" + trusteeship_amount + '\'' +
                ", list=" + list +
                '}';
    }
}
